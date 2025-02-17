
package Course.Overflow.Admin;

import Course.Overflow.DB;
import Course.Overflow.Global.Designation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ObservableList<Designation> list;
    private String selected = "";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_col.setCellValueFactory(new PropertyValueFactory<Designation, Integer>("id"));
        type_col.setCellValueFactory(new PropertyValueFactory<Designation, String>("type"));
        adminid_col.setCellValueFactory(new PropertyValueFactory<Designation, String>("adminId"));
        loadTable();
        addListener();
    }    
    private void addListener()
    {
        add.setOnMouseClicked(event -> {
        if (!designationName.getText().isEmpty()) {
                boolean present = DB.valueExist("DESIGNATION", "TYPE", designationName.getText());
                if (present) {
                    return;
                }
                Designation.createNewDesignation(designationName.getText());
                loadTable();
                selected = "";
                designationName.setText("");
            }
        });
        table.setOnMouseClicked(event -> {
            int idx = table.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                selected = type_col.getCellData(idx).toString();
                designationName.setText(selected);

            } else {
                selected = "";
            }
        });
        update.setOnMouseClicked(event -> {
            boolean flag = !designationName.getText().isEmpty() && !selected.equals(designationName.getText()) && !selected.isEmpty();
            if (flag) {
                Designation.changeDesignationName(selected, designationName.getText());
                loadTable();
                selected = "";
                designationName.setText("");
            }
        });
        
        delete.setOnMouseClicked(event -> {
            boolean flag = !selected.isEmpty()&& !designationName.getText().isEmpty() && DB.valueExist("DESIGNATION", "TYPE", designationName.getText()) ;
            if (flag) {
                Designation.deleteDesignation(selected);
                loadTable();
                designationName.setText("");
                selected="";
            }
        });
    }

    private void loadTable() {
        list = FXCollections.observableArrayList();
        list.setAll(Designation.getList());
        table.setItems(list);
        
    }
    
}
