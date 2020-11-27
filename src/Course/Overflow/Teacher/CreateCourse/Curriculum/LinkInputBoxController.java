/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Global.ToolKit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class LinkInputBoxController implements Initializable {

    private LectureBoxController parent;
    private String oldLink;
    private String oldLinkDesc;

    private boolean isNew;
    @FXML
    private TextArea linkDescField;
    @FXML
    private TextField linkField;
    @FXML
    private AnchorPane container;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Tools.makeDynamicTextArea(linkDescField);
    }

    void setParent(LectureBoxController parent, boolean isNew) {
        this.parent = parent;
        this.isNew = isNew;
    }

    @FXML
    private void mouseClicked(MouseEvent event) throws IOException {
        Object src = event.getSource();
        if (src == saveBtn) {
            if(!isPassedCondition()) return;
            parent.addLinkOutputBox(linkField.getText(), linkDescField.getText());
        } else if (src == cancelBtn && isNew == false) {
            parent.addLinkOutputBox(oldLink, oldLinkDesc);
        } else if (src == cancelBtn) {
            parent.getAvailableContentContainer().getChildren().remove(container);
            parent.setCancelVisible(false);
            parent.setContentVisible(true);
        }
    }

    void setLink(String text) {
        linkField.setText(text);
        oldLink = text;
    }

    void setLinkDetails(String text) {
        linkDescField.setText(text);
        oldLinkDesc = text;
    }

    public boolean isPassedCondition() {
        if (linkField.getText().equals("")) {
            ToolKit.showWarning("Link field can't be empty");
            return false;
        }
        if (!(5 < linkDescField.getText().length() && linkDescField.getText().length() <= 1000)) {
            ToolKit.showWarning("Link description have to be between 50 to 1000 charecters");
            return false;
        }
        return true;
    }

}
