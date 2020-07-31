/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Global.Components.Notification.NotificationView;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.ContainerPage;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class HeaderController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label myCourse;
    @FXML
    private StackPane notificationIcon;
    @FXML
    private Label notificationNum;
    @FXML
    private StackPane profile;
    @FXML
    private Circle profileCircle;
    @FXML
    private Label profileName;
    private NotificationView notiCtrl;
    private AnchorPane noti;
    @FXML
    private AnchorPane header;
    private AnchorPane profilePane;
    @FXML
    private Label leftArrow;
//    public static Label leftArrow;
    @FXML
    private Label rightArrow;
//    public static Label rightArrow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        leftArrow.setOpacity(0.1);
        rightArrow.setOpacity(0.1);
        leftArrow.setOnMouseClicked((event) -> {
            if(ContainerPage.pageIdx>0){
                ContainerPage.pageIdx--;
                ContainerPage.loadPage();
            }
        });
        rightArrow.setOnMouseClicked((event) -> {
            if(ContainerPage.pageIdx < ContainerPage.pages.size()-1){
                ContainerPage.pageIdx++;
                ContainerPage.loadPage();
            }
        });
    }    
    
    
    public void setNotiPane(AnchorPane noti){
        this.noti = noti;
        noti.setVisible(false);
        setNotiPanePosition();
    }
    
    public void setProfilePane(AnchorPane profile){
        this.profilePane = profile;
        profilePane.setVisible(false);
        setProfilePanePosition();
    }
    
    private void setNotiPanePosition(){
        Platform.runLater(()->{
            noti.setLayoutY(header.getHeight());
            noti.setLayoutX(header.getWidth() - noti.getWidth());
            
            logo.setOnMouseClicked((event)->{
                GLOBAL.PAGE_CTRL.setHomePage();
            });
        });
        notificationIcon.setOnMouseClicked((MouseEvent)->{
            FadeTransition t = new FadeTransition(Duration.millis(500), noti);
            if(noti.isVisible()){
                t.setFromValue(1);
                t.setToValue(0);
                t.play();
                t.setOnFinished((event)->{
                    noti.setVisible(false);
                });
            }
            else{
                if(profilePane.isVisible()) profilePane.setVisible(false);
                noti.setVisible(true);
                t.setFromValue(0);
                t.setToValue(1);
                t.play();
            }
        });
//        notificationIcon.setOnMouseExited((MouseEvent)->{
//            FadeTransition t = new FadeTransition(Duration.millis(500), noti);
//            t.setFromValue(1);
//            t.setToValue(0);
//            t.play();
//            t.setOnFinished((event) -> {
//                noti.setVisible(false);
//            });
//        });
    }

    private void setProfilePanePosition() {
        Platform.runLater(()->{
            profilePane.setLayoutY(header.getHeight());
            profilePane.setLayoutX(header.getWidth() - profilePane.getWidth());
        });
        profile.setOnMouseClicked((MouseEvent)->{
            FadeTransition t = new FadeTransition(Duration.millis(500), profilePane);
            if(profilePane.isVisible()){
                t.setFromValue(1);
                t.setToValue(0);
                t.play();
                t.setOnFinished((event)->{
                    profilePane.setVisible(false);
                });
            }
            else{
                if(noti.isVisible()) noti.setVisible(false);
                profilePane.setVisible(true);
                t.setFromValue(0);
                t.setToValue(1);
                t.play();
            }
        });
    }

    public Label getLeftArrow() {
        return leftArrow;
    }

    public Label getRightArrow() {
        return rightArrow;
    }
}
