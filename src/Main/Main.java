package Main;

import Data.Data;
import File_manager.Discord;
import File_manager.Excel_operations;
import File_manager.File_operations;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String date = java.time.LocalDate.now().toString();
        Data.today = date.substring(8) + "." + date.substring(5, 7);
        if (Data.today.substring(0, 1).equals("0")) {
            Data.today = Data.today.substring(1);
        }
        File_operations fo = new File_operations();
        if (!fo.Is_File()) {
            return;
        }
        if (!fo.Json_data()) {
            return;
        }
        Excel_operations eo = new Excel_operations();
        try {
            if (!eo.Get_Data_from_Excel(fo.file_excel)) {
                return;
            }
        } catch (IOException ex) {
            return;
        }
        Discord dc = new Discord();
        dc.Send(Data.webhook_for_A, eo.Return_String_group_A());
        dc.Send(Data.webhook_for_B, eo.Return_String_group_B());
    }
}
