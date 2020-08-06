/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Curriculum;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Customize.PDF;
import com.qoppa.pdf.PDFException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PDFShowBoxController implements Initializable {

      @FXML
      private AnchorPane container;
      @FXML
      private ImageView pdfThumbView;
      @FXML
      private VBox openBtn;
      @FXML
      private Label fileNameLabel;
      @FXML
      private Label descriptionLabel;
      @FXML
      private Label description;
      @FXML
      private Button updateBtn;
      private LectureBoxController parent;
      private File file;
      private File oldFile;
      private PDF pdf;

      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            openBtn.setOpacity(0);
            description.setPrefWidth(GLOBAL.LABEL_PREF_WIDTH-pdfThumbView.getFitWidth()-20);
            fileNameLabel.setPrefWidth(GLOBAL.LABEL_PREF_WIDTH-pdfThumbView.getFitWidth()-20);
      }      

      void setParent(LectureBoxController parent) {
            this.parent = parent;
      }

      @FXML
      private void mouseReleased(MouseEvent event) {
            
      }


      @FXML
      private void mouseClicked(MouseEvent event) throws IOException, Exception {
            Object src = event.getSource();
            if(src == updateBtn){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/CourseContentsUploader.fxml"));
                  AnchorPane pane = loader.load();
                  loader.<CourseContentsUploaderController>getController().setParent(parent, "pdf", false);
                  loader.<CourseContentsUploaderController>getController().setFile(file);
                  loader.<CourseContentsUploaderController>getController().setDescription(description.getText());
                  parent.getAvailableContentContainer().getChildren().remove(container);
                  parent.getAvailableContentContainer().getChildren().add(pane);
            } else if(src == openBtn){
                  pdf.openPDFfile(file);
                  openBtn.setVisible(false);
            }
      }

      void setFile(File f) throws PDFException {
            this.file = f;
            this.oldFile = f;
            fileNameLabel.setText(file.getName());
            pdf = new PDF(file);
            pdfThumbView.setImage(pdf.getThumbnail());
      }

      void setDescription(String description) {
            this.description.setText(description);
      }

      @FXML
      private void mouseEntered(MouseEvent event) {
            openBtn.setOpacity(1);
      }
      
      @FXML
      private void mouseExited(MouseEvent event) {
            openBtn.setOpacity(0);
      }
      
}
