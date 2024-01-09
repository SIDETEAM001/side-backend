package com.sideteam.groupsaver.domain.file.dto;

import java.net.URL;
import java.util.List;

public record FileUrlDto(
        List<URL> urls
) {
    public static FileUrlDto from(URL fileUrl) {
        return new FileUrlDto(List.of(fileUrl));
    }

    public static FileUrlDto from(List<URL> fileUrls) {
        return new FileUrlDto(fileUrls);
    }

}
