/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Global.GLOBAL;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Page {
    AnchorPane root;
    PageName pageName;
    
    public Page(){
        root = new AnchorPane();
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
    }
    
    public Page(PageName pageName){
        this();
        this.pageName = pageName;
    }

    public AnchorPane getRoot() {
        return root;
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
    }
    
    public PageName getPageName(){
        return pageName;
    }
}
