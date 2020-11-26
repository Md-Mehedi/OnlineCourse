/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Course.Course;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.PageByPageLayoutController;
import Course.Overflow.Global.Layout.PageByPageLayoutController.BoxViewType;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Student.PurchaseHistory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private BoxViewType type;
    
    public CourseListShowPage(PageName pageName){
        this(pageName,BoxViewType.GridView);
    }
    
    public CourseListShowPage(PageName pageName, BoxViewType type){
        super(pageName);
        this.type = type;
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        
        container = new VBox();
        root.getChildren().add(container);
        ToolKit.setAnchor(container, 50, 150, 20, 150);
        
        Label label = new Label(pageName.getName());
        container.getChildren().add(label);
        label.getStyleClass().add("title1");
        
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.LAYOUT_LOCATION + "/PageByPageLayout.fxml"));
            AnchorPane pane = loader.load();
            ctrl = loader.<PageByPageLayoutController>getController();
            if(pageName == PageName.MyCourse){
                ctrl.setUpPage(ToolKit.getCurrentPerson().getMyCourses(), BoxViewType.GridView);
            }
            else if(pageName == PageName.PurchaseHistory){
                Map<Course, PurchaseHistory> mp = new HashMap();
                ArrayList<Course> courses = GLOBAL.STUDENT.getCourses();
                for(int i=0; i<courses.size(); i++){
                    mp.put(courses.get(i), new PurchaseHistory(courses.get(i), GLOBAL.STUDENT));
                }
                ctrl.setPurchasyHistory(mp);
                ctrl.setUpPage(courses, BoxViewType.ListView);
                ctrl.stopViewChange();
            }
            container.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(PersonDetailsPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
