package Course.Overflow.Global.Components.LoginSignup;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.FloatingPane;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Kazi wasif Amin
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
    private AnchorPane root;
    private FXMLLoader loader;
    private Pane rootContainer;
    @FXML
    private JFXButton forgetPass;
    private ForgetPasswordController forgetPassCtrl;
    private FloatingPane fp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addListener();
        Platform.runLater(() -> {
            rootContainer = (Pane) root.getParent();
        });
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.LOGIN_SIGNUP_LOCATION + "/ForgetPassword.fxml"));
            AnchorPane pane = loader.load();
            fp = new FloatingPane();
            fp.setAnchorPane(pane);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            try {
                Person person = Person.validUser(username.getText(), password.getText());
                if (person != null) {
                    System.out.println("successfully logged in to " + username.getText() + " account");

                    GLOBAL.USER = person;
                    GLOBAL.PAGE_CTRL.loadPage(PageName.Home);
                } else {
                    int state = JOptionPane.showConfirmDialog(null, "Invalid User ID or Password ! ", "select", JOptionPane.CANCEL_OPTION);
                    if (state == 0) {
                        username.setText("");
                        password.setText("");
                    } else {
                        int state1 = JOptionPane.showConfirmDialog(null, "Do you wish to signup ! ", "select", JOptionPane.CANCEL_OPTION);
                        if (state1 == 0) {
                            GLOBAL.PAGE_CTRL.loadPage(PageName.Signup);
                        } else {
                            username.setText("");
                            password.setText("");
                        }

                    }
                }

            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        forgetPass.setOnMouseClicked((event) -> {
            fp.show();
        });

    }
}
