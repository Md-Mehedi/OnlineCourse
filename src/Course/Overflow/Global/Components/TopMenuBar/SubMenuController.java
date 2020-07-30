/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components.TopMenuBar;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class SubMenuController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private HBox menuContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setSubMenu(ArrayList<MenuItem> subMenus) {
        menuContainer.getChildren().clear();
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        menuContainer.getChildren().add(region);
        for(MenuItem item : subMenus){
            VBox box = item.getMenuInVBox();
            region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            menuContainer.getChildren().addAll(box,region);
        }
    }
    
}
