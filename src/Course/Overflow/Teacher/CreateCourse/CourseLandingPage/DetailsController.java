/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.CourseLandingPage;

import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.ToolKit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DetailsController implements Initializable {

      @FXML
      private ChoiceBox<String> languageCB;
      @FXML
      private ChoiceBox<String> selectLevelCB;
      @FXML
      private ChoiceBox<String> mainCategoryCB;
      @FXML
      private ChoiceBox<String> subCategoryCB;
      @FXML
      private AnchorPane container;
      @FXML
      private TextField courseTitle;
      @FXML
      private TextField courseSubTitle;
      @FXML
      private TextArea courseDescription;
      @FXML
      private TextField primTaught;
      @FXML
      private ImageView courseImage;
      @FXML
      private Label courseImageLabel;
      @FXML
      private Button courseImageUpBtn;
      @FXML
      private ImageView courseVideoImage;
      @FXML
      private MediaView courseVideoMV;
      @FXML
      private Label courseVideoLabel;
      @FXML
      private Button courseVideoUpBtn;
      @FXML
      private HBox langContainer;
      private ImageView add;
      private ObservableList langList;
      @FXML
      private ImageView addLangIcon;
      @FXML
      private FontAwesomeIconView taughtInfoIcon;
      @FXML
      private FontAwesomeIconView imageInfoIcon;
      @FXML
      private FontAwesomeIconView videoInfoIcon;
      @FXML
      private Label ct;
      private AnchorPane parentPane;

      /**
       * Initializes the controller class.
       */
      @Override
      public void initialize(URL url, ResourceBundle rb){
            readyLanguageCB();
            readyLevel();
            readyCategory();
            readySubCategory();
            new ToolTip(MouseEvent.MOUSE_ENTERED, imageInfoIcon, "Make your course stand out with a great image. The "
                  + "image must maintain 16:9 ratio and .jpg, .jpeg, .gif or .png format");
            new ToolTip(MouseEvent.MOUSE_ENTERED, videoInfoIcon, "Make your course attractive with a promo video. "
                  + "Students who watch a well-made promo video are 5x more likely to erroll "
                  + "in your course.");
            new ToolTip(MouseEvent.MOUSE_ENTERED, addLangIcon, "Add secondary language. If you have subtitles of "
                  + "different language in your course video.");
            new ToolTip(MouseEvent.MOUSE_ENTERED, taughtInfoIcon, "Each individual topic chosen should comprehensively"
                        + " describe your course's content without being too broad. E.g. \"The Complete"
                        + " Tennis Course\" should have \"Tennis\" – not \"Tennis Serve\" (specific, but"
                        + " not comprehensive) and not \"Sports\" (comprehensive, but not specific).");
      }
      
      @SuppressWarnings("unchecked")
      private void readyLanguageCB(){
            langList = FXCollections.observableArrayList();
            langList.add("--Select Language--");
            langList.addAll("English", "Bangla", "Spanish", "Japanese", "Hindi", "Urdu");
            languageCB.setItems(langList);
            languageCB.setValue("--Select Language--");
      }

      @SuppressWarnings("unchecked")
      private void readyLevel() {
            ObservableList list = FXCollections.observableArrayList();
            list.add("--Select Level--");
            list.addAll("Beginner", "Medium", "Pro");
            selectLevelCB.setItems(list);
            selectLevelCB.setValue("--Select Level--");
      }

      @SuppressWarnings("unchecked")
      private void readyCategory() {
            ObservableList list = FXCollections.observableArrayList();
            list.add("--Select Main Category--");
            list.addAll("Programming", "Finance", "Language Learning");
            mainCategoryCB.setItems(list);
            mainCategoryCB.setValue("--Select Main Category--");
      }

      @SuppressWarnings("unchecked")
      private void readySubCategory() {
            ObservableList list = FXCollections.observableArrayList();
            list.add("--Select Sub Category--");
            list.addAll("1","2","3");
            subCategoryCB.setItems(list);
            subCategoryCB.setValue("--Select Sub Category--");
      }

      @FXML
      private void mouseExited(MouseEvent event) {
      }

      @FXML
      private void mouseEntered(MouseEvent event) {
            Object src = event.getSource();
      }

      @FXML
      private void mouseClicked(MouseEvent event) throws FileNotFoundException {
            Object src = event.getSource();
            
            if(src == courseImageUpBtn){
                  File file = ToolKit.chooseFile("image");
                  if(file != null){
                        courseImage.setImage(new Image(new FileInputStream(file)));
                        courseImageLabel.setText(file.getName());
                  }                        
            } else if(src == courseVideoUpBtn){
                  File file = ToolKit.chooseFile("video");
                  if(file!=null){
                        courseVideoImage.visibleProperty().set(false);
                        courseVideoLabel.setText(file.getName());
                        courseVideoMV.setMediaPlayer(ToolKit.getMediaPlayer(file));
                  }
            } else if(src == addLangIcon){
                  ChoiceBox<String> newLang = new ChoiceBox<>(langList);
                  langContainer.getChildren().add(langContainer.getChildren().size()-1, newLang);
                  newLang.setValue((String) langList.get(0));
                  newLang.getStyleClass().add("choiceBox");
                  if(langContainer.getChildren().size()==4) langContainer.getChildren().remove(addLangIcon);
            }
      }      
}
