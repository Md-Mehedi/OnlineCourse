/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Course.Show.CourseDetailsController;
import Course.Overflow.Global.GLOBAL;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Md Mehedi Hasan
 */
public class CoursePage extends Page{

    private FXMLLoader loader;
    private CourseDetailsController pageCtrl;
    
    public CoursePage(){
        super(PageName.Course);
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_SHOW_LOCATION + "/CourseDetails.fxml"));
            root = loader.load();
            this.controller = loader.<CourseDetailsController>getController();
        } catch (IOException ex) {
            Logger.getLogger(CoursePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public CourseDetailsController getController(){
        return (CourseDetailsController) this.controller;
    }
}
