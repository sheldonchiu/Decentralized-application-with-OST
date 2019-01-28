import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class projectItemController {

    @FXML
    private Label companyName;

    @FXML
    private Label projectName;

    @FXML
    public Label category;

    @FXML
    public Label status;

    public ProjectItem item;

    @FXML
    private Label time;

    public void setItem(ProjectItem item) {
        this.item = item;
        companyName.setText(item.charityName);
        projectName.setText(item.projectName);
        category.setText("#" + item.ProjectCategory);
        time.setText("Remaining Time:\t" + item.duration + " days");
        status.setText("Target: " + item.Target + "CYC");
    }

    @FXML
    protected void select(MouseEvent mouseEvent) {
        MainController.sideBarC.loadUI("detail");
        MainController.sideBarC.detailController.init(item);
    }
}
