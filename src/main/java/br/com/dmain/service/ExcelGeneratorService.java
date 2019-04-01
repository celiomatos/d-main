package br.com.dmain.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class ExcelGeneratorService {

    public ByteArrayInputStream pagamentosToExcell() throws IOException {

        String[] COLUMNs = {"Id", "Name", "Address", "Age"};
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            CreationHelper createHelper = workbook.getCreationHelper();

            XSSFSheet sheet = workbook.createSheet("Customers");

            sheet.setMargin(XSSFSheet.RightMargin, 0.4);
            sheet.setMargin(XSSFSheet.LeftMargin, 0.4);
            sheet.setMargin(XSSFSheet.TopMargin, 0.4);
            sheet.setMargin(XSSFSheet.BottomMargin, 0.4);

            XSSFPrintSetup layout = sheet.getPrintSetup();
            layout.setLandscape(true);
            layout.setFitWidth((short) 1);
            layout.setFitHeight((short) 0);
            layout.setPaperSize(PrintSetup.A4_PAPERSIZE);
            layout.setFooterMargin(0.25);

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // CellStyle for Age
            CellStyle ageCellStyle = workbook.createCellStyle();
            ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));


            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
