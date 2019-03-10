package utility;

import jdk.nashorn.internal.parser.JSONParser;
import model.Task;
import org.json.JSONArray;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");

    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {

        List<Task> arrTasks = null;

        try {
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(jsonDataFile)); // 建立一个输入流对象reader

            int tempchar;
            String strJson = "";
            while ((tempchar = reader.read()) != -1) {
                strJson += (char) tempchar;
            }
//            System.out.println(strJson);
            reader.close();

            TaskParser taskParser = new TaskParser();

            arrTasks = taskParser.parse(strJson);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrTasks;

    }

    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        JSONArray arrJson = Jsonifier.taskListToJson(tasks);
        String outStr = arrJson.toString();

        try {
            jsonDataFile.createNewFile(); // 创建新文件
            //如果要追加写 new FileWriter(fileName, true)
            BufferedWriter out = new BufferedWriter(new FileWriter(jsonDataFile));
            out.write(outStr); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
