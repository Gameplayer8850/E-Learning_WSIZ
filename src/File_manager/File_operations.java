package File_manager;

import Data.Data;
import Data.Data_json;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;

public class File_operations {

    public File file_excel;
    private File file_json;

    public boolean Is_File() {
        file_excel = Get_File(Get_List_of_Files(), ".xls");
        if (file_excel != null) {
            return true;
        }
        return false;
    }

    private boolean Is_Json() {
        file_json = Get_File(Get_List_of_Files(), ".json");
        if (file_json != null) {
            return true;
        }
        return false;
    }

    public File Get_File(File[] files, String type) {
        for (File file : files) {
            if (file.getName().contains(type)) {
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

    public Boolean Json_data() {
        if (!Is_Json()) {
            return false;
        }
        Gson gson = new Gson();
        try {
            Data_json data = gson.fromJson(new FileReader(file_json), Data_json.class);
            Data.webhook_for_A = data.getWebhook_for_A();
            Data.webhook_for_B = data.getWebhook_for_B();
            Data.semester = data.getSemester();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
