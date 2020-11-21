/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Communication;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.Show.CourseBoxLittle;
import Course.Overflow.Global.ToolKit;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Anouncement {
    AnchorPane root;
    VBox container;
    
    public Anouncement(){
        container = new VBox();
        root = new AnchorPane(container);
        ToolKit.setAnchor(container, 0, 0, 0, 0);
        
        makeSomeDefaultAnouncemnet();
    }
    
    public VBox getAnounceBox(){
        Label title = new Label("Title... lksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr l");
        title.getStyleClass().add("title5");
        Label anounce = new Label("Title... lksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr l lksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr l");
        return (new VBox(title, anounce));
    }

    private void makeSomeDefaultAnouncemnet() {
        Course course = new Course(16);
        for(int j=0; j<10; j++){
            CourseBoxLittle box = new CourseBoxLittle(course);
            container.getChildren().add(box);
            for(int i=0;i<2;i++){
                box.addData(getAnounceBox());
            }
        }
    }
    
    public AnchorPane getRoot(){
        return root;
    }
}
