/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Global.ToolKit;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ArticleInputBoxController implements Initializable {

    private LectureBoxController parent;
    private boolean isNew;
    @FXML
    private AnchorPane container;
    @FXML
    private JFXTextArea article;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private JFXTextField title;
    private String oldArticle;
    private String oldTitle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Tools.makeDynamicTextArea(article);
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
            parent.addArticleOutputBox(title.getText(), article.getText());
        } else if (src == cancelBtn && isNew == false) {
            parent.addArticleOutputBox(oldTitle, oldArticle);
        } else if (src == cancelBtn && isNew == true) {
            parent.getAvailableContentContainer().getChildren().remove(container);
            parent.setCancelVisible(false);
            parent.setContentVisible(true);
        }
    }

    void setArticle(String text) {
        article.setText(text);
        oldArticle = text;
    }

    void setTitle(String text) {
        title.setText(text);
        oldTitle = text;
    }
    
    public boolean isPassedCondition(){
        if(title.getText().equals("")){
            ToolKit.showWarning("Title can not be empty.");
            return false;
        }
        if(!(5 < article.getText().length() && article.getText().length() < 1000)){ //Here need an update.. 5 -> 50
            ToolKit.showWarning("Articles must be between 50 to 1000 charecter.");
            return false;
        }
        return true;
    }

}
