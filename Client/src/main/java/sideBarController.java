import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import jfxtras.styles.jmetro8.JMetro;

import java.io.IOException;
import java.util.ArrayList;

public class sideBarController {

    @FXML
    private ListView<String> sideBox;

    @FXML
    private BorderPane mainPane;

    @FXML
    private VBox titleBox;

    @FXML
    private AnchorPane topBar;

    public Parent accountPage;

    public accountController ac;

    public Parent shopPage;
    public shopController sc;

    public projectDetailController detailController;

    private void createPage(){
        try {
            FXMLLoader temp = new FXMLLoader(getClass().getResource("accountPage.fxml"));
            accountPage = temp.load();
            ac = temp.getController();
            temp = new FXMLLoader(getClass().getResource("shopPage.fxml"));
            shopPage = temp.load();
            sc = temp.getController();
            ArrayList<ProjectItem> list = Server.getList();
            if(list != null){
                sc.products = list;
                sc.populateProducts();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void resize(){
        double width = sideBox.getScene().getWindow().getWidth();
        double height = sideBox.getScene().getWindow().getHeight();
        sideBox.setPrefWidth(width / 8);
        titleBox.setPrefWidth(width / 8);
        topBar.setPrefHeight(height/16);
    }

    public void ready(){
        sideBox.setItems(FXCollections.observableArrayList (
                "Shop", "Account"));
        sideBox.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> loadUI(new_val.toLowerCase()));
        createPage();
        sideBox.getSelectionModel().selectFirst();
    }

//load other
    public void loadUI(String ui){
        Parent root;
        try {
            if(ui.equals("detail")){
                sideBox.getSelectionModel().clearSelection();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("projectDetail.fxml"));
                root = loader.load();
                detailController = loader.getController();
            }
            else
                root = (Parent) this.getClass().getField(ui + "Page").get(this);

            new JMetro(JMetro.Style.LIGHT).applyTheme(root);
            mainPane.setCenter(root);
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
