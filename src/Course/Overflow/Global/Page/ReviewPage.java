
package Course.Overflow.Global.Page;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.Review;
import Course.Overflow.Course.Show.CourseBoxLittle;
import Course.Overflow.Course.Show.ReviewController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Person.AccountType;
import Course.Overflow.Global.ToolKit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;


/**
 *
 * @author Md Mehedi Hasan
 */
public class ReviewPage{
    private AnchorPane root;
    private VBox container;
    
    public ReviewPage(){
        container = new VBox();
        container.setSpacing(30);
        root = new AnchorPane(container);
        ToolKit.setAnchor(container, 0, 0, 0, 0);
        
        makeList();
        
    }
    
    public AnchorPane getReviewBox(Review review){
        AnchorPane pane = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_SHOW_LOCATION + "/Review.fxml"));
            pane = loader.load();
            ReviewController ctrl = loader.<ReviewController>getController();
            ctrl.setPrefWidth(1000);
            ctrl.loadData(review);
        } catch (IOException ex) {
            Logger.getLogger(ReviewPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pane;
    }
    private void makeList() {
        ArrayList<Pair<Course ,ArrayList<Review>>> lists = null;
        if(GLOBAL.ACCOUNT_TYPE == AccountType.Teacher) lists = Review.getReviewsForTeacherView();
        else if(GLOBAL.ACCOUNT_TYPE == AccountType.Student) lists = Review.getReviewsForStudentView();
        
        if(lists.size() == 0){
            ToolKit.showNoDataFound(root);
            return;
        }
        for(int j=0; j<lists.size(); j++){
            CourseBoxLittle box = new CourseBoxLittle(lists.get(j).getKey());
            container.getChildren().add(box);
            for(int i=0;i<lists.get(j).getValue().size();i++){
                box.addData(getReviewBox(lists.get(j).getValue().get(i)));
            }
        }
    }
    
    public AnchorPane getRoot(){
        return root;
    }
}