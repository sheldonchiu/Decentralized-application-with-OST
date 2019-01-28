import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

import java.io.IOException;

public class Main extends Application {

    private static Stage mainStage = null;

    public static Stage getStage(){return  mainStage;}

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        UserInteract.init();
        Server.connect();
//        Server.getFile("QmRepn19djbih1zLh22r3LFo6VV32jR5kYjXAR5zRS7b5W");
//        Server.addUser("1","1","1");
//        UserInteract.getIdLsit();
//        UserInteract.test();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
//        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
        primaryStage.setTitle("Charity");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
