package File_manager;

import Data.Data;
import java.io.File;

public class File_operations {

    public File file_excel;

    public boolean Is_File() {
        file_excel = Get_File(Get_List_of_Files());
        if (file_excel != null) {
            return true;
        }
        return false;
    }

    public File Get_File(File[] files) {
        for (File file : files) {
            if (file.getName().contains(".xls")) {
                Data.file_exist = true;
                return file;
            }
        }
        return null;
    }

    public File[] Get_List_of_Files() {
        File folder = new File(System.getProperty("user.dir"));
        return folder.listFiles();
    }
}
