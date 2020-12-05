/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Course.Category;
import Course.Overflow.Course.Course;
import Course.Overflow.Global.Components.CarouselController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Homepage extends Page{

    private FXMLLoader loader;
    private VBox verticalBox;
    private ArrayList<CarouselController> carousels;
    
    
    public Homepage(){
        super(PageName.Home);
        verticalBox = new VBox();
        root.getChildren().add(verticalBox);
        ToolKit.setAnchor(verticalBox, 0, 0, 0, 0);
        carousels = new ArrayList<>();
        addCarousels();
        
        System.out.println("Home page loaded...");
    }
    
    private CarouselController makeCarousel(){
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/Carousel.fxml"));
            AnchorPane pane = loader.load();
            verticalBox.getChildren().add(pane);
            return loader.<CarouselController>getController();
        } catch (IOException ex) {
            Logger.getLogger(Homepage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void addCarousels() {
        ArrayList<Course> apcourses = new ArrayList<Course> (Course.getApprovedCourses());
        CarouselController ctrl;
        
        ctrl = makeCarousel();
        ctrl.setCourses(Course.getNewReleasedCourse());
        ctrl.setTitle("New Released");
        
        ctrl = makeCarousel();
        ctrl.setCourses(Course.getBestSellerCourse());
        ctrl.setTitle("Best Seller");
        
        ctrl = makeCarousel();
        ctrl.setCourses(Course.getMostDiscountCourse());
        ctrl.setTitle("Most Discount");
        
        ctrl = makeCarousel();
        ctrl.setCourses(Course.getFreeCourse());
        ctrl.setTitle("Free! Free!! Free!!!");
        
        ctrl = makeCarousel();
        ctrl.setCourses(Course.getMostReivewedCourse());
        ctrl.setTitle("Most Reviewed");
        
        ctrl = makeCarousel();
        ctrl.setCourses(Course.getList(new Category("Computer Programming")));
        ctrl.setTitle("Computer Programming");
    }
}
