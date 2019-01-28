import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;

public class DonateController {

    @FXML
    private Text userAddress;

    @FXML
    private Text projectAddress;

    @FXML
    private TextField amount;

    @FXML
    private Text max;

    private String address;

    private String id;

    public void init(ProjectItem item){
        userAddress.setText(UserInteract.userAddress);
        projectAddress.setText(item.projectAddress + '\n' + item.projectName);
        max.setText("Max: " + UserInteract.userBalance);
        address = item.projectAddress;
        id = item.Id;
    }

    @FXML
    protected void confirm(ActionEvent event){
        Window owner = max.getScene().getWindow();
        Float money = null;
        try{
            money =  Float.valueOf(amount.getText());
        }
        catch (NumberFormatException e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Value is incorrect",
                    "Please enter a valid amount");
            return;
        }
        if(money > UserInteract.userBalance | money < 0) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Value is incorrect",
                    "Please enter a valid amount");
            return;
        }
        Window main = Main.getStage();
        while(money > 0){
            Float temp = money % 100 == 0? 100: money % 100;
            if(UserInteract.donate(id, temp))
                money -= temp;
            else{
                Float done = Float.valueOf(amount.getText()) - money;
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, main, "Failure",
                        "Transferred " + done.toString());
                ((Stage)amount.getScene().getWindow()).close();
                return;
            }
        }
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, main, "Success",
                "Thank you for the donation");
        ((Stage)amount.getScene().getWindow()).close();
    }

    @FXML
    protected void cancel(ActionEvent event){
        ((Stage)amount.getScene().getWindow()).close();
    }
}

