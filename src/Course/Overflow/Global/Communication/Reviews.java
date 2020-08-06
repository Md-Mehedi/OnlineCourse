/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Communication;

import Course.Overflow.Global.GLOBAL;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Reviews extends Communication{
    VBox reviewContainer;
    
    public Reviews(){
        reviewContainer = bottomContainer;
        
        addAReview();
        addAReview();
        addAReview();
        addAReview();
    }
    
    public void addAReview(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_HOME_LOCATION + "/Review.fxml"));
            reviewContainer.getChildren().add(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(Reviews.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
