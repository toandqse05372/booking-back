package com.capstone.booking.config.aws;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3ClientService
{
//    void deleteFileFromS3Bucket(String url);

    void uploadFileToS3Bucket(Long placeId, MultipartFile multipartFile, String name, String ext, boolean enablePublicReadAccess);
}