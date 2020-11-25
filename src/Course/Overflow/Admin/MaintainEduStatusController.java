
package Course.Overflow.Admin;

import Course.Overflow.DB;
import Course.Overflow.Global.EducationalStatus;
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
 * @author Shammya
 */
public class MaintainEduStatusController implements Initializable {

    @FXML
    private TableView<EducationalStatus> table;
    @FXML
    private TableColumn<EducationalStatus, Integer> id_col;
    @FXML
    private TableColumn<EducationalStatus, String> type_col;
    @FXML
    private TableColumn<EducationalStatus, String> adminid_col;
    @FXML
    private JFXButton add;
    @FXML
    private JFXTextField statusName;
    @FXML
    private JFXButton update;
    @FXML
    private JFXButton delete;
    @FXML
    private AnchorPane EduStatusMaintainPane;
    private ObservableList<EducationalStatus> list;
    private String selected = "";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_col.setCellValueFactory(new PropertyValueFactory<EducationalStatus, Integer>("id"));
        type_col.setCellValueFactory(new PropertyValueFactory<EducationalStatus, String>("type"));
        adminid_col.setCellValueFactory(new PropertyValueFactory<EducationalStatus, String>("adminId"));
        loadTable();
        addListener();
    }    
    
    private void addListener()
    {
        add.setOnMouseClicked(event -> {
        if (!statusName.getText().isEmpty()) {
                boolean present = DB.valueExist("EDUCATIONAL_STATUS", "TYPE", statusName.getText());
                if (present) {
                    return;
                }
                EducationalStatus.createNewEduStatus(statusName.getText());
                loadTable();
                selected = "";
                statusName.setText("");
            }
        });
        table.setOnMouseClicked(event -> {
            int idx = table.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                selected = type_col.getCellData(idx).toString();
                statusName.setText(selected);

            } else {
                selected = "";
            }
        });
        update.setOnMouseClicked(event -> {
            boolean flag = !statusName.getText().isEmpty() && !selected.equals(statusName.getText()) && !selected.isEmpty();
            if (flag) {
                EducationalStatus.changeEduStatusName(selected, statusName.getText());
                loadTable();
                selected = "";
                statusName.setText("");
            }
        });
        
        delete.setOnMouseClicked(event -> {
            boolean flag = !selected.isEmpty()&& !statusName.getText().isEmpty() && DB.valueExist("EDUCATIONAL_STATUS", "TYPE", statusName.getText()) ;
            if (flag) {
                EducationalStatus.deleteEduStatus(selected);
                loadTable();
                statusName.setText("");
                selected="";
            }
        });
    }

    private void loadTable() {
        list = FXCollections.observableArrayList();
        list.setAll(EducationalStatus.getList());
        table.setItems(list);
        
    }
    
}
