/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.ToolKit;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class RightMenuPopOverController implements Initializable {

    @FXML
    private HBox setting;
    @FXML
    private Circle imageCircle;
    @FXML
    private Label imageLabel;
    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private Label myCourse;
    @FXML
    private Label purchaseHistory;
    @FXML
    private Label wishlist;
    @FXML
    private Label message;
    @FXML
    private Label faq;
    @FXML
    private Label accountSetting;
    @FXML
    private Label signOut;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addDetails();
    }    

    @FXML
    private void mouseExited(MouseEvent event) {
    }

    @FXML
    private void mouseEntered(MouseEvent event) {
    }

    @FXML
    private void mouseClicked(MouseEvent event) {
//        if(event.getSource()==myCourse){
//            GLOBAL.PAGE_CTRL.loadPage(PageName.MyCourse);
//        }
//        else if(event.getSource()==wishlist){
//            GLOBAL.PAGE_CTRL.loadPage(PageName.Wishlist);
//        }
//        else if(event.getSource()==purchaseHistory){
//            GLOBAL.PAGE_CTRL.loadPage(PageName.PurchaseHistory);
//        }
//        else if(event.getSource()==message){
//            GLOBAL.PAGE_CTRL.loadPage(PageName.Messenger);
//        }
//        else if(event.getSource()==faq){
//            GLOBAL.PAGE_CTRL.loadPage(PageName.FAQ);
//        }
        if(event.getSource()==signOut){
            GLOBAL.ACCOUNT_TYPE = null;
            GLOBAL.STUDENT = null;
            GLOBAL.TEACHER = null;
            GLOBAL.PAGE_CTRL.loadPage(PageName.Login);
        }
        else if(event.getSource()==setting){
            GLOBAL.PAGE_CTRL.loadPage(PageName.ProfileSetting);
        }
        else if(event.getSource()==accountSetting){
            GLOBAL.PAGE_CTRL.loadPage(PageName.ProfileSetting);
        }
    }    

    private void addDetails() {
        Person person = ToolKit.getCurrentPerson();
        username.setText(person.getUsername());
        email.setText(person.getEmail());
        if(person.getPhoto() != null){
            Image image = new Image(new File(ToolKit.makeAbsoluteLocation(person.getPhoto().getContent())).toURI().toString());
            imageCircle.setFill(new ImagePattern(image));
            imageLabel.setText("");
        }
        else{
            imageLabel.setText(ToolKit.userShortName());
        }
    }
}
