/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.TargetStudentPage;

import Course.Overflow.Global.ToolKit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AddAnswerController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private TextField answerField;
    @FXML
    private Label deleteIcon;
    @FXML
    private Label upIcon;
    @FXML
    private Label downIcon;

    private VBox parentContainer;
    private TargetStudentPageController parentController;
    private AnchorPane mainContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void mouseExited(MouseEvent event) {
    }

    @FXML
    private void mouseEntered(MouseEvent event) {
    }

    @FXML
    private void mouseClicked(MouseEvent event) {
        Object src = event.getSource();
        if (src == deleteIcon) {
            parentContainer.getChildren().remove(container);
            parentController.removeCourseLearningCtrl(this);
            parentController.removeRequCtrl(this);
        } else if (src == upIcon) {
            ToolKit.moveRow(parentContainer, parentContainer.getChildren().indexOf(container), -1);            
        } else if (src == downIcon) {
            ToolKit.moveRow(parentContainer, parentContainer.getChildren().indexOf(container), 1);
        }
    }

    public void setParent(TargetStudentPageController parentController, VBox parentContainer, AnchorPane mainContainer) {
        this.parentContainer = parentContainer;
        this.parentController = parentController;
        this.mainContainer = mainContainer;
    }
    
    public String getValue(){
        return answerField.getText();
    }
    
    public void loadData(String text){
        answerField.setText(text);
    }
}
