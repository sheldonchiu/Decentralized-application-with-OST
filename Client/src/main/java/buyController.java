import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class buyController {

    @FXML
    private TextField buyAmount;

    @FXML
    protected void buyConfirm(ActionEvent event){
        Window owner = buyAmount.getScene().getWindow();
        try{
            Float input = Float.valueOf(buyAmount.getText());
            if(UserInteract.buy(input))
                ((Stage)buyAmount.getScene().getWindow()).close();
            else
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Failure",
                        "Error");
        }
        catch (NumberFormatException e){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Amount Error",
                    "Please enter a valid amount");
        }
    }
}
