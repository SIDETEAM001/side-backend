package com.sideteam.groupsaver.global.exception.file;

public class FileException extends RuntimeException {

    private final FileErrorCode errorCode;
    private final String message;

    public FileException(FileErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return "File 에러 코드: " + errorCode + ", " + message;
    }
}
