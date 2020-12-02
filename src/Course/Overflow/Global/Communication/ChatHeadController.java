/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Communication;

import Course.Overflow.Files.FileType;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.ToolKit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ChatHeadController implements Initializable {

    @FXML
    private Circle instPhoto;
    @FXML
    private Label instName;
    @FXML
    private Label lastMessage;
    @FXML
    private Label time;
    private MessengerController messengerCtrl;
    private Person person;
    private Message lMessage;
    @FXML
    private AnchorPane root;
    private ChatHeadContainerController parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setMessengerController(MessengerController messengerCtrl) {
        this.messengerCtrl = messengerCtrl;
    }

    void setPerson(Person person) {
        this.person = person;
        instPhoto.setFill(new ImagePattern(ToolKit.makeImage(person.getPhoto())));
        instName.setText(person.getFullName());
        root.setOnMouseClicked((event) -> {
            if(parent.getSelectedBox().getStyleClass().contains("selectedChatBox")){
                parent.getSelectedBox().getStyleClass().remove("selectedChatBox");
            }
            root.getStyleClass().add("selectedChatBox");
            parent.setSelectedBox(root);
            Message newLastMessage = Message.getLastMessage(ToolKit.getCurrentPerson(), person);
            if(newLastMessage == null){
                lastMessage.setText("Send a message to start the chat");
            }
            else if(!newLastMessage.getMessage().getContent().equals(lMessage.getMessage().getContent())){
                parent.popUpChatHead(root, newLastMessage);
            }
            messengerCtrl.setChatHeadBox(root);
            messengerCtrl.loadMessage(person);
        });
    }
    
    void setLastMessage(Message lMessage){
        this.lMessage = lMessage;
        if(lMessage == null){
            lastMessage.setText("Send a message to start the chat");
            time.setText("");
        }
        else{
            if(lMessage.getMessage().getType() == FileType.PICTURE){
                if(lMessage.getSenderId().equals(ToolKit.getCurrentPerson().getUsername())){
                    lastMessage.setText("You sent a picture");
                }
                else{
                    lastMessage.setText(person.getFirstName() + " sent a picture");
                }
            }
            else{
                if(lMessage.getSenderId().equals(ToolKit.getCurrentPerson().getUsername())){
                    lastMessage.setText("You : " + lMessage.getMessage().getContent());
                }
                else{
                    lastMessage.setText(lMessage.getMessage().getContent());
                }
            }
            if(ToolKit.dateBetween(ToolKit.getCurTime(), lMessage.getMessage().getUploadTime()) > 1){
                time.setText(ToolKit.makeDateStructured(lMessage.getMessage().getUploadTime(), "dd MMMMM, yyyy"));
            }
            else{
                time.setText(ToolKit.makeDateStructured(lMessage.getMessage().getUploadTime(), "hh:mm aa"));
            }
        }
    }

    void setParent(ChatHeadContainerController parent) {
        this.parent = parent;
    }
    
}
