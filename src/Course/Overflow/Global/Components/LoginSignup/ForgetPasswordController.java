package Course.Overflow.Global.Components.LoginSignup;

import Course.Overflow.Global.Layout.FloatingPane;
import com.jfoenix.controls.JFXButton;
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
    private JFXButton retrieve;
    @FXML
    private Label mesage;
    @FXML
    private AnchorPane rootpanefp;

    public ForgetPasswordController() {
        
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // this.backPane = rootpanefp;
        addListener();
    }
    public void showWindow()
    {
        FloatingPane fp = new FloatingPane();
        fp.setAnchorPane(rootpanefp);
        fp.show();
    }

    void addListener() {
        retrieve.setOnMouseClicked((event) -> {

        });

    }

}
