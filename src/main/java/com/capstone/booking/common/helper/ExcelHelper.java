package com.capstone.booking.common.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.capstone.booking.api.output.ReportItem;
import com.capstone.booking.entity.Code;
import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.repository.VisitorTypeRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

//working with excel
@Component
public class ExcelHelper {

    private static VisitorTypeRepository visitorTypeRepository;

    @Autowired
    public ExcelHelper(VisitorTypeRepository visitorTypeRepository){
        ExcelHelper.visitorTypeRepository = visitorTypeRepository;
    }

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADER_REPORT = {"#", "Ticket Type Name", "Quantity", "Total"};
    static String SHEET = "Sheet1";

    //check excel format
    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    //write data to excel file
    public static void writeExcel(List<ReportItem> reportItems, String excelFilePath) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowCount = 0;

        for (ReportItem aBook : reportItems) {
            Row row = sheet.createRow(++rowCount);
            Cell cell = row.createCell(1);
            cell.setCellValue(aBook.getTicketTypeName());

            cell = row.createCell(2);
            cell.setCellValue(aBook.getQuantity());

            cell = row.createCell(3);
            cell.setCellValue(aBook.getTotal());
        }

        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }

    //get data from excel
    public static List<Code> excelToCode(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Code> codes = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Code code = new Code();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 1:
                            code.setVisitorType(visitorTypeRepository. findByTypeKey(currentCell.getStringCellValue()));
                            break;
                        case 2:
                            currentCell.setCellType(CellType.STRING);
                            code.setCode(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                codes.add(code);
            }
            workbook.close();

            return codes;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}