/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
    private Label name;
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
    }    

    @FXML
    private void mouseExited(MouseEvent event) {
    }

    @FXML
    private void mouseEntered(MouseEvent event) {
    }

    @FXML
    private void mouseClicked(MouseEvent event) {
        if(event.getSource()==myCourse){
            GLOBAL.PAGE_CTRL.loadPage(PageName.MyCourse);
        }
        else if(event.getSource()==wishlist){
            GLOBAL.PAGE_CTRL.loadPage(PageName.Wishlist);
        }
        else if(event.getSource()==purchaseHistory){
            GLOBAL.PAGE_CTRL.loadPage(PageName.PurchaseHistory);
        }
        else if(event.getSource()==message){
            GLOBAL.PAGE_CTRL.loadPage(PageName.Messenger);
        }
        else if(event.getSource()==faq){
            GLOBAL.PAGE_CTRL.loadPage(PageName.FAQ);
        }
        else if(event.getSource()==signOut){
            GLOBAL.PAGE_CTRL.loadPage(PageName.Login);
        }
        else if(event.getSource()==setting){
            GLOBAL.PAGE_CTRL.loadPage(PageName.ProfileSetting);
        }
    }    
}
