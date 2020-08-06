/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Tools.IconChooser;

import Course.Overflow.Global.Customize.Icon;
import Course.Overflow.Global.Customize.Icon.IconType;
import Course.Overflow.Global.GLOBAL;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 *
 * @author Md Mehedi Hasan
 */
public class IconChooser{
    private HashMap<String,Object> result;
    private Icon icon;

    public IconChooser() {
        result = new HashMap<>();
    }
    

    public Icon showIconWindow(){
        AnchorPane root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Popup.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(IconChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        loader.<PopupController>getController().setStage(primaryStage);
        primaryStage.initOwner(GLOBAL.stage);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
        result = loader.<PopupController>getController().getResult();
        IconType type = (IconType) result.get("type");
        icon = new Icon(type, (String) result.get("name"));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                icon = null;
            }
        });
        
        return icon;
    }
}
