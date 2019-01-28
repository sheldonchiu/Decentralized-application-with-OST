import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.styles.jmetro8.JMetro;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class projectDetailController {

    @FXML
    private TabPane projectTab;

    @FXML
    private Text projectName;

    @FXML
    private Text charityName;

    @FXML
    private Text amount;

    @FXML
    private Text description;

    @FXML
    private Text projectAddress;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private Text category;

    @FXML
    private Label date;

    @FXML
    private TableView<transactionFx> table;

    @FXML
    private GridPane proveGrid;

    private final Integer maxCol = 3;
    private final Integer[] gridSize = {0,0};  //col ,row

    public ObservableList<transactionFx> otable = null;

    private ProjectItem item;

    public double balance;

    private Integer target;
    private Timeline t;
    private Timer updateTable;

    public void init(ProjectItem item){
        this.item = item;
        projectName.setText(item.projectName);
        charityName.setText("-"+ item.charityName);
        projectAddress.setText(item.projectAddress);
        description.setText(item.projectDescription);
        category.setText("#" + item.ProjectCategory);
        date.setText("Remaining Time:\t" + item.duration + " days");
        target = Integer.valueOf(item.Target);
        update();
        progressbar.setProgress(balance / target);
        t = new Timeline();
        t.getKeyFrames().add(new KeyFrame(Duration.seconds(10), actionEvent -> update()));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
        updateTable();
        for(String key : item.evidence.keySet()){
            VBox file = new VBox(10);
            ImageView img = new ImageView("file.png");
            img.setStyle("-fx-background-color:transparent");
            Label prove = new Label(key);
            prove.paddingProperty().setValue(new Insets(0,0,0,5));
            file.getChildren().addAll(img, prove);
            file.setOnMouseClicked(this::downloadFile);
            proveGrid.add(file,gridSize[0],gridSize[1],1,1);
            if(++gridSize[0] >= maxCol){
                gridSize[0] = 0;
                ++gridSize[1];
            }
        }
    }

    private void updateTable(){
        updateTable = new Timer();
        otable = FXCollections.observableArrayList(new ArrayList<>());
        table.setItems(otable);
        updateTable.schedule(new TimerTask() {
            @Override
            public void run() {
                UserInteract.readTransaction(item.Id);
                otable.clear();
                otable.addAll(UserInteract.projectTable);
            }
        }, 0, 10000);
//        table.setColumnResizePolicy((param) -> true );
//        Platform.runLater(() -> helper.customResize(table));
    }

    private void update(){
        balance =  UserInteract
                ._getBalance(item.Id);
        amount.setText("Progress:\t" + balance + " / " + target);
        progressbar.setProgress(balance / target);
        LocalDateTime now = LocalDateTime.now();
        Long diff = now.until(item.time, ChronoUnit.DAYS);
        if(!item.duration.equals(diff.toString())){
            item.duration = diff.toString();
            date.setText("Remaining Time:\t" + item.duration + " days");
        }
    }
    @FXML
    protected void downloadFile(MouseEvent event){
        VBox temp = (VBox) event.getSource();
        String text = ((Label)temp.getChildren().get(1)).getText();
        Server.getFile(text, "http://localhost:8080/ipfs/" + item.evidence.get(text));
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, Main.getStage(), "Download",
                "File download started");
    }

    @FXML
    protected void copy(MouseEvent event){
        helper.copy(event);
    }

    @FXML
    protected void donate(ActionEvent event){
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("donatePage.fxml"));
            root = loader.load();
            DonateController c = loader.getController();
            c.init(item);
            new JMetro(JMetro.Style.LIGHT).applyTheme(Objects.requireNonNull(root));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 500, 350));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
