package com.synterra.lens.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.synterra.lens.config.FileStorageConfig;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class FileDownloadUtil {

    private final FileStorageConfig fileStorageConfig;

    public Resource getFileAsResource(String fileName) throws IOException {
        Path filePath = Paths.get(fileStorageConfig.getUploadDir()).resolve(fileName).normalize();
        if (Files.exists(filePath)) {
            return new FileSystemResource(filePath.toFile()); 
        } else {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }
}
