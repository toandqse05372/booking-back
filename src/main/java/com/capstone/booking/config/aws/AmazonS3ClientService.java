package com.capstone.booking.config.aws;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3ClientService
{
    void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess);

    void deleteFileFromS3Bucket(String fileName);
}