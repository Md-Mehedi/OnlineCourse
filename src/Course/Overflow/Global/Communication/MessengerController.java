/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Communication;

import Course.Overflow.Files.FileType;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.ToolKit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    private Label lm;
    private ImageView iv;
    private HBox box;
    
    private Files userImage;
    private Files opponentImage;
    @FXML
    private AnchorPane scrollingPane;
    private MessagePage parent;
    private Person receiver;
    private Person sender;

    
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
//        userImage = new Image(GLOBAL.PICTURE_LOCATION + "/Person 1.jpg");
//        opponentImage = new Image(GLOBAL.PICTURE_LOCATION + "/Person 2.jpg");
        addListener();
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
            File file = ToolKit.chooseFile(FileType.PICTURE);
            if(file == null){
                return;
            }
            Files files = new Files(file, FileType.PICTURE, "Message");
            makeMessage(new Message(sender, receiver, files));
        });
    }
    
    private void sendAMessage(){
        if(!inputField.getText().equalsIgnoreCase("")){
            String text = inputField.getText();
            inputField.clear();
            Files files = new Files(FileType.MESSAGE, "Message", text);
            makeMessage(new Message(sender, receiver, files));
//            setUpPersonImage(!text.startsWith("2"));
        }
    }
    
    private void createAMessageBox(Files file, boolean isUser){
        ImageView cl = new ImageView();
        ImageView cr = new ImageView();
        cl.setFitWidth(60);
        cl.setFitHeight(60);
        cr.setFitWidth(60);
        cr.setFitHeight(60);
        cl.setStyle("-fx-background-radius: 30");
        cr.setStyle("-fx-background-radius: 30");
        
        if(file.getType() == FileType.PICTURE){
            iv = new ImageView(ToolKit.makeImage(file));
            iv.setFitWidth(580);
            iv.setPreserveRatio(true);
            ToolKit.setTooltip(iv, ToolKit.makeDateStructured(file.getUploadTime(), "dd MMMMM, yyyy - hh:mm aa"), GLOBAL.rootPane);
            box = new HBox(cl,iv,cr);
        }
        else if(file.getType() == FileType.MESSAGE){
            lm = new Label(file.getContent());
            ToolKit.setTooltip(lm, ToolKit.makeDateStructured(file.getUploadTime(), "dd MMMMM, yyyy - hh:mm aa"), GLOBAL.rootPane);
            box = new HBox(cl,lm,cr);
        }
        if(isUser){
            cl.setOpacity(0);
            ToolKit.print("Right side");
            cr.setImage(ToolKit.makeImage(userImage));
            box.getStyleClass().add("message1");
        }
        else{
            cr.setOpacity(0);
            ToolKit.print("Left side");
            cl.setImage(ToolKit.makeImage(opponentImage));
            box.getStyleClass().add("message2");
        }
        box.getStyleClass().add("message1");
        messageContainer.getChildren().add(box);
    }
    
    private void makeMessage(Message message){
        createAMessageBox(message.getMessage(), message.getSenderId().equals(sender.getUsername()));
//        setUpPersonImage(message.getSenderId().equals(sender.getUsername()));
        Platform.runLater(()->{
            scroll.setVvalue(1);
        });
    }
    
//    private void setUpPersonImage(boolean isUser){
//        if(isUser){
//            cl.setOpacity(0);
//            cr.setFill(new ImagePattern(ToolKit.makeImage(userImage)));
//            box.getStyleClass().add("message1");
//        }
//        else{
//            cr.setOpacity(0);
//            cl.setFill(new ImagePattern(ToolKit.makeImage(opponentImage)));
//            box.getStyleClass().add("message2");
//        }
//    }
    
    void loadMessage(Person person) {
        this.sender = ToolKit.getCurrentPerson();
        this.receiver = person;
        messageContainer.getChildren().clear();
        userImage = sender.getPhoto();
        opponentImage = person.getPhoto();
        
        ArrayList<Message> messages = Message.getMessageList(sender, receiver);
        for(int i=0; i<messages.size(); i++){
            makeMessage(messages.get(i));
        }
    }

    void setParent(MessagePage parent) {
        this.parent = parent;
    }
}
