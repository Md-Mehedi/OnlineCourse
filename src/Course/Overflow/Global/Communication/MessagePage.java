/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Communication;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.Page;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.ToolKit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class MessagePage extends Page{
    private FXMLLoader loader;
    private AnchorPane pane;
    private MessengerController messengerCtrl;
    private ChatHeadContainerController chatHeadCtrl;
    public MessagePage(){
        super(PageName.Messenger);
        root = new AnchorPane();
        HBox box = new HBox();
        ToolKit.setAnchor(box, 0, 0, 0, 0);
        
        box.setStyle("-fx-spacing: 0; -fx-alignment: top-left;");
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMMUNICATION_LOCATION + "/ChatHeadContainer.fxml"));
            pane = loader.load();
            chatHeadCtrl = loader.<ChatHeadContainerController>getController();
            chatHeadCtrl.setParent(this);
            box.getChildren().add(pane);
            
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMMUNICATION_LOCATION + "/Messenger.fxml"));
            pane = loader.load();
            messengerCtrl = loader.<MessengerController>getController();
            messengerCtrl.setParent(this);
            box.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(MessagePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        root.getChildren().add(box);
        
        chatHeadCtrl.setMessengerCtrl(messengerCtrl);
        chatHeadCtrl.loadChatHeads();
        messengerCtrl.setChatHeadCtrl(chatHeadCtrl);
        Platform.runLater(()->{
            setPrefHeight(GLOBAL.HEIGHT - GLOBAL.HEADER.getRoot().getPrefHeight() - GLOBAL.TOP_MENU_BAR.getHeight()-10);
        });
    }
    
    public AnchorPane getRoot(){
        return root;
    }
    
    public void setPrefHeight(double value){
        root.setMaxHeight(value);
    }

    public void showNoDataFound() {
        ToolKit.showNoDataFound(root);
    }
    
    public void addNewChatHead(Person person){
        chatHeadCtrl.makeChatBox(person, 0);
    }
    
    public AnchorPane getSelectedBox(){
        return chatHeadCtrl.getSelectedBox();
    }
}
