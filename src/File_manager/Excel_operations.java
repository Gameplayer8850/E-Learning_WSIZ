package File_manager;

import Data.Data_from_Excel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class Excel_operations {

    List<Data_from_Excel> all;
    List<Data_from_Excel> group_A;
    List<Data_from_Excel> group_B;

    public boolean Get_Data_from_Excel(File file) throws IOException {
        HSSFWorkbook wb;
        HSSFSheet sheet;
        FileInputStream fis = new FileInputStream(file);
        wb = new HSSFWorkbook(fis);
        sheet = wb.getSheetAt(0);
        FormulaEvaluator forlulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
        int title_index, day_index, hour_index, group_index, link_index;
        title_index = day_index = hour_index = group_index = link_index = 0;
        List<Data_from_Excel> objects = new ArrayList();
        for (Row row : sheet) {
            Data_from_Excel line = new Data_from_Excel();
            boolean flag = false;
            for (Cell cell : row) {
                switch (forlulaEvaluator.evaluateInCell(cell).getCellType()) {
                    case STRING:
                        if (cell.getRowIndex() == 0) {
                            switch (cell.getStringCellValue()) {
                                case "Przedmiot":
                                    title_index = cell.getColumnIndex();
                                    break;
                                case "Dzień":
                                    day_index = cell.getColumnIndex();
                                    break;
                                case "Godzina":
                                    hour_index = cell.getColumnIndex();
                                    break;
                                case "Grupa":
                                    group_index = cell.getColumnIndex();
                                    break;
                                case "Wideokonferencja":
                                    link_index = cell.getColumnIndex();
                                    break;
                            }
                        } else {
                            if (cell.getColumnIndex() == title_index) {
                                line.title = cell.getStringCellValue();
                                flag = true;
                                continue;
                            }
                            if (cell.getColumnIndex() == day_index) {
                                line.day = cell.getStringCellValue();
                                flag = true;
                                continue;
                            }
                            if (cell.getColumnIndex() == hour_index) {
                                line.hours = cell.getStringCellValue();
                                flag = true;
                                continue;
                            }
                            if (cell.getColumnIndex() == group_index) {
                                line.group = cell.getStringCellValue();
                                flag = true;
                                continue;
                            }
                            if (cell.getColumnIndex() == link_index) {
                                line.link = cell.getStringCellValue();
                                flag = true;
                                continue;
                            }
                        }
                        break;
                }
            }
            if (flag) {
                if (line.group.contains(Data.Data.semester) && line.day.contains(Data.Data.today)) {
                    objects.add(line);
                }
            }
        }
        if (!objects.isEmpty()) {
            all = objects;
            Sort_data_on_group();
            return true;
        } else {
            return false;
        }
    }

    private void Sort_data_on_group() {
        group_A = new ArrayList();
        group_B = new ArrayList();
        for (Data_from_Excel dfm : all) {
            if (dfm.group == null) {
                continue;
            }
            if (dfm.group.contains("A")) {
                group_A.add(dfm);
            } else if (dfm.group.contains("B")) {
                group_B.add(dfm);
            } else {
                group_A.add(dfm);
                group_B.add(dfm);
            }
        }
    }

    public String Return_String_group_A() {
        if (group_A.isEmpty()) {
            return "";
        }
        String data = "`Dzień: " + Data.Data.today + "`";
        for (Data_from_Excel dfm : group_A) {
            data += ("\\r\\n`" + dfm.hours + " - " + dfm.title + "`\\r\\n" + dfm.link);
        }
        return data;
    }

    public String Return_String_group_B() {
        if (group_B.isEmpty()) {
            return "";
        }
        String data = "`Dzień: " + Data.Data.today + "`";
        for (Data_from_Excel dfm : group_B) {
            data += ("\\r\\n`" + dfm.hours + " - " + dfm.title + "`\\r\\n" + dfm.link);
        }
        return data;
    }
}
