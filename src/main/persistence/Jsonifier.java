package persistence;


import jdk.nashorn.internal.runtime.JSONFunctions;
import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", tag.getName());
        return jsonObject;

    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("important", priority.isImportant());
        jsonObject.put("urgent", priority.isUrgent());
        return jsonObject;

    }

    // EFFECTS: returns JSON representation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        Calendar calendar = Calendar.getInstance();

        JSONObject jsonObject = new JSONObject();

        if (dueDate == null) {
            return jsonObject;
        } else {
            calendar.setTime(dueDate.getDate());
            jsonObject.put("year", calendar.get(Calendar.YEAR));
            jsonObject.put("month", calendar.get(Calendar.MONTH));
            jsonObject.put("day", calendar.get(Calendar.DATE));
            jsonObject.put("hour", calendar.get(Calendar.HOUR_OF_DAY));
            jsonObject.put("minute", calendar.get(Calendar.MINUTE));
            return jsonObject;
        }

    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {

        JSONObject jsonObject = new JSONObject();

        JSONArray arrTagsJson = new JSONArray();

        if (task.getTags().size() > 0) {
            for (Tag t :
                    task.getTags()) {
                arrTagsJson.put(Jsonifier.tagToJson(t));
            }
        }


        jsonObject.put("description", task.getDescription());
        jsonObject.put("tags", arrTagsJson);
        if (task.getDueDate() != null) {
            jsonObject.put("due-date", Jsonifier.dueDateToJson(task.getDueDate()));
        } else {
            jsonObject.put("due-date", JSONObject.NULL);
        }
        jsonObject.put("priority", Jsonifier.priorityToJson(task.getPriority()));
        jsonObject.put("status", task.getStatus().toString().replace(" ", "_"));

        return jsonObject;
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {

        JSONArray arr = new JSONArray();

        if (tasks.size() > 0) {
            for (Task t :
                    tasks) {
                arr.put(Jsonifier.taskToJson(t));

            }
        }

        return arr;   // stub
    }
}