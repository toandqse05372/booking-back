package com.capstone.booking.config.aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import lombok.SneakyThrows;
import org.apache.poi.util.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
public class AmazonS3ClientServiceImplTest {

    @Mock
    private Region mockAwsRegion;
    @Mock
    private AWSCredentialsProvider mockAwsCredentialsProvider;

    private AmazonS3ClientServiceImpl amazonS3ClientServiceImplUnderTest;


    @Before
    public void setUp() {
        initMocks(this);
        amazonS3ClientServiceImplUnderTest = new AmazonS3ClientServiceImpl(Region.getRegion(Regions.fromName("ap-southeast-1")), mockAwsCredentialsProvider, "toandqse05372-bucket");
    }

    @SneakyThrows
    @Test
    public void testUploadFileToS3Bucket() {
        // Setup
        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        // Run the test
        amazonS3ClientServiceImplUnderTest.uploadFileToS3Bucket(0L, multipartFile, "name", "ext", false);

        // Verify the results
    }
}
