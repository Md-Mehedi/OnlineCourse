/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components.LoginSignup;

import Course.Overflow.DB;
import Course.Overflow.Global.Components.ProfileSettingController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person.AccountType;
import Course.Overflow.Global.ToolKit;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Asus
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
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXButton signupBtn;
    @FXML
    private AnchorPane root;
    private FXMLLoader loader;
    private ObservableList<String> numOfItemList;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXComboBox<String> accountTypeCB;
//    int stat = 0;
//    int[] state = new int[]{0, 0, 0, 0};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpAccountTypeChoiceBox();
        addListener();

//        System.out.println("hi");
//        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println("check");
//                
//                //while (press == false) 
//                {
//                    String pass = password.getText().toString();
//                    for (int i = 0; i < pass.length(); i++) {
//                        if ((pass.charAt(i) >= 'a' && pass.charAt(i) <= 'z')) {
//                            stat = 1;
//                        }
//                        if ((stat == 1) && (pass.charAt(i) >= 'A' && pass.charAt(i) <= 'Z')) {
//                            stat = 2;
//                        }
//                        if ((stat == 2) && (Character.isDigit(pass.charAt(i)))) {
//                            stat = 3;
//                        }
//                        if ((stat == 3) && (Character.isLetter(pass.charAt(i)))) {
//                            stat = 4;
//                        }
//                    }
//                    if (stat == 1) {
//                        pass_strength.setText("poor");
//                        poor.setStyle("-fx-background-color: red;");
//                        poor.getStyleClass().add("poor-active");
//                    }
//                    if (stat == 2) {
//                        pass_strength.setText("medium");
//                        medium.setStyle("-fx-background-color: yellow;");
//                    }
//                    if (stat == 3) {
//                        pass_strength.setText("strong");
//                        strong.setStyle("-fx-background-color: lightgreen;");
//                    }
//                    if (stat == 4) {
//                        pass_strength.setText("very strong");
//                        very_strong.setStyle("-fx-background-color: green;");
//                    }
//                   
//                    //System.out.println(pass);
//                }
//            }
//        });
//                System.out.println("ok1");
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("ok");
//            }
//        });
//                System.out.println("ok2");
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
        signupBtn.setOnMouseClicked((event) -> {
            
            String em = email.getText().trim();
            String un = username.getText().trim();
            String pwd = password.getText().trim();
            boolean f1 = (!ToolKit.isSpaceMiddle(un)) & ToolKit.isValidString(un);
            boolean f2 = (!ToolKit.isSpaceMiddle(pwd)) & ToolKit.isValidString(pwd);
            boolean f3 = (!ToolKit.isSpaceMiddle(em)) & ToolKit.isValidString(em);
            boolean f = f1 & f2 & f3;
            if (f==false) {
                JOptionPane.showConfirmDialog(null, "The username , email or password cannot contain \\ ,\",\' and whitespace ", "select", JOptionPane.CANCEL_OPTION);
            } else {
                if (!accountTypeCB.getSelectionModel().isEmpty()) {
                    if (!un.isEmpty() && !em.isEmpty()) {
                        if (!pwd.isEmpty()) {
                            if (DB.valueExist("PERSON", "EMAIL", em) != true && DB.valueExist("PERSON", "ID", un) != true) {
                                GLOBAL.ACCOUNT_TYPE = AccountType.valueOf(accountTypeCB.getValue());
                                ProfileSettingController profSetCtrl = (ProfileSettingController) GLOBAL.PAGE_CTRL.loadFXML(GLOBAL.COMPONENTS_LOCATION + "/ProfileSetting.fxml");
                                profSetCtrl.createEnvironmentForSignup(em, un, pwd);
                                GLOBAL.IS_LIGHT = !GLOBAL.IS_LIGHT;
                            } else {
                                int stat = JOptionPane.showConfirmDialog(null, "The username or email is already taken !\n log in instead ", "select", JOptionPane.CANCEL_OPTION);
                                if (stat == 0) {
                                    GLOBAL.PAGE_CTRL.loadPage(PageName.Login);
                                } else {
                                    email.setText("");
                                    username.setText("");
                                    password.setText("");
                                }
                            }
                        } else {
                            JOptionPane.showConfirmDialog(null, "Password cannot be empty ! ", "select", JOptionPane.CANCEL_OPTION);
                        }
                    } else {
                        JOptionPane.showConfirmDialog(null, "username or email cannot be empty ! ", "select", JOptionPane.CANCEL_OPTION);
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "Please select account type ! ", "select", JOptionPane.CANCEL_OPTION);
                }
            }

        });
        password.setOnKeyTyped((event2) -> {
            //String spec_char = "!@#$%^&*()<>,./?;':+=-{[}]|~`\\";
            // while (press == false) {
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
            String pass = password.getText();
            int len = pass.length();
            if (len >=1 && len<=2) {
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
            } else  if (len==0){
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
        numOfItemList.addAll("Select type","Student", "Teacher");
        accountTypeCB.setItems(numOfItemList);
        accountTypeCB.setValue("Select type");
    }

    @FXML
    private void check_pass_strength(MouseEvent event) {
    }

    @FXML
    private void pass_clk(ActionEvent event) {
    }

    @FXML
    private void TandC_clk(ActionEvent event) {
    }

}
