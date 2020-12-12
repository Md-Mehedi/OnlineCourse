/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Communication;

import Course.Overflow.Files.FileType;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.PersonPreviewController;
import Course.Overflow.Global.ToolKit;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
    private JFXTextArea inputField;
    @FXML
    private FontAwesomeIconView sendBtn;

    private Label lm;
    private ImageView iv;
    private HBox box;
    
    private Image userImage;
    private Image opponentImage;
    @FXML
    private AnchorPane scrollingPane;
    private MessagePage parent;
    private Person receiver;
    private Person sender;
    private ChatHeadContainerController chatHeadCtrl;
    private AnchorPane chatHeadBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        messageContainer.getChildren().clear();
        addListener();
        Platform.runLater(()->{
            messageContainer.setPrefHeight(GLOBAL.HEIGHT - GLOBAL.HEADER.getRoot().getPrefHeight() - 110);
            System.out.println(GLOBAL.HEIGHT - GLOBAL.HEADER.getRoot().getPrefHeight() - 110);
        });
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
            Message message = new Message(sender, receiver, files);
            makeMessage(message);
            chatHeadCtrl.popUpChatHead(chatHeadBox, message);
        });
    }
    
    private void sendAMessage(){
        if(!inputField.getText().equalsIgnoreCase("")){
            String text = inputField.getText().trim();
            inputField.clear();
            Files files = new Files(FileType.MESSAGE, "Message", text);
            Message message = new Message(sender, receiver, files);
            makeMessage(message);
            chatHeadCtrl.popUpChatHead(chatHeadBox, message);
        }
    }
    
    private void createAMessageBox(Files file, boolean isUser){
        ImageView cl = new ImageView(opponentImage);
        cl.setFitWidth(60);
        cl.setFitHeight(60);
        ToolKit.makeCircularImage(cl);
        
        ImageView cr = new ImageView(userImage);
        cr.setFitWidth(60);
        cr.setFitHeight(60);
        ToolKit.makeCircularImage(cr);
//        Circle cl = new Circle(30);
//        Circle cr = new Circle(30);
////        cl.setFill(new ImagePattern(ToolKit.makeImage(opponentImage)));
////        cr.setFill(new ImagePattern(ToolKit.makeImage(userImage)));
        
        
        if(file.getType() == FileType.PICTURE){
            iv = new ImageView(ToolKit.makeImage(file));
            iv.setFitWidth(580);
            iv.setPreserveRatio(true);
            ToolKit.setTooltip(iv, ToolKit.makeDateStructured(file.getUploadTime(), "dd MMMMM, yyyy - hh:mm aa"), GLOBAL.rootPane);
            box = new HBox(cl,iv,cr);
        }
        else if(file.getType() == FileType.MESSAGE){
            Text text = new Text(file.getContent());
            lm = new Label();
            lm.setGraphic(text);
            Platform.runLater(()->{
                if(text.getLayoutBounds().getWidth() > lm.getMaxWidth() - lm.getPadding().getLeft()*2){
                    text.setWrappingWidth(lm.getMaxWidth() - lm.getPadding().getLeft()*2);
                }
            });
            ToolKit.setTooltip(lm, ToolKit.makeDateStructured(file.getUploadTime(), "dd MMMMM, yyyy - hh:mm aa"), GLOBAL.rootPane);
            box = new HBox(cl,lm,cr);
        }
        if(isUser){
            cl.setOpacity(0);
            box.getStyleClass().add("message1");
        }
        else{
            cr.setOpacity(0);
            box.getStyleClass().add("message2");
        }
        messageContainer.getChildren().add(box);
    }
    
    private void makeMessage(Message message){
        createAMessageBox(message.getMessage(), message.getSenderId().equals(sender.getUsername()));
        Platform.runLater(()->{
            scroll.setVvalue(1);
        });
    }
    
    void loadMessage(Person person) {
        this.sender = ToolKit.getCurrentPerson();
        this.receiver = person;
        teacherName.setText(person.getFullName());
        teacherName.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.PersonDetails);
            PersonPreviewController ctrl = (PersonPreviewController) GLOBAL.PAGE_CTRL.getController();
            ctrl.loadData(person);
        });
        messageContainer.getChildren().clear();
        userImage = ToolKit.makeImage(ToolKit.getCurrentPerson().getPhoto());
        opponentImage = ToolKit.makeImage(person.getPhoto());
        
        ArrayList<Message> messages = Message.getMessageList(sender, receiver);
        System.out.println(messages.size());
        for(int i=0; i<messages.size(); i++){
            System.out.println(i);
            makeMessage(messages.get(i));
        }
        inputField.setFocusTraversable(true);
    }

    void setParent(MessagePage parent) {
        this.parent = parent;
    }

    void setChatHeadCtrl(ChatHeadContainerController chatHeadCtrl) {
        this.chatHeadCtrl = chatHeadCtrl;
    }

    void setPrefWidth(double width) {
//        scroll.setMinWidth(width);
//        scrollingPane.setMinWidth(width);
    }

    void setChatHeadBox(AnchorPane box) {
        this.chatHeadBox = box;
    }
}
