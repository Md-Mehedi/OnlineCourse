/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Teacher;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.Show.CourseBoxLittle;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.PageByPageLayoutController;
import Course.Overflow.Global.Layout.PageByPageLayoutController.BoxViewType;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Student.PurchaseHistory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 *
 * @author Md Mehedi Hasan
 */
public class EnrolledStudentsView {
    private AnchorPane root;
    private VBox container;
    private FXMLLoader loader;
    
    public EnrolledStudentsView(){
        container = new VBox();
        root = new AnchorPane(container);
        ToolKit.setAnchor(container, 0, 0, 0, 0);
        
        makeDefaultStudentList();
    }
    
    private Pane getSudentList(ArrayList<PurchaseHistory> list){
        Pane pane = null;
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.LAYOUT_LOCATION + "/PageByPageLayout.fxml"));
            pane = loader.load();
            PageByPageLayoutController ctrl = loader.getController();
            ctrl.setUpPage(list, BoxViewType.PersonGrid, 3);
            ctrl.stopViewChange();
            ctrl.attachedWithContainer();
        } catch (IOException ex) {
            Logger.getLogger(EnrolledStudentsView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pane;
    }

    private void makeDefaultStudentList() {
        ArrayList<Pair<Course, ArrayList<PurchaseHistory>>> lists = PurchaseHistory.getEnrolledStudentList();
        for(int j=0; j<lists.size(); j++){
            CourseBoxLittle box = new CourseBoxLittle(lists.get(j).getKey());
            container.getChildren().add(box);
            box.addData(getSudentList(lists.get(j).getValue()));
        }
    }
    
    public AnchorPane getRoot(){
        return root;
    }
}
