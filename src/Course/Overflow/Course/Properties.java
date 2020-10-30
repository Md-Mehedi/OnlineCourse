/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course;

import Course.Overflow.Global.Customize.Icon;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Properties {
    Icon icon;
    String text;

    public Properties(Icon icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }
    
}
