package Course.Overflow.Global.Components.LoginSignup;

import Course.Overflow.DB;
import Course.Overflow.Global.Layout.FloatingPane;
import Course.Overflow.Global.ToolKit;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private JFXButton retrieve;
    @FXML
    private AnchorPane rootpanefp;
    @FXML
    private Label message;
    
    @FXML
    private JFXButton refresh;

    public ForgetPasswordController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        message.setText("");
        addListener();
    }

    public void showWindow() {
        FloatingPane fp = new FloatingPane();
        fp.setAnchorPane(rootpanefp);
        fp.show();
    }

    void addListener() {
        retrieve.setOnMouseClicked((event) -> {
            if (username.getText().isEmpty() | dob.getValue() == null) {
                message.setText("Please enter username and date of birth !");
                return;
            } else {
                String sql = "SELECT ID , DOB,PASSWORD FROM PERSON WHERE ID = '#' AND DOB = # ";
                System.out.println(sql);
                ResultSet rs = DB.executeQuery(sql,username.getText(),ToolKit.JDateToDDate(ToolKit.localDateToDate(dob.getValue())));
                try {
                    if(rs.next())
                    {
                        
                        String msg = "Your password is "+ rs.getString("PASSWORD");
                        message.setText(msg);
                    }
                    else
                    {
                        message.setText("Please enter valid username and date of birth !");
                        return ;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ForgetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        refresh.setOnMouseClicked((event)->{
        
            username.setText("");
            dob.setValue(null);
            message.setText("");
            //GLOBAL.PAGE_CTRL.loadPage(PageName.Login);

        });

    }

}
