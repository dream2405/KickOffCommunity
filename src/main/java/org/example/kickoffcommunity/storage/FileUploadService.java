package org.example.kickoffcommunity.storage;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class FileUploadService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file, String fileName) {
        if (file.isEmpty()) {
            return "Failed to upload empty file.";
        }

        try {
            // 파일 경로 설정
            Path uploadPath = Paths.get(uploadDir + fileName);

            // 디렉토리가 존재하지 않는 경우 생성
            if (!Files.exists(uploadPath.getParent())) {
                Files.createDirectories(uploadPath.getParent());
            }

            // MultipartFile을 byte 배열로 변환 후 파일 쓰기
            byte[] fileBytes = file.getBytes();
            Files.write(uploadPath, fileBytes, StandardOpenOption.CREATE);

            return "File uploaded successfully: " + file.getOriginalFilename();
        } catch (IOException e) {
            return "Failed to upload " + file.getOriginalFilename() + " due to IO error";
        }
    }
}
