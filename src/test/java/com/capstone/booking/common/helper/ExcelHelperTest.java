package com.capstone.booking.common.helper;

import com.capstone.booking.api.output.ReportItem;
import com.capstone.booking.entity.*;
import lombok.SneakyThrows;
import org.apache.poi.util.IOUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ExcelHelperTest {

    @SneakyThrows
    @Test
    public void testHasExcelFormat() {
        // Setup
        File file = new File("NiceJavaBooks.xls");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        // Run the test
        final boolean result = ExcelHelper.hasExcelFormat(multipartFile);

        // Verify the results
        assertThat(!result).isTrue();
    }

    @SneakyThrows
    @Test
    public void testHasWrongFormat() {
        // Setup
        File file = new File("NiceJavaBooks.xls");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        // Run the test
        final boolean result = ExcelHelper.hasExcelFormat(multipartFile);

        // Verify the results
        assertThat(!result).isTrue();
    }

    @Test
    public void testWriteExcel() throws Exception {
        // Setup
        final ReportItem item = new ReportItem();
        item.setTicketTypeName("ticketTypeName");
        item.setQuantity(0);
        item.setTotal(0);
        final List<ReportItem> reportItems = Arrays.asList(item);

        // Run the test
        ExcelHelper.writeExcel(reportItems, "excelFilePath", 100);

        // Verify the results
    }
}
