package Course.Overflow.Global.Components.LoginSignup;

import Course.Overflow.DB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Shammya
 */
public class ForgetPasswordController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private DatePicker dob;
    @FXML
    private AnchorPane rootpanefp;
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

    public ForgetPasswordController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        message.setText("Enter your username and birthdate to change your password");
        makeDefault(false);

    }

    private void makeDefault(boolean flag) {
        newpass.setVisible(flag);
        confirmpass.setVisible(flag);
        confirm.setVisible(flag);
        login.setVisible(flag);
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
            boolean a = DB.valueExist("PERSON", "ID", username.getText());
            boolean b = DB.valueExist("PERSON", "DOB", dob.getValue().toString());
            flag = a & b;
            if (flag) {
                makeDefault(flag);
                message.setText(" Now change your passoword ! ");
            } else {
                message.setText("Invalid username or date of birth ! ");
            }
        });
        confirm.setOnMouseClicked(event -> {
            if (!newpass.getText().isEmpty() && !confirmpass.getText().isEmpty()) {
                
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
                pass_strength.setText("");
                poor.setStyle(" -fx-background-color:  #E79D30;");
                medium.setStyle(" -fx-background-color:  #E79D30;");
                strong.setStyle(" -fx-background-color:   #E79D30;");
                very_strong.setStyle(" -fx-background-color:   #E79D30;");
            }
        });
    }

}
