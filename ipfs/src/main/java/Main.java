import org.json.JSONObject;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String jsonString = new JSONObject()
                .put("charityName", "International AIDS Society")
                .put("projectName", "HIV Vaccination in Chiang Rai, Thailand")
                .put("category", "Health")
                .put("target", "50000")
                .put("duration", "1")
                .put("projectDescription","Let more people be able to enjoy the help of HIV vaccination").toString();

        FileOutputStream out =new FileOutputStream("final1.txt");
        out.write(jsonString.getBytes());
        out.close();

    }
}
