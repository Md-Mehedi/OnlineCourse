/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Communication;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MessengerController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Label teacherName;
    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox messageContainer;
    @FXML
    private FontAwesomeIconView addImageBtn;
    @FXML
    private TextArea inputField;
    @FXML
    private FontAwesomeIconView sendBtn;

    private Circle cl;
    private Circle cr;
    private Label lm;
    private ImageView iv;
    private HBox box;
    
    private Image userImage;
    private Image opponentImage;
    @FXML
    private AnchorPane scrollingPane;

    
    public enum MessageType{
        TEXT("TEXT"),
        IMAGE("IMAGE");
        String s;
        private MessageType(String s) {
            this.s = s;
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        messageContainer.getChildren().clear();
        GLOBAL.scrollingPane = scrollingPane;
        userImage = new Image(GLOBAL.PICTURE_LOCATION + "/Person 1.jpg");
        opponentImage = new Image(GLOBAL.PICTURE_LOCATION + "/Person 2.jpg");
        addListener();
        
        buildSomeDefaultMessaging();
    }    
    
    private void addListener(){
        inputField.setOnKeyReleased(event->{
            if(event.getCode() == KeyCode.ENTER){
                sendAMessage();
            }
        });
        sendBtn.setOnMouseClicked((event) -> {
            sendAMessage();
        });
        addImageBtn.setOnMouseClicked((event) -> {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(null);
            String destURL = GLOBAL.ROOT_LOCATION + GLOBAL.PICTURE_LOCATION + "/Message_101_" + file.getName();
            try {
                Files.copy(file.toPath(), (new File(destURL)).toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(MessengerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Image image = new Image((new File(destURL)).toURI().toString());
            
            makeMessage(image);
            setUpPersonImage(true);
        });
    }
    
    private void sendAMessage(){
        if(!inputField.getText().equalsIgnoreCase("")){
            String text = inputField.getText();
            inputField.clear();
            makeMessage(text);
            setUpPersonImage(!text.startsWith("2"));
        }
    }
    
    private void createAMessageBox(MessageType type){
        cl = new Circle(30);
        cr = new Circle(30);
        if(type == MessageType.IMAGE){
            iv = new ImageView();
            iv.setFitWidth(580);
            iv.setPreserveRatio(true);
            ToolKit.setTooltip(iv, "22 September, 2020 - 12:06 AM", GLOBAL.rootPane);
            box = new HBox(cl,iv,cr);
        }
        else if(type == MessageType.TEXT){
            lm = new Label();
            ToolKit.setTooltip(lm, "22 September, 2020 - 12:06 AM", GLOBAL.rootPane);
            box = new HBox(cl,lm,cr);
        }
        box.getStyleClass().add("message1");
        messageContainer.getChildren().add(box);
        scroll.setVvalue(1);
    }
    
    private void makeMessage(String text){
        createAMessageBox(MessageType.TEXT);
        lm.setText(text);
    }
    
    private void makeMessage(Image image){
        createAMessageBox(MessageType.IMAGE);
        iv.setImage(image);
    }
    
    private void setUpPersonImage(boolean isUser){
        if(isUser){
            cl.setOpacity(0);
            cr.setFill(new ImagePattern(userImage));
            box.getStyleClass().add("message1");
        }
        else{
            cr.setOpacity(0);
            cl.setFill(new ImagePattern(opponentImage));
            box.getStyleClass().add("message2");
        }
    }
    
    private void buildSomeDefaultMessaging() {
        for(int i=0; i<50; i++){
            int ran;
            String text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s";
            String text2 = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable.";
            ran = (int)(Math.random()*1000)%2;
            if(ran == 0){
                ran = (int)(Math.random()*1000)%2;
                makeMessage(ran==0 ? text1 : text2);
                ran = (int)(Math.random()*1000)%2;
                setUpPersonImage(ran==0 ? true : false);
            }
            else{
                ran = (int)(Math.random()*1000)%2;
//                makeMessage(new Image(GLOBAL.PICTURE_LOCATION + "/Message_101_" + (ran==0 ? "2" : "3") + (ran==0 ? ".JPG" : ".jpg")));
                makeMessage(new Image(GLOBAL.PICTURE_LOCATION + "/Congratulation 2.jpg"));
                ran = (int)(Math.random()*1000)%2;
                setUpPersonImage(ran==0 ? true : false);
            }
        }
    }
    void loadMessage() {
        messageContainer.getChildren().clear();
        buildSomeDefaultMessaging();
    }
}
