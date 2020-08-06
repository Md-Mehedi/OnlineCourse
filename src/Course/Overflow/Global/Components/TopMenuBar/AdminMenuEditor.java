/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components.TopMenuBar;

import Course.Overflow.Global.Customize.Icon;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 * 
 * Create a variable and use 'getContainer()' to
 * get the container of menu.
 * @author ASUS
 */
public class AdminMenuEditor{
    private AnchorPane wrapper;
    private VBox container;
    private Label heading;
    private VBox menuItemContainer;
    private ImageView img;
    private ArrayList<MenuItemEditorController> ctrlList;

    public AdminMenuEditor() {
        ctrlList = new ArrayList<MenuItemEditorController>();
        container = new VBox();
        wrapper = new AnchorPane(container);
        heading = new Label("Menu Editor");
        menuItemContainer = new VBox();
        img = new ImageView(new Image(GLOBAL.ICON_LOCATION + "/plus_48px.png"));
        img.getStyleClass().add("minScaleHover");
        img.getStyleClass().add("cursorHand");
        container.getChildren().addAll(heading, menuItemContainer, new HBox(img));
        wrapper.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        wrapper.getStylesheets().add(GLOBAL.TOP_MENU_LOACATION + "/TopMenuBar.css");
        wrapper.setId("adminMenuEditorContainer");
        container.getStyleClass().add("centerRightWidth");
        ToolKit.setAnchor(container, 0, 0, 0, 0);
        heading.getStyleClass().add("title2");
        addEventHandler();
        createAutoMenus();
        for(TopMenu menu : menus){
            MenuItemEditorController ctrl = createMenu();
            ctrl.setTopMenu(menu);
        }
    }
    private MenuItemEditorController createMenu(){
        MenuItemEditorController ctrl = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.TOP_MENU_LOACATION + "/MenuItemEditor.fxml"));
            AnchorPane subMenu = loader.load();
            menuItemContainer.getChildren().add(subMenu);
            ctrl = loader.getController();
            ctrlList.add(ctrl);
            ctrl.setCtrlList(ctrlList);
        } catch (IOException ex) {
            Logger.getLogger(AdminMenuEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ctrl;
    }

    private void addEventHandler(){
        img.setOnMouseClicked((event) -> {
            createMenu();
        });
    }
    public AnchorPane getContainer() {
        return wrapper;
    }
    
    ArrayList<TopMenu> menus;
    static int j = 1;
    static int i = 1;
    
    public void createAutoMenus() {
        menus = new ArrayList<>();
        menus.add(new TopMenu("Menu "+(j++), new Icon(FontAwesomeIcon.ADJUST), getItems()));
        menus.add(new TopMenu("Menu "+(j++), new Icon(FontAwesomeIcon.ADJUST), getItems()));
        menus.add(new TopMenu("Menu "+(j++), new Icon(FontAwesomeIcon.ADJUST), getItems()));
        menus.add(new TopMenu("Menu "+(j++), new Icon(FontAwesomeIcon.ADJUST), getItems()));
        menus.add(new TopMenu("Menu "+(j++), new Icon(FontAwesomeIcon.ADJUST), getItems()));
    }
    
    public ArrayList<MenuItem> getItems(){
        ArrayList<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("Sub Menu "+(i++), new Icon(FontAwesomeIcon.COG)));
        items.add(new MenuItem("Sub Menu "+(i++), new Icon(FontAwesomeIcon.COG)));
        items.add(new MenuItem("Sub Menu "+(i++), new Icon(FontAwesomeIcon.COG)));
        items.add(new MenuItem("Sub Menu "+(i++), new Icon(FontAwesomeIcon.COG)));
        items.add(new MenuItem("Sub Menu "+(i++), new Icon(FontAwesomeIcon.COG)));
        items.add(new MenuItem("Sub Menu "+(i++), new Icon(FontAwesomeIcon.COG)));
        items.add(new MenuItem("Sub Menu "+(i++), new Icon(FontAwesomeIcon.COG)));
        return items;
    }
    
}
