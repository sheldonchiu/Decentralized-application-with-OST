import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.Body;
import javafx.scene.control.Alert;
import org.asynchttpclient.*;
import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private static final String ipfs = "http://localhost:8080/ipfs/";

    public static void connect(){
        Unirest.setObjectMapper(new ObjectMapper() {
            private final com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static ArrayList<ProjectItem> getList(){
        try {
            HttpResponse response = Unirest.get("http://localhost:8000/projects")
                    .asJson();
            ArrayList<ProjectItem> out = new ArrayList<>();
            JSONArray all =  ((JsonNode) response.getBody()).getArray();
            for(int i = 0; i <all.length(); ++i)
                out.add(new ProjectItem(all.getJSONObject(i)));
            return out;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getFile(String name, String hash){
        AsyncHttpClient client = Dsl.asyncHttpClient();
        try {
            FileOutputStream stream = new FileOutputStream(name);
            client.prepareGet(hash).execute(new AsyncCompletionHandler<FileOutputStream>() {

                @Override
                public State onBodyPartReceived(HttpResponseBodyPart bodyPart)
                        throws Exception {
                    stream.getChannel().write(bodyPart.getBodyByteBuffer());
                    return State.CONTINUE;
                }

                @Override
                public FileOutputStream onCompleted(Response response) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return stream;
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String id ,String address, String name, String email){
        User user = new User(id, address, name, email);

        try {
            Unirest.post("http://localhost:8000/projects")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(user)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    public static void regProj(String id ,String address){
        Project p = new Project(id, address);
        try {
            HttpResponse response = Unirest.post("http://localhost:8000/projects/reg")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(p)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
    public  static String getUserName(String id) {
        String res = MainController.sideBarC.sc.getProjectName(id);
        if(res != null)
            return res;
        HttpResponse<String> response = null;
        try {
            response = Unirest.get("http://localhost:8000/{id}")
                    .routeParam("id", id)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        res = response.getBody();
        res = res.replace("\"", "").replace("\"\n","");
        return res;
    }
}
