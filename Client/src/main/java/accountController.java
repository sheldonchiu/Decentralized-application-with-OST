import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class accountController {
    @FXML
    private Text name;

    @FXML
    private Text balance;

    @FXML
    private Text address;

    @FXML
    private TableView<transactionFx> table;

    public ObservableList<transactionFx> otable = null;

    public SimpleStringProperty balanceText = new SimpleStringProperty("");

    public TableView getTable(){return table;}

    public Timer updateTable;

    @FXML
    public void initialize() {
        name.setText(UserInteract.userName);
        address.setText(UserInteract.userAddress);
        balance.textProperty().bind(balanceText);
        linkTable();
    }

    private void linkTable(){
        updateTable = new Timer();
        updateTable.schedule(new TimerTask() {
            @Override
            public void run() {
                UserInteract.readTransaction("");
                otable.clear();
                otable.addAll(UserInteract.table);
                UserInteract.getBalance();
                balanceText.set(UserInteract.userBalance.toString());
            }
        }, 0, 10000);
        otable = FXCollections.observableArrayList(new ArrayList<>());
        table.setItems(otable);
        table.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> columnClick((new_val)));
//        table.setColumnResizePolicy((param) -> true );
//        Platform.runLater(() -> helper.customResize(table));
    }

    private void columnClick(transactionFx item){
        for (ProjectItem i : MainController.sideBarC.sc.products)
            if(i.projectName.equals(item.getTo())){
            MainController.sideBarC.loadUI("detail");
            MainController.sideBarC.detailController.init(i);
            return;
            }
        AlertHelper.showAlert(Alert.AlertType.ERROR, Main.getStage(), "Error",
                "Fail to retrieve project data");
    }

    @FXML
    protected void buyToken(ActionEvent event){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("buyWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new JMetro(JMetro.Style.LIGHT).applyTheme(Objects.requireNonNull(root));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 200, 200));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void copy(MouseEvent event){
        helper.copy(event);
    }
}
