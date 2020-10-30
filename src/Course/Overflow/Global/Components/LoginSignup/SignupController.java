package Course.Overflow.Global.Components.LoginSignup;

import Course.Overflow.DB;
import Course.Overflow.Global.Components.ProfileSettingController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Mehedi,Shammya
 */
public class SignupController implements Initializable {

    @FXML
    private JFXPasswordField password;
    @FXML
    private Label poor;
    @FXML
    private Label medium;
    @FXML
    private Label strong;
    @FXML
    private Label very_strong;
    @FXML
    private Label pass_strength;
    Boolean press = false;
    int stat = 0;
    int[] state = new int[]{0, 0, 0, 0};
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXButton signupBtn;
    @FXML
    private AnchorPane root;
    private FXMLLoader loader;
    @FXML
    private ChoiceBox<String> accountType;
    private ObservableList<String> numOfItemList;
    @FXML
    private JFXTextField email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpAccountTypeChoiceBox();
        addListener();

    }

    @FXML
    private void TandC_clk(ActionEvent event) {
    }

    private void check_pass_strength(ActionEvent event) {

        //System.out.println(pass);
        // }
    }

    private void addListener() {
        loginBtn.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.Login);
//            try {
//                loader = new FXMLLoader(getClass().getResource(GLOBAL.LOGIN_SIGNUP_LOCATION + "/Login.fxml"));
//                AnchorPane pane = loader.load();
//                
//                Pane p = (Pane) root.getParent();
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
//                    p.getChildren().add(pane);
//                    fadeOut.play();
//                });
//                fadeOut.setOnFinished((event1) -> {
//                    p.getChildren().remove(root);
//                });
//            } catch (IOException ex) {
//                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//            }
        });
        signupBtn.setOnMouseClicked((event1) -> {
            if (password.getText().toString() == "") {
                JOptionPane.showConfirmDialog(null, "Password cannot be empty ! ", "select", JOptionPane.CANCEL_OPTION);
            } else {
//                String sql = "SELECT PASSWORD FROM PERSON WHERE ID = '#' AND PASSWORD = '#'";
//                ResultSet rs = DB.executeQuery(sql, username.getText(), password.getText());
                if (DB.valueExist("PERSON", "EMAIL", email.getText()) == true || DB.valueExist("PERSON", "USERNAME", username.getText()) == true) {
                    int stat = JOptionPane.showConfirmDialog(null, "You have already signed up! Please Log in ", "select", JOptionPane.CANCEL_OPTION);
                    //DB.execute("INSERT INTO SAMPLE values('#', '#')", username.getText(), password.getText());
                    if (stat == 0) {
                        GLOBAL.PAGE_CTRL.loadPage(PageName.Login);
                    } else {
                        email.setText("");
                        username.setText("");
                        password.setText("");
                    }
                } else {
                    ProfileSettingController profSetCtrl = (ProfileSettingController) GLOBAL.PAGE_CTRL.loadFXML(GLOBAL.COMPONENTS_LOCATION + "/ProfileSetting.fxml");
                    profSetCtrl.createEnvironmentForSignup();
                    profSetCtrl.setUsernamePassword(email.getText(), username.getText(), password.getText());
                }//            if(DB.execute("sample", "username", username.getText())) return;
//            DB.execute(sql);
                //GLOBAL.PAGE_CTRL.loadPage(PageName.ProfileSetting);
                // GLOBAL.ACCOUNT_TYPE = Person.AccountType.valueOf(accountType.getValue());
            }

        });
        password.setOnKeyTyped((event2) -> {
            //String spec_char = "!@#$%^&*()<>,./?;':+=-{[}]|~`\\";
            // while (press == false) {
            String pass = password.getText().toString();
//        for (int i = 0; i < pass.length(); i++) {
//            if ((pass.charAt(i) >= 'a' && pass.charAt(i) <= 'z')) {
//                state[0] = 1;
//            } else if ((state[0] == 1) && (pass.charAt(i) >= 'A' && pass.charAt(i) <= 'Z')) {
//                state[1] = 1;
//            } else if ((state[1] == 1) && (Character.isDigit(pass.charAt(i)))) {
//                state[2] = 1;
//            } else if ((state[2] == 1) && (spec_char.indexOf(pass.charAt(i)) != -1)) {
//                state[3] = 1;
//            }
//        }
//        stat = state[0] + state[1] + state[2] + state[3];
            int len = pass.length();
            if (len <= 2 && len > 0) {
                pass_strength.setText("poor");
                poor.setStyle(" -fx-background-color: red;");
                medium.setStyle(" -fx-background-color:  #E79D30;");
                strong.setStyle(" -fx-background-color:   #E79D30;");
                very_strong.setStyle(" -fx-background-color:   #E79D30;");
            } else if (len > 2 && len <= 4) {
                pass_strength.setText("medium");
                medium.setStyle(" -fx-background-color: yellow;");
                strong.setStyle(" -fx-background-color:   #E79D30;");
                very_strong.setStyle(" -fx-background-color:   #E79D30;");
            } else if (len > 4 && len <= 6) {
                pass_strength.setText("strong");
                strong.setStyle(" -fx-background-color: blue;");
            } else if (len > 6) {
                pass_strength.setText("very strong");
                very_strong.setStyle(" -fx-background-color: #39FF14;");
            } else {
                pass_strength.setText("");
                poor.setStyle(" -fx-background-color:  #E79D30;");
                medium.setStyle(" -fx-background-color:  #E79D30;");
                strong.setStyle(" -fx-background-color:   #E79D30;");
                very_strong.setStyle(" -fx-background-color:   #E79D30;");
            }
        });
    }

    private void setUpAccountTypeChoiceBox() {

        numOfItemList = FXCollections.observableArrayList();
        numOfItemList.addAll("Admin", "Student", "Teacher");
        accountType.setItems(numOfItemList);
    }

    @FXML
    private void check_pass_strength(MouseEvent event) {
    }

    @FXML
    private void pass_clk(ActionEvent event) {
    }

}
