package org.mfr.infra;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mfr.domain.port.ExcelActionsPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExcelActions implements ExcelActionsPort {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Sheet getByPath(String path) {
        try(FileInputStream file = new FileInputStream(path); Workbook workbook = new XSSFWorkbook(file)) {
            return workbook.getSheetAt(0);
        } catch (Exception ex) {
            log.error("Falha ao buscar excel no path informado.", ex);
            return null;
        }
    }

    @Override
    public void write(Workbook workbook) {
        try {
            String localDateTimeNow = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());

            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + localDateTimeNow + " Valores Atualizados das OS.xlsx";

            FileOutputStream outputStream = new FileOutputStream(fileLocation);

            workbook.write(outputStream);
            workbook.close();
        } catch (Exception ex) {
            log.error("Falha ao gerar Excel", ex);
        }
    }
}
