package com.clover.moodiary.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String upload(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String key = "images/" + UUID.randomUUID() + ext;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(URLConnection.guessContentTypeFromName(originalFileName))
//                    .acl("public-read")
                    .build();

            PutObjectResponse response = s3Client.putObject(
                    request,
                    RequestBody.fromBytes(file.getBytes())
            );

            return "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + key;

        } catch (S3Exception e) {
            throw new RuntimeException("S3 업로드 실패: " + e.awsErrorDetails().errorMessage());
        }
    }
}
