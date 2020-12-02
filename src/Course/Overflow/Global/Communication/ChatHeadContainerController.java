/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Communication;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.ToolKit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ChatHeadContainerController implements Initializable {

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
    private AnchorPane selectedBox;
    private MessengerController messengerCtrl;
    private MessagePage parent;
    private Map<AnchorPane, ChatHeadController> paneToCtrl;
    private Map<String, AnchorPane> personToPane;
    @FXML
    private AnchorPane chatBoxRootContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paneToCtrl = new HashMap<AnchorPane, ChatHeadController>();
        personToPane = new HashMap<String, AnchorPane>();
    }    
    
    public void makeChatBox(Person person, Integer position){
        System.out.println("size: "+personToPane.size());
        if(personToPane.containsKey(person.getUsername())){
            AnchorPane head = personToPane.get(person.getUsername());
            loadMessage(person, head);
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COMMUNICATION_LOCATION + "/ChatHead.fxml"));
            AnchorPane chatHead = loader.load();
            ChatHeadController ctrl = loader.getController();
            ctrl.setParent(this);
            ctrl.setMessengerController(messengerCtrl);
            ctrl.setPerson(person);
            ctrl.setLastMessage(Message.getLastMessage(ToolKit.getCurrentPerson(), person));
            chatBoxContainer.getChildren().add(position, chatHead);
            if(position == 0){
                loadMessage(person, chatHead);
            }
            paneToCtrl.put(chatHead, ctrl);
            personToPane.put(person.getUsername(), chatHead);
        } catch (IOException ex) {
            Logger.getLogger(ChatHeadContainerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadMessage(Person person, AnchorPane chatHead){
        if(selectedBox != null){
            selectedBox.getStyleClass().remove("selectedChatBox");
        }
        chatHead.getStyleClass().add("selectedChatBox");
        selectedBox = chatHead;
        messengerCtrl.setChatHeadBox(chatHead);
        messengerCtrl.loadMessage(person);
    }
    
    public void makeChatBox(Person person){
        makeChatBox(person, chatBoxContainer.getChildren().size());
    }
    
    public void setMessengerCtrl(MessengerController ctrl){
        this.messengerCtrl = ctrl;
    }

    public void loadChatHeads() {
        chatBoxContainer.getChildren().clear();
        ArrayList<Person> list = Message.getMessageReceipentList(ToolKit.getCurrentPerson());
        if(list.isEmpty()){
            return;
        }
        for(int i=0;i<list.size();i++) {
            makeChatBox(list.get(i));
        }
    }

    void setParent(MessagePage parent) {
        this.parent = parent;
    }

    double getPrefWidth() {
        return container.getPrefWidth();
    }

    void popUpChatHead(AnchorPane box, Message message) {
        ChatHeadController ctrl = paneToCtrl.get(box);
        ctrl.setLastMessage(message);
        int curIdx = chatBoxContainer.getChildren().indexOf(box);
        if(curIdx == 0) return;
        double width = box.getPrefWidth();
        double height = 105;
        
        AnchorPane blankDown = new AnchorPane();
        blankDown.setPrefWidth(width);
        blankDown.setPrefHeight(height);
        blankDown.setMaxHeight(height);
        chatBoxContainer.getChildren().remove(box);
        chatBoxContainer.getChildren().add(curIdx, blankDown);
        chatBoxRootContainer.getChildren().add(box);
        box.setLayoutY(curIdx*height);
        
        AnchorPane blankUp = new AnchorPane();
        blankUp.setPrefWidth(width);
        blankUp.setPrefHeight(0);
        chatBoxContainer.getChildren().add(0, blankUp);
        
        Duration cycleDuration = Duration.millis(500);
        Platform.runLater(()->{
            new Timeline().play();
        });
        Timeline timeline = new Timeline(
                new KeyFrame(cycleDuration,
                        new KeyValue(blankUp.minHeightProperty(), height ,Interpolator.EASE_BOTH)),
                new KeyFrame(cycleDuration,
                        new KeyValue(blankDown.maxHeightProperty(), 0,Interpolator.EASE_BOTH)),
                new KeyFrame(cycleDuration, 
                        new KeyValue(box.layoutYProperty(), 0, Interpolator.EASE_IN))
                );
        timeline.play();
        timeline.setOnFinished(event->{
            chatBoxContainer.getChildren().remove(0);
            chatBoxContainer.getChildren().remove(curIdx);
            chatBoxRootContainer.getChildren().remove(box);
            chatBoxContainer.getChildren().add(0,box);
        });
    }
    
    public AnchorPane getSelectedBox(){
        return selectedBox;
    }
    
    public void setSelectedBox(AnchorPane box){
        selectedBox = box;
    }
    
}
