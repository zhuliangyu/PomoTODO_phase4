package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.YearMonth;
import java.util.*;

// Represents Task parser
public class TaskParser {

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {

        List<Task> listTask = new ArrayList<>();

        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(input);
        } catch (JSONException e) {
//            e.printStackTrace();
            return listTask;
        }

        for (Object object : jsonArray) {

            Task tk = new Task(" ");

            JSONObject taskJson = (JSONObject) object;

            try {
                setTask(tk, taskJson);
                listTask.add(tk);
            } catch (JSONException e) {
//                e.printStackTrace();
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }

        return listTask;

    }

    private void setTask(Task tk, JSONObject taskJson) {
        //set description
        String description = (String) taskJson.get("description");
        tk.setDescription(description);

        //set due day
        setDueDay(tk, taskJson);

        //set tags
        setTags(tk, taskJson);

        //set priority
        setPriority(tk, taskJson);

        //set Status
        setStatus(tk, taskJson);
    }

    private void setDueDay(Task tk, JSONObject taskJson) {
        if (taskJson.get("due-date").equals(null)) {
            tk.setDueDate(null);
        } else {
            JSONObject dueDateJson = (JSONObject) taskJson.get("due-date");
            DueDate dd = jsonToDueDay(dueDateJson);
            tk.setDueDate(dd);
        }

    }

    private void setTags(Task tk, JSONObject taskJson) {
        JSONArray tags = (JSONArray) taskJson.get("tags");
        for (Object t :
                tags) {
            JSONObject tag = (JSONObject) t;
            String strTag = (String) tag.get("name");
            tk.addTag(strTag);
        }
    }

    private void setStatus(Task tk, JSONObject taskJson) {
        String statusBefore = (String) taskJson.get("status");
        String statusAfter = statusBefore.toUpperCase();
        if (statusAfter.equalsIgnoreCase(Status.TODO.name())) {
            tk.setStatus(Status.TODO);
        } else if (statusAfter.equalsIgnoreCase(Status.UP_NEXT.name())) {
            tk.setStatus(Status.UP_NEXT);
        } else if (statusAfter.equalsIgnoreCase(Status.IN_PROGRESS.name())) {
            tk.setStatus(Status.IN_PROGRESS);
        } else if (statusAfter.equalsIgnoreCase(Status.DONE.name())) {
            tk.setStatus(Status.DONE);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void setPriority(Task tk, JSONObject taskJson) {
        JSONObject priorityJson = (JSONObject) taskJson.get("priority");
        Priority p = new Priority();
        Boolean isImportant = (boolean) priorityJson.get("important");
        Boolean isUrgent = (boolean) priorityJson.get("urgent");
        p.setImportant(isImportant);
        p.setUrgent(isUrgent);
        tk.setPriority(p);
    }

    private DueDate jsonToDueDay(JSONObject dueDateJson) {

        preJudgeDate(dueDateJson);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, (Integer) dueDateJson.get("year"));
        //todo 修改了-1
        cal.set(Calendar.MONTH, (Integer) dueDateJson.get("month"));
        cal.set(Calendar.DATE, (Integer) dueDateJson.get("day"));
        cal.set(Calendar.HOUR_OF_DAY, (Integer) dueDateJson.get("hour"));
        cal.set(Calendar.MINUTE, (Integer) dueDateJson.get("minute"));

        Date dt = cal.getTime();
        return (new DueDate(dt));
    }

    private void preJudgeDate(JSONObject dueDateJson) {
        Integer maxOfDay = YearMonth.of((Integer) dueDateJson.get("year"),
                (Integer) dueDateJson.get("month")).lengthOfMonth();
        if ((Integer) dueDateJson.get("month") < 1
                || (Integer) dueDateJson.get("month") > 12
                || (Integer) dueDateJson.get("hour") > 23
                || (Integer) dueDateJson.get("hour") < 0
                || (Integer) dueDateJson.get("minute") > 59
                || (Integer) dueDateJson.get("minute") < 0
                || (Integer) dueDateJson.get("day") > maxOfDay
                || (Integer) dueDateJson.get("day") < 1
                || (Integer) dueDateJson.get("year") < 1900
                || (Integer) dueDateJson.get("year") > 3000) {
            throw new IllegalArgumentException();
        }
    }


}