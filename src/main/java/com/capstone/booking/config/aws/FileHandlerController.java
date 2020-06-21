package com.capstone.booking.config.aws;

import org.apache.commons.compress.compressors.FileNameUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/files")
public class FileHandlerController {

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    @PostMapping
    public Map<String, String> uploadFile(@RequestPart(value = "file") MultipartFile[] files,
                                          @RequestPart(value = "placeId") String id)
    {
        Map<String, String> response = new HashMap<>();
        Long placeId = Long.parseLong(id);
        int location = 1;
        for (MultipartFile file: files) {
            String ext = "."+FilenameUtils.getExtension(file.getOriginalFilename());
            this.amazonS3ClientService.uploadFileToS3Bucket(placeId, file, id +"_"+ location , ext, true);
            location++;
            response.put("message", "file [] uploading request submitted successfully.");
        }

        return response;
    }

    @DeleteMapping
    public Map<String, String> deleteFile(@RequestParam("file_name") String fileName)
    {
        this.amazonS3ClientService.deleteFileFromS3Bucket(fileName);

        Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + fileName + "] removing request submitted successfully.");

        return response;
    }
}