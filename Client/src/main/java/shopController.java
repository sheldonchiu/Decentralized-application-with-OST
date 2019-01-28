import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class shopController {

    @FXML
    public GridPane shopGrid;

    @FXML
    public ScrollPane shopScroll;

    public ArrayList<ProjectItem> products = null;
    private final Integer[] gridSize = {0,0};  //col ,row
    private final Integer maxCol = 3;

    @FXML
    public void initialize() {
        products = new ArrayList<>();
        setProductFetch();
    }

    public String getProjectName(String id){
        for (ProjectItem p : products)
            if(id.equals(p.Id))
                return p.projectName;
    return null;
    }

    public void populateProducts(){
        gridSize[0] = 0;
        gridSize[1] = 0;
        for( ProjectItem i : products){
            try {
                FXMLLoader projectLoader = new FXMLLoader(getClass().getResource("projectItem.fxml"));
                Node item = projectLoader.load();
                projectItemController con = projectLoader.getController();
                con.setItem(i);
                shopGrid.add(item,gridSize[0],gridSize[1],1,1);
                if(++gridSize[0] >= maxCol){
                    gridSize[0] = 0;
                    ++gridSize[1];
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void setProductFetch(){
        Timeline productUpdate = new Timeline();
        productUpdate.getKeyFrames().add(new KeyFrame(Duration.minutes(1), actionEvent -> {
            ArrayList<ProjectItem> temp = Server.getList();
            if(temp == null)
                System.out.println("Empty or Error");
            else if(temp.size() != products.size()) {
                products = temp;
                populateProducts();
            }
        }));
        productUpdate.setCycleCount(Animation.INDEFINITE);
        productUpdate.play();
    }

}
