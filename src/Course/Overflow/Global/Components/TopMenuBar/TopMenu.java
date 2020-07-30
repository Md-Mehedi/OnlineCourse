/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Components.TopMenuBar;

import Course.Overflow.Global.Customize.Icon;
import java.util.ArrayList;

/**
 *
 * @author Md Mehedi Hasan
 */
public class TopMenu {
    MenuItem menu;
    ArrayList<MenuItem> subMenus;
    
    public TopMenu(String name, Icon icon, ArrayList<MenuItem> subMenus){
        menu = new MenuItem(name, icon);
        this.subMenus = subMenus;
    }

    public MenuItem getMenu() {
        return menu;
    }

    public ArrayList<MenuItem> getSubMenus() {
        return subMenus;
    }
}
