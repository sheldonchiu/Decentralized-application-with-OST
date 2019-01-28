import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

class ProjectItem {

    public final String Id;
    public final String charityName;
    public final String projectName;
    public final String projectDescription;
    public final String projectAddress;
    public final String ProjectCategory;
    public final String Target;
    public LocalDateTime time;
    public String duration;
    public HashMap<String,String>  evidence;

    ProjectItem(JSONObject o){
        Id = o.getString("id");
        charityName = o.getString("charityName");
        projectName = o.getString("projectName");
        projectDescription = o.getString("projectDescription");
        projectAddress = o.getString("projectAddress");
        ProjectCategory = o.getString("category");
        Target = o.getString("target");
        String input = o.getString("timestamp");
        time = LocalDateTime.parse(input.substring(0,input.indexOf("+")));
        duration = o.getString("duration");
        if(!duration.equals("")){
            time = time.plusMonths(Long.parseLong(duration));
            LocalDateTime now = LocalDateTime.now();
            Long diff = now.until(time, ChronoUnit.DAYS);
            duration = diff.toString();
        }
        evidence = new HashMap<>();
        if(!o.isNull("evidence")){
            JSONArray t = o.getJSONArray("evidence");
            for(int i = 0 ; i < t.length(); ++i){
                JSONArray data = t.getJSONArray(i);
                evidence.put(data.getString(0),data.getString(1));
                }
            }
    }
}
