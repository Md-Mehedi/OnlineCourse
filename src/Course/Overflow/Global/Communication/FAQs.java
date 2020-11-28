/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Communication;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.FAQ;
import Course.Overflow.Course.Show.CourseBoxLittle;
import Course.Overflow.Course.Show.FAQOutputBoxController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Person.AccountType;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.CreateCourse.Curriculum.CurriculumController.ViewerType;
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
public class FAQs {
    private AnchorPane root;
    private VBox container;
    
    public FAQs(){
        container = new VBox();
        root = new AnchorPane(container);
        ToolKit.setAnchor(container, 0, 0, 0, 0);
        
        makeList();
    }
    
    private Pane getaQA(FAQ faq){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_SHOW_LOCATION + "/FAQOutputBox.fxml"));
            AnchorPane pane = loader.load();
            FAQOutputBoxController ctrl = loader.getController();
            ViewerType viewer = (GLOBAL.ACCOUNT_TYPE == AccountType.Student ? ViewerType.NormalStudent : ViewerType.OwnerTeacherNormal);
            ctrl.loadData(viewer, faq);
            ctrl.getQuestionLabel().getStyleClass().add("title5");
            return pane;
        } catch (IOException ex) {
            Logger.getLogger(FAQs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void makeList() {
        ArrayList<Pair<Course, ArrayList<FAQ>>> list = null;
        if(GLOBAL.ACCOUNT_TYPE == AccountType.Teacher) list = FAQ.getFAQForTeacherView();
        else if(GLOBAL.ACCOUNT_TYPE == AccountType.Student) list = FAQ.getFAQForStudentView();
        if(list.size() == 0){
           ToolKit.showNoDataFound(root);
        }
        else{
            for(int j=0; j<list.size(); j++){
                CourseBoxLittle box = new CourseBoxLittle(list.get(j).getKey());
                container.getChildren().add(box);
                for(int i=0;i<list.get(j).getValue().size();i++){
                    box.addData(getaQA(list.get(j).getValue().get(i)));
                }
            }
        }
    }
    
    public AnchorPane getRoot(){
        return root;
    }
    
}
