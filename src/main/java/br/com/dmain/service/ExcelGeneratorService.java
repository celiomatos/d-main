package br.com.dmain.service;

import br.com.dmain.dto.PagamentoSearchDto;
import br.com.dmain.model.Pagamento;
import br.com.dmain.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFHeaderFooter;
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

    private XSSFCellStyle styleGroupOne;
    private XSSFCellStyle styleGroupTwo;
    private XSSFCellStyle styleGroupThree;
    private XSSFCellStyle styleGroupFour;
    private XSSFCellStyle styleHeader;
    private XSSFCellStyle styleHeaderBlack;
    private XSSFCellStyle styleDefault;
    private XSSFCellStyle styleDefaultOdd;
    private XSSFCellStyle styleAlinhado;

    public ByteArrayInputStream pagamentosToExcell(PagamentoSearchDto pagSearchDto) throws IOException {

        Page<Pagamento> pagamentos = pagamentoService.findAll(pagSearchDto);
        if (pagamentos.getTotalElements() == 0 || pagamentos.getTotalElements() > 1000) {
            throw new IOException("");
        } else {
            boolean isLast = false;

            try (XSSFWorkbook workbook = new XSSFWorkbook();
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {

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

                XSSFRow row;
                int rowAtual = 6;
                XSSFCell cell;
                int cellAtual;

                setStyle(workbook);

                // ordem de agrupamento
                String grupo1 = "Orgão";
                String grupo2 = "Credor";

                String valueGroupOne = "";
                String valueGroupTwo = "";

                Double totalG1 = new Double(0);
                Double totalG2 = new Double(0);
                Double totalGeral = new Double(0);

                int rowValorOne = 0;
                int rowValorTwo = 0;

                int odd = 0;
                int i = 0;

                do {

                    odd++;
                    boolean isCreateRow = false;

                    String chaveGrupo1 = valueGroup(grupo1, lst.get(i));

                    if (!valueGroupOne.equalsIgnoreCase(chaveGrupo1)) {

                        if (i > 0) {
                            cell = sheet.getRow(rowValorOne).createCell(6);
                            cell.setCellValue(totalG1);
                            cell.setCellStyle(styleGroupOne);

                            totalG1 = 0D;
                            sheet.createRow(rowAtual++);
                            sheet.createRow(rowAtual++);
                            isCreateRow = true;
                        }

                        valueGroupOne = chaveGrupo1;

                        rowValorOne = rowAtual;

                        row = sheet.createRow(rowAtual++);
                        cellAtual = 0;
                        cell = row.createCell(cellAtual++);
                        cell.setCellValue(grupo1);
                        cell.setCellStyle(styleGroupOne);

                        cell = row.createCell(cellAtual++);
                        cell.setCellValue(valueGroupOne);
                        cell.setCellStyle(styleGroupOne);
                        cell = row.createCell(cellAtual++);
                        cell.setCellStyle(styleGroupOne);
                        cell = row.createCell(cellAtual++);
                        cell.setCellStyle(styleGroupOne);
                        cell = row.createCell(cellAtual++);
                        cell.setCellStyle(styleGroupOne);
                        cell = row.createCell(cellAtual++);
                        cell.setCellStyle(styleGroupOne);

                        sheet.addMergedRegion(new CellRangeAddress(rowAtual - 1, rowAtual - 1, 1, 5));
                        sheet.createRow(rowAtual++);
                    }

                    StringBuilder chaveGrupo2 = new StringBuilder();
                    chaveGrupo2.append(chaveGrupo1);
                    String valueGroupTwoAux = valueGroup(grupo2, lst.get(i));
                    chaveGrupo2.append(valueGroupTwoAux);

                    if (!valueGroupTwo.equalsIgnoreCase(chaveGrupo2.toString())) {
                        odd = 1;
                        if (i > 0) {
                            cell = sheet.getRow(rowValorTwo).createCell(6);
                            cell.setCellValue(totalG2);
                            cell.setCellStyle(styleGroupTwo);

                            totalG2 = 0D;

                            if (!isCreateRow) {
                                sheet.createRow(rowAtual++);
                            }
                        }
                        rowValorTwo = rowAtual;

                        valueGroupTwo = chaveGrupo2.toString();

                        row = sheet.createRow(rowAtual++);
                        cellAtual = 0;
                        cell = row.createCell(cellAtual++);
                        cell.setCellValue(grupo2);
                        cell.setCellStyle(styleGroupTwo);

                        cell = row.createCell(cellAtual++);
                        cell.setCellValue(valueGroupTwoAux);
                        cell.setCellStyle(styleGroupTwo);
                        cell = row.createCell(cellAtual++);
                        cell.setCellStyle(styleGroupTwo);
                        cell = row.createCell(cellAtual++);
                        cell.setCellStyle(styleGroupTwo);
                        cell = row.createCell(cellAtual++);
                        cell.setCellStyle(styleGroupTwo);
                        cell = row.createCell(cellAtual++);
                        cell.setCellStyle(styleGroupTwo);

                        sheet.addMergedRegion(new CellRangeAddress(rowAtual - 1, rowAtual - 1, 1, 5));

                        row = sheet.createRow(rowAtual++);
                        cellAtual = 0;

                        cell = row.createCell(cellAtual++);
                        cell.setCellValue("Data");
                        cell.setCellStyle(styleHeaderBlack);

                        cell = row.createCell(cellAtual++);
                        cell.setCellValue("OB");
                        cell.setCellStyle(styleHeaderBlack);

                        cell = row.createCell(cellAtual++);
                        cell.setCellValue("NL");
                        cell.setCellStyle(styleHeaderBlack);

                        cell = row.createCell(cellAtual++);
                        cell.setCellValue("NE");
                        cell.setCellStyle(styleHeaderBlack);

                        cell = row.createCell(cellAtual++);
                        cell.setCellValue("Fonte");
                        cell.setCellStyle(styleHeaderBlack);

                        cell = row.createCell(cellAtual++);
                        cell.setCellValue("Classificação");
                        cell.setCellStyle(styleHeaderBlack);

                        cell = row.createCell(cellAtual++);
                        cell.setCellValue("Valor");
                        cell.setCellStyle(styleHeaderBlack);

                    }

                    row = sheet.createRow(rowAtual++);
                    cellAtual = 0;
                    cell = row.createCell(cellAtual++);
                    cell.setCellValue(lst.get(i)[4].toString());
                    if (odd % 2 == 0) {
                        cell.setCellStyle(styleDefaultOdd);
                    } else {
                        cell.setCellStyle(styleDefault);
                    }
                    cell = row.createCell(cellAtual++);
                    //nr_ob
                    if (lst.get(i)[5] != null && lst.get(i)[5].toString().length() > 7) {
                        cell.setCellValue(lst.get(i)[5].toString().substring(6));
                    } else {
                        cell.setCellValue("-");
                    }

                    if (odd % 2 == 0) {
                        cell.setCellStyle(styleDefaultOdd);
                    } else {
                        cell.setCellStyle(styleDefault);
                    }

                    cell = row.createCell(cellAtual++);
                    if (lst.get(i)[6] != null && lst.get(i)[6].toString().length() > 7) {
                        cell.setCellValue(lst.get(i)[6].toString().substring(6));
                    } else {
                        cell.setCellValue("-");
                    }
                    if (odd % 2 == 0) {
                        cell.setCellStyle(styleDefaultOdd);
                    } else {
                        cell.setCellStyle(styleDefault);
                    }
                    cell = row.createCell(cellAtual++);
                    if (lst.get(i)[7] != null && lst.get(i)[7].toString().length() > 7) {
                        cell.setCellValue(lst.get(i)[7].toString().substring(6));
                    } else {
                        cell.setCellValue("-");
                    }
                    if (odd % 2 == 0) {
                        cell.setCellStyle(styleDefaultOdd);
                    } else {
                        cell.setCellStyle(styleDefault);
                    }
                    cell = row.createCell(cellAtual++);
                    cell.setCellValue(lst.get(i)[8].toString()
                            + "-" + lst.get(i)[9]);
                    if (odd % 2 == 0) {
                        cell.setCellStyle(styleDefaultOdd);
                    } else {
                        cell.setCellStyle(styleDefault);
                    }
                    cell = row.createCell(cellAtual++);
                    cell.setCellValue(lst.get(i)[10].toString());
                    if (odd % 2 == 0) {
                        cell.setCellStyle(styleDefaultOdd);
                    } else {
                        cell.setCellStyle(styleDefault);
                    }

                    cell = row.createCell(cellAtual++);
                    Double valor = Util.strToBigDecimal(lst.get(i)[2].toString()).doubleValue();
                    cell.setCellValue(valor);
                    if (odd % 2 == 0) {
                        cell.setCellStyle(styleDefaultOdd);
                    } else {
                        cell.setCellStyle(styleDefault);
                    }

                    cell.getRow().setHeight((short) -1);

                    totalG1 += valor;
                    totalG2 += valor;
                    totalGeral += valor;

                    if (i == lst.size() - 1) {
                        cell = sheet.getRow(rowValorOne).createCell(6);
                        cell.setCellValue(totalG1);
                        cell.setCellStyle(styleGroupOne);

                        cell = sheet.getRow(rowValorTwo).createCell(6);
                        cell.setCellValue(totalG2);
                        cell.setCellStyle(styleGroupTwo);

                        row = sheet.createRow(4);
                        cell = row.createCell(0);
                        cell.setCellValue("Total Geral");
                        cell.setCellStyle(styleDefaultOdd);

                        cell = row.createCell(1);
                        cell.setCellValue(totalGeral);
                        cell.setCellStyle(styleDefaultOdd);

                        cell = row.createCell(2);
                        cell.setCellStyle(styleDefaultOdd);
                        sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 4));

                        row = sheet.createRow(0);
                        cell = row.createCell(0);
                        cell.setCellValue("Relação de pagamentos");

                        createHead(b, sheet);
                    }

                    if (pagamentos.isLast()) {
                        isLast = true;
                    } else {
                        pagSearchDto.setPage(pagSearchDto.getPage() + 1);
                        pagamentos = pagamentoService.findAll(pagSearchDto);
                    }
                    i++;
                } while (isLast);

                sheet.setColumnWidth(0, 2500);
                sheet.setColumnWidth(1, 1700);
                sheet.setColumnWidth(2, 1700);
                sheet.setColumnWidth(3, 1700);
                sheet.setColumnWidth(4, 11900);
                sheet.setColumnWidth(5, 11900);
                sheet.setColumnWidth(6, 4000);

                //numero de pagina
                XSSFHeaderFooter header = (XSSFHeaderFooter) sheet.getFooter();
                header.setLeft(HSSFHeader.font("Calibri", "Normal")
                        + HSSFHeader.fontSize((short) 9)
                        + "Deputado Dermilson Chagas");
                header.setRight(HSSFHeader.font("Calibri", "Normal")
                        + HSSFHeader.fontSize((short) 9) + "Página "
                        + HeaderFooter.page() + " de " + HeaderFooter.numPages());

                workbook.write(out);
                return new ByteArrayInputStream(out.toByteArray());

            }
        }
    }

    private void createHead(PagamentoBean b, XSSFSheet sheet) {

        StringBuilder vl = new StringBuilder("Valores ");
        String e = "";

        if (b.getValorInicial() != null && !b.getValorInicial().isEmpty()) {
            vl.append("acima de ");
            vl.append(b.getValorInicial());
            e = " e ";
        }
        if (b.getValorFinal() != null && !b.getValorFinal().isEmpty()) {
            vl.append(e);
            vl.append("abaixo de ");
            vl.append(b.getValorFinal());
        }
        int r = 3;
        int c = 4;
        if (!b.getTipoConsulta().equalsIgnoreCase("a")) {
            c = 1;
        }
        if (vl.length() > 10) {
            XSSFRow row = sheet.createRow(r);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue(vl.toString());
            cell.setCellStyle(styleDefaultOdd);
            sheet.addMergedRegion(new CellRangeAddress(r, r, 0, c));
            r = 2;
        }
        StringBuilder pe = new StringBuilder("Período");
        if (b.getDtInicial() != null && b.getDtInicial().length() > 0) {
            pe.append(" de ");
            pe.append(b.getDtInicial());

        }
        if (b.getDtFinal() != null && b.getDtFinal().length() > 0) {
            pe.append(" até ");
            pe.append(b.getDtFinal());
        }
        if (pe.length() > 10) {
            XSSFRow row = sheet.createRow(r);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue(pe.toString());
            cell.setCellStyle(styleDefaultOdd);
            sheet.addMergedRegion(new CellRangeAddress(r, r, 0, c));
        }
    }

    private void setStyle(XSSFWorkbook wb) {
        setStyleGroupOne(wb);
        setStyleGroupTwo(wb);
        setStyleGroupThree(wb);
        setStyleGroupFour(wb);
        setStyleHeader(wb);
        setStyleHeaderBlack(wb);
        setStyleDefault(wb);
        setStyleDefaultOdd(wb);
        setStyleAlinhado(wb);
    }

    /**
     * @param wb
     */
    private void setStyleGroupOne(XSSFWorkbook wb) {
        styleGroupOne = wb.createCellStyle();
        styleGroupOne.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        styleGroupOne.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleGroupOne.setDataFormat((short) 7);
    }

    /**
     * @param wb
     */
    private void setStyleGroupTwo(XSSFWorkbook wb) {
        styleGroupTwo = wb.createCellStyle();
        styleGroupTwo.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        styleGroupTwo.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleGroupTwo.setDataFormat((short) 7);
    }

    /**
     * @param wb
     */
    private void setStyleGroupThree(XSSFWorkbook wb) {
        styleGroupThree = wb.createCellStyle();
        styleGroupThree.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        styleGroupThree.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleGroupThree.setDataFormat((short) 7);
    }

    /**
     * @param wb
     */
    private void setStyleGroupFour(XSSFWorkbook wb) {
        styleGroupFour = wb.createCellStyle();
        styleGroupFour.setFillForegroundColor(IndexedColors.GOLD.getIndex());
        styleGroupFour.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleGroupFour.setDataFormat((short) 7);
    }

    /**
     * @param wb
     */
    private void setStyleHeader(XSSFWorkbook wb) {
        styleHeader = wb.createCellStyle();
        styleHeader.setBorderBottom(BorderStyle.THIN);
        styleHeader.setBorderTop(BorderStyle.THIN);
        styleHeader.setBorderLeft(BorderStyle.THIN);
        styleHeader.setBorderRight(BorderStyle.THIN);
        styleHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
    }

    /**
     * @param wb
     */
    private void setStyleHeaderBlack(XSSFWorkbook wb) {
        styleHeaderBlack = wb.createCellStyle();
        styleHeaderBlack.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        styleHeaderBlack.setFillPattern(CellStyle.SOLID_FOREGROUND);

        XSSFFont font = wb.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        styleHeaderBlack.setFont(font);
    }

    /**
     * @param wb
     */
    private void setStyleDefault(XSSFWorkbook wb) {
        styleDefault = wb.createCellStyle();
        styleDefault.setDataFormat((short) 7);
        styleDefault.setVerticalAlignment(VerticalAlignment.CENTER);
        styleDefault.setAlignment(HorizontalAlignment.JUSTIFY);
    }

    /**
     * @param wb
     */
    private void setStyleDefaultOdd(XSSFWorkbook wb) {
        styleDefaultOdd = wb.createCellStyle();
        styleDefaultOdd.setDataFormat((short) 7);
        styleDefaultOdd.setVerticalAlignment(VerticalAlignment.CENTER);
        styleDefaultOdd.setAlignment(HorizontalAlignment.JUSTIFY);
        styleDefaultOdd.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        styleDefaultOdd.setFillPattern(CellStyle.SOLID_FOREGROUND);
    }

    /**
     * @param wb
     */
    private void setStyleAlinhado(XSSFWorkbook wb) {
        styleAlinhado = wb.createCellStyle();
        styleAlinhado.setVerticalAlignment(VerticalAlignment.TOP);
        styleAlinhado.setAlignment(HorizontalAlignment.JUSTIFY);
    }

    private String valueGroup(String grupo, Object[] row) {
        String value = null;
        if (grupo.equalsIgnoreCase("Orgão")) {
            value = row[0] + " - " + row[1];
        } else if (grupo.equalsIgnoreCase("Credor")) {
            value = row[3].toString();
        }
        return value;
    }
}
