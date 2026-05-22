package com.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static List<Map<String, String>> getTestData(String fileName, String sheetName) {
        List<Map<String, String>> testData = new ArrayList<>();
        String filePath = "src/main/resources/testdata/" + fileName;

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Map<String, String> rowData = new LinkedHashMap<>();

                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    String header = headerRow.getCell(j).getStringCellValue();
                    Cell cell = row.getCell(j);
                    String value = "";
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:  value = cell.getStringCellValue(); break;
                            case NUMERIC: value = String.valueOf((long) cell.getNumericCellValue()); break;
                            case BOOLEAN: value = String.valueOf(cell.getBooleanCellValue()); break;
                            default:      value = ""; break;
                        }
                    }
                    rowData.put(header, value);
                }
                testData.add(rowData);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }

        return testData;
    }
}
