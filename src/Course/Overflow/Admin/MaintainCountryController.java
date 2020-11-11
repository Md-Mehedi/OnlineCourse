package Course.Overflow.Admin;

import Course.Overflow.DB;
import Course.Overflow.Global.Country;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class MaintainCountryController implements Initializable {

    @FXML
    private AnchorPane CountryMaintainPane;
    @FXML
    private TableView<Country> table;
    @FXML
    private TableColumn<Country, Integer> id_col;
    @FXML
    private TableColumn<Country, String> country_col;
    @FXML
    private TableColumn<Country, String> adminid_col;
    @FXML
    private JFXButton add;
    @FXML
    private Label countrylabel;
    @FXML
    private JFXTextField countryName;
    @FXML
    private JFXButton delete;
    private ObservableList<Country> list;
    @FXML
    private JFXButton update;
    private String selected = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_col.setCellValueFactory(new PropertyValueFactory<Country, Integer>("id"));
        country_col.setCellValueFactory(new PropertyValueFactory<Country, String>("name"));
        adminid_col.setCellValueFactory(new PropertyValueFactory<Country, String>("adminId"));
        addListener();
        loadTable();
    }

    private void addListener() {
        add.setOnMouseClicked(event -> {
            if (!countryName.getText().isEmpty()) {
                boolean present = DB.valueExist("COUNTRY", "NAME", countryName.getText());
                if (present) {
                    return;
                }
                Country.createNewCountry(countryName.getText());
                loadTable();
                selected = "";
                countryName.setText("");
            }

        });

        table.setOnMouseClicked(event -> {
            int idx = table.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                selected = country_col.getCellData(idx).toString();
                countryName.setText(selected);

            } else {
                selected = "";
            }

        });
        update.setOnMouseClicked(event -> {
            boolean flag = !countryName.getText().isEmpty() && !selected.equals(countryName.getText()) && !selected.isEmpty();
            if (flag) {
                Country.changeCountryName(selected, countryName.getText());
                loadTable();
                selected = "";
                countryName.setText("");
            }

        });
        delete.setOnMouseClicked(event -> {
            boolean flag = !selected.isEmpty()&& !countryName.getText().isEmpty() && DB.valueExist("COUNTRY", "NAME", countryName.getText()) ;
            if (flag) {
                Country.deleteCountry(selected);
                loadTable();
                countryName.setText("");
                selected="";
            }
        });
    }

    private void loadTable() {
        list = FXCollections.observableArrayList();
        list.setAll(Country.getList());
        table.setItems(list);
    }
}
