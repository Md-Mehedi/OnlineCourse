package Course.Overflow.Admin;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.PersonPreviewController;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShowPersonController implements Initializable {

    @FXML
    private TableColumn<Person, String> id_col;
    @FXML
    private TableColumn<Person, String> email_col;
    @FXML
    private TableColumn<Person, String> name_col;
    @FXML
    private TableColumn<Person, Date> dob_col;
    @FXML
    private TableColumn<Person, Date> signup_col;
    @FXML
    private TableColumn<Person, String> type_col;
    @FXML
    private TableColumn<Person, String> fburl_col;
    @FXML
    private Label countrylabel;
    @FXML
    private TableView<Person> table;
    private ObservableList<Person> list;
    private ContextMenu contextMenu;
    private MenuItem itm1;
    private MenuItem itm2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFactory();
        loadData();
        adListener();
        setContextMenu();
    }

    private void setContextMenu() {
        contextMenu = new ContextMenu();
        itm1 = new MenuItem("Show Details");
        itm1.setOnAction(event -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.PersonDetails);
            PersonPreviewController ctrl = (PersonPreviewController) GLOBAL.PAGE_CTRL.getController();
            ctrl.loadData(table.getSelectionModel().getSelectedItem());
//            Person selected = table.getSelectionModel().getSelectedItem();
//            ProfileSettingController profSetCtrl = (ProfileSettingController) GLOBAL.PAGE_CTRL.loadFXML(GLOBAL.COMPONENTS_LOCATION + "/ProfileSetting.fxml");
//            profSetCtrl.createEnvironmentForSignup(selected.getEmail(), selected.getUsername(), selected.getPassword());
        });
        contextMenu.getItems().add(itm1);
        table.setContextMenu(contextMenu);
    }

    private void adListener() {
        table.setOnMouseClicked(event -> {
            Person selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                
            }
        });

    }

    private void setFactory() {
        id_col.setCellValueFactory(new PropertyValueFactory<Person, String>("username"));
        email_col.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        name_col.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        dob_col.setCellValueFactory(new PropertyValueFactory<Person, Date>("dob"));
        signup_col.setCellValueFactory(new PropertyValueFactory<Person, Date>("signupDate"));
        type_col.setCellValueFactory(new PropertyValueFactory<Person, String>("accountType"));
        fburl_col.setCellValueFactory(new PropertyValueFactory<Person, String>("fbURL"));
    }

    private void loadData() {
        list = FXCollections.observableArrayList();
        list.addAll(Person.getList());
        table.setItems(list);
    }
}
