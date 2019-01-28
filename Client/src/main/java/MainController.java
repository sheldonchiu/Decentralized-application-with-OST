import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import jfxtras.styles.jmetro8.JMetro;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField signUpUsername;

    @FXML
    private PasswordField password;
    @FXML
    private PasswordField signUpPassword;

    @FXML
    private TextField signUpEmail;

    @FXML
    private Button login;

    @FXML
    private Button SignUpConfirm;

    @FXML
    private CheckBox charityBox;

    public static sideBarController sideBarC;

    private void _login(){
        Window owner = login.getScene().getWindow();
        String pass = password.getText();
        if(pass.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Empty Error!",
                    "Please enter your password");
            return;
        }
        if(UserInteract.login(pass,owner))
            loadMainPage();
    }

    @FXML
    protected void login(ActionEvent event) {
        _login();
    }

    @FXML
    protected  void regPage(ActionEvent event){
        loadUI();
    }

    @FXML
    protected void reg(ActionEvent event){
        Window owner = SignUpConfirm.getScene().getWindow();
        String username = signUpUsername.getText();
        String pass = signUpPassword.getText();
        String email = signUpEmail.getText();
        if(!UserInteract.reg(username, pass, email, charityBox.isSelected()))
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "OST Error",
                    "Unable to create user in OST");
        else
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Successfully",
                    "Account successfully created");
        signUpUsername.setText("");
        signUpEmail.setText("");
        signUpPassword.setText("");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("login.fxml"));
            new JMetro(JMetro.Style.LIGHT).applyTheme(root);
            signUpUsername.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUI(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("signUp" + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setCenter(root);
    }

    private void loadMainPage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sideBar.fxml"));
        try {
            Parent root = loader.load();
            new JMetro(JMetro.Style.DARK).applyTheme(root);
            sideBarC = loader.getController();
            sideBarC.ready();
            Stage stage = Main.getStage();
            Scene s = new Scene(root, 1000, 500);
            s.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
            stage.setScene(s);
            stage.centerOnScreen();
            sideBarC.resize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void buttonPressed(KeyEvent e){
        if(e.getCharacter().equals("\r"))
            _login();
    }
}
