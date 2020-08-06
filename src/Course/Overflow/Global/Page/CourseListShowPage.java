/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.PageByPageLayoutController;
import Course.Overflow.Global.Layout.PageByPageLayoutController.CourseBoxShowType;
import Course.Overflow.Global.ToolKit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class CourseListShowPage extends Page{
    private FXMLLoader loader;
    private VBox container;
    private PageByPageLayoutController ctrl;
    private CourseBoxShowType type;
    
    public CourseListShowPage(String title){
        this(title,CourseBoxShowType.Grid);
    }
    
    public CourseListShowPage(String title, CourseBoxShowType type){
        this.type = type;
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        
        container = new VBox();
        root.getChildren().add(container);
        ToolKit.setAnchor(container, 50, 150, 20, 150);
        
        Label label = new Label(title);
        container.getChildren().add(label);
        label.getStyleClass().add("title1");
        
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.LAYOUT_LOCATION + "/PageByPageLayout.fxml"));
            AnchorPane pane = loader.load();
            ctrl = loader.<PageByPageLayoutController>getController();
            if(type == CourseBoxShowType.Grid){
                ctrl.setUpPage(41, 4, 45);
            }
            else{
                ctrl.setUpPage(41);
            }
            container.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(TeacherDetailsPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void makeItPurchaseHistoryPage(){
        ctrl.addPurchaseDateColumn();
        ctrl.stopViewChange();
    }
}
