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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ChatHeadController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox chatBoxContainer;
    @FXML
    private Circle instPhoto;
    @FXML
    private Label courseName;
    @FXML
    private Label instName;
    @FXML
    private Label lastMessage;
    private HBox selectedBox;
    private MessengerController messengerCtrl;
    private MessagePage parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedBox = new HBox();
    }    
    
    public HBox makeChatBox(Person person){
        Circle c = new Circle(40);
        c.setFill(new ImagePattern(ToolKit.makeImage(person.getPhoto())));
        
        Label l1 = new Label("Course name");
        l1.getStyleClass().add("courseName");
        
        Label l2 = new Label(person.getFullName());
        l2.getStyleClass().add("teacherName");
        
        Message lMessage = Message.getLastMessage(ToolKit.getCurrentPerson(), person);
        String text = "";
        if(lMessage.getMessage().getType() == FileType.PICTURE){
            text = "Sent a message";
        }
        else{
            text = lMessage.getMessage().getContent();
        }
        Label l3 = new Label(text);
        l3.getStyleClass().add("lastMessage");
        
        HBox box = new HBox(c,new VBox(l1,l2,l3));
        box.getStyleClass().add("chatBox");
        chatBoxContainer.getChildren().add(box);
        
        box.setOnMouseClicked((event) -> {
            if(selectedBox.getStyleClass().contains("selectedChatBox")){
                selectedBox.getStyleClass().remove("selectedChatBox");
            }
            box.getStyleClass().add("selectedChatBox");
            selectedBox = box;
            messengerCtrl.loadMessage(person);
        });
        
        return box;
    }
    
    public void setMessengerCtrl(MessengerController ctrl){
        this.messengerCtrl = ctrl;
    }

    public void loadChatBox() {
        chatBoxContainer.getChildren().clear();
        ArrayList<Person> list = Message.getMessageReceipentList(ToolKit.getCurrentPerson());
        if(list.isEmpty()){
            parent.showNoDataFound();
            return;
        }
        for(int i=0;i<list.size();i++) {
            HBox box = makeChatBox(list.get(i));
            if(i==0){
                box.getStyleClass().add("selectedChatBox");
                selectedBox = box;
                messengerCtrl.loadMessage(list.get(0));
            }
        }
    }

    void setParent(MessagePage parent) {
        this.parent = parent;
    }
    
}
