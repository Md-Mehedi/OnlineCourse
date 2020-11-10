/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ContentsListBoxController implements Initializable {

    @FXML
    private VBox articleBtn;
    @FXML
    private VBox videoBtn;
    @FXML
    private VBox pdfBtn;
    @FXML
    private VBox linkBtn;
    @FXML
    private AnchorPane container;

    private LectureBoxController parent;
    private FXMLLoader loader;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void mouseClicked(MouseEvent event) throws IOException {
        Object src = event.getSource();
        HBox box = (HBox) container.getParent();
        AnchorPane pane = new AnchorPane();
        if (src == articleBtn) {
            pane = parent.addArticleInputBox();
        } else if (src == videoBtn) {
            pane = parent.addVideoInputBox();
        } else if (src == pdfBtn) {
            pane = parent.addPDFInputBox();
        } else if (src == linkBtn) {
            pane = parent.addLinkInputBox();
        }
        box.getChildren().remove(container);
        box.getChildren().add(pane);
    }

    public void setParent(LectureBoxController parent) {
        this.parent = parent;
    }
}
