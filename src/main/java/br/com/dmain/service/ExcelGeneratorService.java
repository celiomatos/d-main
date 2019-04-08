package br.com.dmain.service;

import br.com.dmain.dto.PagamentoSearchDto;
import br.com.dmain.model.Pagamento;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class ExcelGeneratorService {

    @Autowired
    private PagamentoService pagamentoService;

    public ByteArrayInputStream pagamentosToExcell(PagamentoSearchDto pagSearchDto) throws IOException {

        Page<Pagamento> pagamentos = pagamentoService.findAll(pagSearchDto);
        if (pagamentos.isEmpty()) {
            throw new IOException("");
        } else {
            do {
                pagamentos = pagamentoService.findAll(pagSearchDto);
            } while (pagamentos.isEmpty());
        }

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
