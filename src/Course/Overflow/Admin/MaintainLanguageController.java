package Course.Overflow.Admin;

import Course.Overflow.DB;
import Course.Overflow.Global.Language;
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
 * @author Asus
 */
public class MaintainLanguageController implements Initializable {

    @FXML
    private JFXButton add;
    @FXML
    private JFXTextField languageName;
    @FXML
    private TableView<Language> table;
    @FXML
    private TableColumn<Language, String> language_col;
    @FXML
    private TableColumn<Language, String> adminid_col;
    
    @FXML
    private TableColumn<Language, Integer> id_col;
    ObservableList<Language> list;
    private int idx;
    @FXML
    private JFXButton update;
    private String selected = "";
    @FXML
    private JFXButton delete;
    @FXML
    private AnchorPane LanguageMaintainPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_col.setCellValueFactory(new PropertyValueFactory<Language, Integer>("id"));
        language_col.setCellValueFactory(new PropertyValueFactory<Language, String>("name"));
        adminid_col.setCellValueFactory(new PropertyValueFactory<Language, String>("adminId"));
        addListener();
        loadTable();
    }

    private void addListener() {
        add.setOnMouseClicked(event -> {
            if (!languageName.getText().isEmpty()) {
                boolean present = DB.valueExist("LANGUAGE", "NAME", languageName.getText());
                if (present) {
                    return;
                }
                Language.createNewLanguage(languageName.getText());
                loadTable();
                languageName.setText("");
                selected="";
            }
        });

        table.setOnMouseClicked(event -> {
            idx = table.getSelectionModel().getSelectedIndex();
            if(idx >=0)
            {
            selected = language_col.getCellData(idx).toString();
            languageName.setText(selected);
                
            }
            else
                selected="";

        });
        update.setOnMouseClicked(event -> {
            boolean flag = !languageName.getText().isEmpty() && !selected.equals(languageName.getText()) && !selected.isEmpty();
            if (flag) {
                Language.changeLanguageName(selected, languageName.getText());
                loadTable();
                languageName.setText("");
                selected="";
            }

        });
        LanguageMaintainPane.setOnMouseClicked(event -> {
            selected = "";
        });

        delete.setOnMouseClicked(event -> {
            boolean flag = !selected.isEmpty()&& !languageName.getText().isEmpty() && DB.valueExist("LANGUAGE", "NAME", languageName.getText()) ;
            if (flag) {
                Language.deleteLanguage(selected);
                loadTable();
                languageName.setText("");
                selected="";
            }
        });

    }

    private void loadTable() {
        list = FXCollections.observableArrayList();
        list.setAll(Language.getList());
        table.setItems(list);
    }

}
