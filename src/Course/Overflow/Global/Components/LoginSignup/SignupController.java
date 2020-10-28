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
import javafx.scene.layout.AnchorPane;

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
    private JFXTextField fullname;
    @FXML
    private AnchorPane root;
    private FXMLLoader loader;
    @FXML
    private ChoiceBox<String> accountType;
    private ObservableList<String> numOfItemList;

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


    @FXML
    private void TandC_clk(ActionEvent event) {
    }

    @FXML
    private void pass_clk(ActionEvent event) {
//        int stat = 0;
//        while (press == false) {
//            String pass = user_name.getText().toString();
//            for (int i = 0; i < pass.length(); i++) {
//                if ((pass.charAt(i) >= 'a' && pass.charAt(i) <= 'z')) {
//                    stat = 1;
//                }
//                if ((stat == 1) && (pass.charAt(i) >= 'A' && pass.charAt(i) <= 'Z')) {
//                    stat = 2;
//                }
//                if ((stat == 2) && (Character.isDigit(pass.charAt(i)))) {
//                    stat = 3;
//                }
//                if ((stat == 3) && (Character.isLetter(pass.charAt(i)))) {
//                    stat = 4;
//                }
//            }
//            if (stat == 1) {
//                pass_strength.setText("poor");
//            }
//            if (stat == 2) {
//                pass_strength.setText("medium");
//            }
//            if (stat == 3) {
//                pass_strength.setText("strong");
//            }
//            if (stat == 4) {
//                pass_strength.setText("very strong");
//            }
//            //System.out.println(pass);
//        }
//        System.out.println("hahah");
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
            DB.execute("INSERT INTO SAMPLE values('#', '#')"
                  ,username.getText()
                  ,password.getText()
            );
//            if(DB.execute("sample", "username", username.getText())) return;
//            DB.execute(sql);
            
            //GLOBAL.PAGE_CTRL.loadPage(PageName.ProfileSetting);
            ProfileSettingController profSetCtrl =  (ProfileSettingController) GLOBAL.PAGE_CTRL.loadFXML(GLOBAL.COMPONENTS_LOCATION + "/ProfileSetting.fxml");
            profSetCtrl.createEnvironmentForSignup();
            // GLOBAL.ACCOUNT_TYPE = Person.AccountType.valueOf(accountType.getValue());
        });
    }

    private void setUpAccountTypeChoiceBox() {
    
        numOfItemList = FXCollections.observableArrayList();
        numOfItemList.addAll("Admin", "Student", "Teacher");
        accountType.setItems(numOfItemList);
    }

}
