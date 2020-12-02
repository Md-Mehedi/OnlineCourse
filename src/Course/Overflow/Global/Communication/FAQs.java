/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Communication;

import Course.Overflow.Course.Course;
import Course.Overflow.DB;
import Course.Overflow.Global.ToolKit;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    
    private VBox getaQA(){
        Label q = new Label("Question sldkfjlksdj flksdjf kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri  s;lkfj ;lajs fljlskfj ljf lsjflksjdlfjlashfjkshfkljoiweroi??");
        q.getStyleClass().add("title5");
        Label a = new Label("Answer ajsfljsl fowo ojosa fweuo lsifu oiwer jzjoaiuf worijoif poiweir wir kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri kfj slfj lasjf lsjf lsjf lsjf lksaj fwru owijljo iwoir jljoiuwo rowieri  owroi...");
        
        return new VBox(q,a);
    }

    private void makeList() {
        ToolKit.showNoDataFound(root);
//        for(int j=0; j<10; j++){
//            CourseBoxLittle box = new CourseBoxLittle(course);
//            container.getChildren().add(box);
//            for(int i=0;i<5;i++){
//                box.addData(getaQA());
//            }
//        }
    }
    
    public AnchorPane getRoot(){
        return root;
    }
    
    public static void delete(Course course) {
        DB.execute("DELETE FROM FAQ WHERE COURSE_ID = #", course.getId().toString());
    }
    
}
