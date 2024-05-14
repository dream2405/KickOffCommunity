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

    public void deleteFile(String fileName) {
        Path path = Paths.get(uploadDir + fileName);

        try {
            // 파일이 존재하는 경우 삭제합니다.
            boolean deleted = Files.deleteIfExists(path);

            // 삭제 성공 여부를 확인합니다.
            if (deleted) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("File not found.");
            }
        } catch (IOException e) {
            // 파일 삭제 중 발생한 예외를 처리
            System.err.println("Failed to delete the file: " + e.getMessage());
        }
    }
}
