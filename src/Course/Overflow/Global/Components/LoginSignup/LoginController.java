package Course.Overflow.Global.Components.LoginSignup;

import Course.Overflow.DB;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
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
            String sql = "SELECT PASSWORD FROM PERSON WHERE ID = '#' AND PASSWORD = '#'";
            try {
                ResultSet rs = DB.executeQuery(sql, username.getText(), password.getText());
                if (rs.next() == true) {
                    System.out.println("successfully logged in to " + username.getText() + " account");
                    GLOBAL.PAGE_CTRL.loadPage(PageName.Home);
                } else {
                    JOptionPane.showConfirmDialog(null, "Invalid User ID or Password ! ", "select", JOptionPane.CANCEL_OPTION);
                }

            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }
}
