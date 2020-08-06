/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Communication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedBox = new HBox();
        for(int i=0;i<10;i++)
            makeChatBox();
    }    
    
    public void makeChatBox(){
        Circle c = new Circle(40);
        Label l1 = new Label("Course namajsdfl sjflsd flsjf klsjf lskfj slf saljf ljflskfj klsj flskj fjjasd fe");
        l1.getStyleClass().add("courseName");
        Label l2 = new Label("Instructor najsljsl flsf klfjsljf lsjf lskjf dfk sdklfj slfj me");
        l2.getStyleClass().add("teacherName");
        Label l3 = new Label("Last message ad fsdjsd flsdjfldsjf sljf jf lsdjf lsdkf ...");
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
            messengerCtrl.loadMessage();
        });
    }
    
    public void setMessengerCtrl(MessengerController ctrl){
        this.messengerCtrl = ctrl;
    }
    
}
