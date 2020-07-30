/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components.TopMenuBar;

import Course.Overflow.Global.Customize.Icon;
import Course.Overflow.Global.Customize.Icon.IconType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class MenuItem {

    private String name;
    private Icon icon;


    public MenuItem(String name, Icon icon) {
        this.name = name;
        this.icon = icon;
    }
    
    public MenuItem(String name, IconType type, String iconName) {
        this(name, new Icon(type, iconName));
    }

    public String getName() {
        return name;
    }

    public Icon getIcon() {
        return icon;
    }
    
    public HBox getMenuInHBox(){
        Label label = new Label(name);
        HBox box = new HBox(icon,label);
        return box;
    }
    
    public VBox getMenuInVBox(){
        Label label = new Label(name);
        VBox box = new VBox(icon,label);
        return box;
    }
}
