package com.sideteam.groupsaver.domain.file.api;

import com.sideteam.groupsaver.domain.file.dto.FileUrlDto;
import com.sideteam.groupsaver.infra.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
@RestController
public class FileController {
    private final FileService fileService;


    @PostMapping
    public ResponseEntity<FileUrlDto> uploadFiles(@RequestParam(name = "files") List<MultipartFile> files) {
        final FileUrlDto fileUrlDto = fileService.upload("upload/", files);
        return ResponseEntity.ok(fileUrlDto);
    }

}
