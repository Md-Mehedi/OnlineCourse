/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Global.Customize.HoverEffect;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Language;
import Course.Overflow.Global.Page.PageName;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ProfileSettingController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField headline;
    @FXML
    private TextField language;
    @FXML
    private TextArea biography;
    @FXML
    private TextField website;
    @FXML
    private TextField facebook;
    @FXML
    private TextField youtube;
    @FXML
    private ImageView photo;
    @FXML
    private Label imageNameLabel;
    @FXML
    private Button upload;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton cancel;
    private ArrayList<Language> selectedLanguage;
    @FXML
    private TextField email;
    @FXML
    private TextField oldPass;
    @FXML
    private TextField newPass;
    @FXML
    private TextField newPassAgain;
    @FXML
    private VBox securityBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedLanguage = new ArrayList<>();
        addLanguage();
        addListener();
    }    

    private void addLanguage() {
        ArrayList<Language> list = Language.getList();
        VBox container = new VBox();
        AnchorPane root = new AnchorPane(container);
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        
        for(Language l : list){
            CheckBox cb = new CheckBox(l.getName());
            container.getChildren().add(cb);
            cb.setStyle(cb.getStyle() + "-fx-font-size: 18;");
            cb.setOnMouseClicked((event) -> {
                if(cb.isSelected()){
                    selectedLanguage.add(l);
                }
                else{
                    selectedLanguage.remove(l);
                }
                refreshLanguageString();
            });
        }
        
        new HoverEffect(language, root) {
            @Override
            public void setLocation() {
                this.defaultLocation();
            }
        };
        
        Platform.runLater(()->{
            root.setStyle(
                  root.getStyle()
                  + "-fx-background-color: white;"
                  + "-fx-pref-width: 350;"
            );
            System.out.println(language.getPrefWidth() + 100);
            container.setStyle(
                  container.getStyle()
                  + "-fx-alignment: center-left;"
                  + "-fx-spacing: 5; "
            );
        });
    }

    private void refreshLanguageString() {
        String text = "";
        String[] languages = new String[selectedLanguage.size()];
        
        for(int i=0; i<selectedLanguage.size(); i++){
            languages[i] = selectedLanguage.get(i).getName();
        }
        Arrays.sort(languages);
        for(String name : languages){
            if(text == ""){
                text = name;
            }
            else{
                text += ", " + name;
            }
        }
        language.setText(text);
    }

    public void createEnvironmentForSignup() {
        VBox parent = (VBox) securityBox.getParent();
        parent.getChildren().remove(securityBox);
        save.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.Home);
        });
    }

    private void addListener() {
        save.setOnMouseClicked(event ->{
            
        });
    }
    
}
