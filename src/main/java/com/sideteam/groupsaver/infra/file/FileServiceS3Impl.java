package com.sideteam.groupsaver.infra.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sideteam.groupsaver.domain.file.domain.ResourceFile;
import com.sideteam.groupsaver.domain.file.dto.FileUrlDto;
import com.sideteam.groupsaver.domain.file.repository.ResourceFileRepository;
import com.sideteam.groupsaver.global.exception.file.FileErrorCode;
import com.sideteam.groupsaver.global.exception.file.FileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
public class FileServiceS3Impl implements FileService {
    private final AmazonS3 amazonS3;
    private final String bucketName;
    private final ResourceFileRepository resourceFileRepository;

    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>(Arrays.asList(
            "png", "jpeg", "jpg"
    ));


    public FileServiceS3Impl(
            AmazonS3 amazonS3,
            @Value("${cloud.aws.s3.bucket}") String bucketName,
            ResourceFileRepository resourceFileRepository
    ) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
        this.resourceFileRepository = resourceFileRepository;
    }


    @Override
    public FileUrlDto upload(String prefix, MultipartFile multipartFile) {
        if (!isValidExtension(multipartFile)) {
            throw new FileException(FileErrorCode.INVALID_EXTENSION, multipartFile.getOriginalFilename());
        }
        URL fileUrl = saveInStorage(prefix, multipartFile);
        return FileUrlDto.from(fileUrl);
    }

    @Override
    public FileUrlDto upload(String prefix, List<MultipartFile> multipartFiles) {
        List<URL> fileUrls = multipartFiles.stream()
                .filter(this::isValidExtension)
                .map(file -> saveInStorage(prefix, file))
                .toList();

        log.info("총 {}개의 파일 업로드 성공", fileUrls.size());
        return FileUrlDto.from(fileUrls);
    }


    @Override
    public void deleteFile(String fileKey) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileKey));
        resourceFileRepository.deleteByFileKey(fileKey);
        log.info("파일 '{}' 삭제 완료", fileKey);
    }


    private URL saveInStorage(String prefix, MultipartFile multipartFile) {
        String fileKey = createFileKey(prefix, multipartFile.getOriginalFilename());
        URL fileUrl = uploadFileToS3(fileKey, multipartFile);
        log.debug("S3 업로드 성공: {}", fileKey);

        ResourceFile resourceFile = ResourceFile.builder()
                .fileKey(fileKey)
                .path(fileUrl)
                .build();
        resourceFileRepository.save(resourceFile);
        return fileUrl;
    }

    private URL uploadFileToS3(String fileKey, MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        try {
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, fileKey, file.getInputStream(), objectMetadata);

            amazonS3.putObject(putObjectRequest);
            return amazonS3.getUrl(bucketName, fileKey);
        } catch (IOException | AmazonS3Exception e) {
            throw new FileException(FileErrorCode.FAILED_S3_UPLOAD, e.getMessage());
        }
    }


    private String createFileKey(String prefix, String fileName) {
        String randomFileName = UUID.randomUUID() + getValidFileExtensionWithDot(fileName);
        return Paths.get(formatPrefix(prefix), randomFileName).toString();
    }

    private static String formatPrefix(String prefix) {
        return prefix.endsWith("/") ? prefix : prefix + "/";
    }

    private String getValidFileExtensionWithDot(String fileName) {
        String extension = StringUtils.getFilenameExtension(fileName);
        return "." + extension;
    }

    private boolean isValidExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(fileName);
        return ALLOWED_EXTENSIONS.contains(extension);
    }


}
