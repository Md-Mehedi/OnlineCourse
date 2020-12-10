package Course.Overflow.Global.Components.LoginSignup;

import Course.Overflow.DB;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.FloatingPane;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.ToolKit;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Shammya
 */
public class ForgetPasswordController extends FloatingPane implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXDatePicker dob;
    @FXML
    private AnchorPane rootpanefp;
    @FXML
    private Label message;
    @FXML
    private JFXButton confirm;
    @FXML
    private JFXPasswordField newpass;
    @FXML
    private JFXPasswordField confirmpass;
    @FXML
    private JFXButton check;
    @FXML
    private JFXButton login;
    private boolean flag = false;
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
    @FXML
    private HBox strength;

    public ForgetPasswordController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.backPane = rootpanefp;
        message.setText("Enter your username and birthdate to change your password");
        makeDefault(false);
        addListener();
    }

    private void makeDefault(boolean flag) {
        newpass.setVisible(flag);
        confirmpass.setVisible(flag);
        confirm.setVisible(flag);
        login.setVisible(flag);
        strength.setVisible(flag);
    }

    public void RefreshWindow() {
        username.setText("");
        dob.setValue(null);
        message.setText("");
    }

    void addListener() {
//        retrieve.setOnMouseClicked((event) -> {
//            if (username.getText().isEmpty() | dob.getValue() == null) {
//                message.setText("Please enter username and date of birth !");
//                return;
//            } else {
//                String sql = "SELECT ID , DOB,PASSWORD FROM PERSON WHERE ID = '#' AND DOB = # ";
//                System.out.println(sql);
//                ResultSet rs = DB.executeQuery(sql, username.getText(), ToolKit.JDateToDDate(ToolKit.localDateToDate(dob.getValue())));
//                try {
//                    if (rs.next()) {
//
//                        String msg = "Your password is " + rs.getString("PASSWORD");
//                        message.setText(msg);
//                    } else {
//                        message.setText("Please enter valid username and date of birth !");
//                        rs.close();
//                        return;
//                    }
//                    rs.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(ForgetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
        check.setOnMouseClicked(event -> {
            if(!username.getText().isEmpty() && dob.getValue()!=null)
            { 
            boolean a = DB.valueExist("PERSON", "ID", username.getText());
            boolean b = DB.valueExistnew("PERSON", "DOB",ToolKit.JDateToDDate(ToolKit.localDateToDate(dob.getValue())) );               
            flag = a & b;
            if (flag) {
                makeDefault(flag);
                message.setText(" Now change your passoword ! ");
            } else {
                message.setText("Invalid username or date of birth ! ");
            }
            }
        });
        confirm.setOnMouseClicked(event -> {
            if (!newpass.getText().isEmpty() && !confirmpass.getText().isEmpty()) {
                Person person = new Person(username.getText());
                if(newpass.getText().equals(confirmpass.getText()))
                {
                    person.setPassword(newpass.getText());
                    message.setText("Password updated successfully !");
                    close();
                }
                else
                {
                    message.setText("New password should match with confirm password !");
                }
            }

        });
        newpass.setOnKeyTyped((event2) -> {
            String pass = newpass.getText();
            int len = pass.length();
            if (len >= 1 && len <= 2) {
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
            } else if (len == 0) {
                pass_strength.setText("poor");
                poor.setStyle(" -fx-background-color:  red;");
                medium.setStyle(" -fx-background-color:  #E79D30;");
                strong.setStyle(" -fx-background-color:   #E79D30;");
                very_strong.setStyle(" -fx-background-color:   #E79D30;");
            }
        });
        login.setOnMouseClicked(event ->{
            GLOBAL.PAGE_CTRL.loadPage(PageName.Login);
        });
    }

}
