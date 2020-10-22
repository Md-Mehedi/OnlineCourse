/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components.LoginSignup;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class LoginController implements Initializable {

    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton signUpBtn;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXButton forgetPassBtn;
    @FXML
    private AnchorPane root;
    private FXMLLoader loader;
    private Pane rootContainer;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addListener();
        Platform.runLater(() -> {
            rootContainer = (Pane) root.getParent();
        });
    }    

    private void addListener() {
        signUpBtn.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.Signup);
//            try {
//                loader = new FXMLLoader(getClass().getResource(GLOBAL.LOGIN_SIGNUP_LOCATION + "/Signup.fxml"));
//                AnchorPane pane = loader.load();
//                
//                rootContainer = (Pane) root.getParent();
//                
//                FadeTransition fadeIn = new FadeTransition(Duration.millis(600), root);
//                fadeIn.setFromValue(1);
//                fadeIn.setToValue(0.2);
//                fadeIn.play();
//                FadeTransition fadeOut = new FadeTransition(Duration.millis(600), pane);
//                fadeOut.setFromValue(0);
//                fadeOut.setToValue(1);
//                fadeIn.setOnFinished((event1) -> {
//                    pane.setOpacity(0);
//                    rootContainer.getChildren().add(pane);
//                    fadeOut.play();
//                });
//                fadeOut.setOnFinished((event1) -> {
//                    rootContainer.getChildren().remove(root);
//                });
//            } catch (IOException ex) {
//                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//            }
        });
        loginBtn.setOnMouseClicked((event) -> {
            String sql = "SELECT PASSWORD FROM PERSON WHERE USERNAME = " + username.getText();
            GLOBAL.PAGE_CTRL.loadPage(PageName.Home);
        });
    }
}
