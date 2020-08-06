/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components.TopMenuBar;

import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.GLOBAL;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class TopMenuController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private HBox topMenuContainer;
    private MenuBar menubar;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;

    private AnchorPane subMenu;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        createTopMenu();
    }   
    
    private void createSubMenu(Label label) throws IOException{
        AnchorPane subMenu;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/SubMenu.fxml"));
        subMenu = loader.load();
        Platform.runLater(()->{
            new ToolTip(MouseEvent.MOUSE_ENTERED, label, subMenu,-label.getLayoutX());
        });
    }
    
    private void createTopMenu(){
        topMenuContainer.getChildren().clear();
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        topMenuContainer.getChildren().add(region);
        
        for(int i=0;i<5;i++){
            Label label = new Label("Menu "+(i+1));
            region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            try {
                createSubMenu(label);
            } catch (IOException ex) {Logger.getLogger(TopMenuController.class.getName()).log(Level.SEVERE, null, ex);}
            topMenuContainer.getChildren().addAll(label,region);
        }
    }
    
    private void updateLabelWidth() {
        Platform.runLater(()->{
            double max = -500;
            for(Node node : topMenuContainer.getChildren()){
                if(node.getClass() == Label.class){
                    Label label = (Label) node;
                    max = Math.max(max,label.getWidth()+10.0);
                }
            }
            for(Node node : topMenuContainer.getChildren()){
                if(node.getClass() == Label.class){
                    Label label = (Label) node;
                    label.setPrefWidth(max);
                }
            }
        });
    }
    
    public void setMenu(ArrayList<TopMenu> menus) {
        topMenuContainer.getChildren().clear();
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        topMenuContainer.getChildren().add(region);
        for(TopMenu menu : menus) {
            HBox box = new HBox(menu.getMenu().getMenuInHBox());
            box.getStyleClass().add("topMenuuu");
            region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            topMenuContainer.getChildren().addAll(box,region);
            
            Platform.runLater(()->{
                try {
                    subMenu = new AnchorPane();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.TOP_MENU_LOACATION + "/SubMenu.fxml"));
                    subMenu = loader.load();
                    loader.<SubMenuController>getController().setSubMenu(menu.getSubMenus());
                        new ToolTip(MouseEvent.MOUSE_ENTERED, box, subMenu,-box.getLayoutX());
                } catch (IOException ex) {Logger.getLogger(TopMenuController.class.getName()).log(Level.SEVERE, null, ex);}
            });
        }
    }
}
