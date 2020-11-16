
package Course.Overflow.Admin;

import Course.Overflow.Global.Designation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author shammya
 */
public class MaintainDesignationController implements Initializable {

    @FXML
    private AnchorPane DesignationMaintainPane;
    @FXML
    private TableView<Designation> table;
    @FXML
    private TableColumn<Designation, Integer> id_col;
    @FXML
    private TableColumn<Designation, String> type_col;
    @FXML
    private TableColumn<Designation, String> adminid_col;
    @FXML
    private JFXButton add;
    @FXML
    private JFXTextField designationName;
    @FXML
    private JFXButton update;
    @FXML
    private JFXButton delete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_col.setCellValueFactory(new PropertyValueFactory<Designation, Integer>("id"));
        type_col.setCellValueFactory(new PropertyValueFactory<Designation, String>("name"));
        adminid_col.setCellValueFactory(new PropertyValueFactory<Designation, String>("adminId"));
    }    
    
}
