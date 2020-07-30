/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Components.TopMenuBar;

import Course.Overflow.Global.Customize.Icon;
import Course.Overflow.Global.GLOBAL;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Md Mehedi Hasan
 */
public class MenuBar {
    ArrayList<TopMenu> menus;
    AnchorPane menuContainer;
    static int j = 1;
    static int i = 1;
    
    public MenuBar() {
        menus = new ArrayList<>();
        menuContainer = new AnchorPane();
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
        return items;
    }
    
    public void add(TopMenu menu){
        menus.add(menu);
    }
    
    public void add(String name, Icon icon, ArrayList<MenuItem> subMenus){
        menus.add(new TopMenu(name, icon, subMenus));
    }
    
    public AnchorPane getMenuContainer(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.TOP_MENU_LOACATION + "/TopMenu.fxml"));
        try {
            menuContainer = loader.load();
            loader.<TopMenuController>getController().setMenu(menus);
        } catch (IOException ex) {
            Logger.getLogger(MenuBar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return menuContainer;
    }
    
    public void setMenus(ArrayList<TopMenu> menus){
        this.menus = menus;
    }
}
