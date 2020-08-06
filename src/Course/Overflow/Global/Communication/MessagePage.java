/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Communication;

import Course.Overflow.Global.GLOBAL;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class MessagePage {
    private AnchorPane root;
    private FXMLLoader loader;
    private AnchorPane pane;
    private MessengerController messengerCtrl;
    private ChatHeadController chatHeadCtrl;
    public MessagePage(){
        root = new AnchorPane();
        HBox box = new HBox();
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMMUNICATION_LOCATION + "/ChatHead.fxml"));
            pane = loader.load();
            chatHeadCtrl = loader.<ChatHeadController>getController();
            box.getChildren().add(pane);
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMMUNICATION_LOCATION + "/Messenger.fxml"));
            pane = loader.load();
            messengerCtrl = loader.<MessengerController>getController();
            box.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(MessagePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        root.getChildren().add(box);
        chatHeadCtrl.setMessengerCtrl(messengerCtrl);
    }
    
    public AnchorPane getRoot(){
        return root;
    }
}
