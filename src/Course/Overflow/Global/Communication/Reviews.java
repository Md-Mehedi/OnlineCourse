/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Communication;

import Course.Overflow.Course.Show.CourseBoxLittle;
import Course.Overflow.Course.Show.ReviewController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 *
 * @author Md Mehedi Hasan
 */
public class Reviews {
    private AnchorPane root;
    private VBox container;
    
    public Reviews(){
        container = new VBox();
        root = new AnchorPane(container);
        ToolKit.setAnchor(container, 0, 0, 0, 0);
        
        makeSomeDefaultReview();
        
    }
    
    public AnchorPane getReviewBox(){
        AnchorPane pane = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_SHOW_LOCATION + "/Review.fxml"));
            pane = loader.load();
            loader.<ReviewController>getController().setPrefWidth(1000);
        } catch (IOException ex) {
            Logger.getLogger(Reviews.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pane;
    }
    private void makeSomeDefaultReview() {
        for(int j=0; j<10; j++){
            CourseBoxLittle box = new CourseBoxLittle();
            container.getChildren().add(box);
            for(int i=0;i<2;i++){
                box.addData(getReviewBox());
            }
        }
    }
    
    public AnchorPane getRoot(){
        return root;
    }
}
