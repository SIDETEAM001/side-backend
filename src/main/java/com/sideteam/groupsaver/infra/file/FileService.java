package com.sideteam.groupsaver.infra.file;

import com.sideteam.groupsaver.domain.file.dto.FileUrlDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    /**
     * <p>
     * 저장할 경로와 파일을 바탕으로 파일들을 업로드<br>
     * 파일 URL 리스트 반환
     * </p>
     *
     * @param prefix        저장할 경로
     * @param multipartFile 저장할 파일들
     * @return 저장한 파일 고유 키
     */
    FileUrlDto upload(String prefix, MultipartFile multipartFile);


    /**
     * <p>
     * 저장할 경로와 파일을 바탕으로 파일들을 업로드<br>
     * 파일 URL 리스트 반환
     * </p>
     *
     * @param prefix         저장할 경로
     * @param multipartFiles 저장할 파일들
     * @return 저장한 파일 고유 키 리스트
     */
    FileUrlDto upload(String prefix, List<MultipartFile> multipartFiles);

    /**
     * <p>
     * 기존에 저장된 파일을 삭제
     * </p>
     *
     * @param fileId - DB의 id
     */
    void deleteFile(long fileId);
}
