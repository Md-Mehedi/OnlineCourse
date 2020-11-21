/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Teacher;

import Course.Overflow.Course.Show.CourseBoxLittle;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.PageByPageLayoutController;
import Course.Overflow.Global.ToolKit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    
    private Pane getSudentList(){
        Pane pane = null;
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.LAYOUT_LOCATION + "/PageByPageLayout.fxml"));
            pane = loader.load();
            PageByPageLayoutController ctrl = loader.getController();
//            ctrl.setUpPage(PageByPageLayoutController.BoxType.PersonGrid, 40, 3, 30);
        } catch (IOException ex) {
            Logger.getLogger(EnrolledStudentsView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pane;
    }

    private void makeDefaultStudentList() {
        for(int j=0; j<5; j++){
            CourseBoxLittle box = new CourseBoxLittle();
            container.getChildren().add(box);
            box.addData(getSudentList());
        }
    }
    
    public AnchorPane getRoot(){
        return root;
    }
}
