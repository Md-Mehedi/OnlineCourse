package Course.Overflow.Admin;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.PersonPreviewController;
import Course.Overflow.Student.Student;
import Course.Overflow.Teacher.Teacher;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShowPersonController implements Initializable {

    private ObservableList<Teacher> teacherList;
    private ObservableList<Student> studentList;
    private ContextMenu contextMenu;
    private MenuItem itm1;
    private MenuItem itm2;

    @FXML
    private TableView<Teacher> teachertable;
    @FXML
    private TableColumn<Teacher, String> usernamet;
    @FXML
    private TableColumn<Teacher, Integer> coursecreated;
    @FXML
    private TableColumn<Teacher, Integer> coursepurchased;
    @FXML
    private TableView<Student> studenttable;
    @FXML
    private TableColumn<Student, String> usernames;
    @FXML
    private TableColumn<Student, Integer> courseowned;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFactory();
        loadData();
        adListener();
        setContextMenus();
        setContextMenut();
    }

    private void setContextMenus() {
        contextMenu = new ContextMenu();
        itm1 = new MenuItem("Show Details");
        itm1.setOnAction(event -> {
            if (studenttable.getSelectionModel().getSelectedItem() != null) {
                GLOBAL.PAGE_CTRL.loadPage(PageName.PersonDetails);
                PersonPreviewController ctrl = (PersonPreviewController) GLOBAL.PAGE_CTRL.getController();
                ctrl.loadData(studenttable.getSelectionModel().getSelectedItem());
            } else {
                studenttable.setSelectionModel(null);
            }

//            Person selected = table.getSelectionModel().getSelectedItem();
//            ProfileSettingController profSetCtrl = (ProfileSettingController) GLOBAL.PAGE_CTRL.loadFXML(GLOBAL.COMPONENTS_LOCATION + "/ProfileSetting.fxml");
//            profSetCtrl.createEnvironmentForSignup(selected.getEmail(), selected.getUsername(), selected.getPassword());
        });

        contextMenu.getItems().add(itm1);
        studenttable.setContextMenu(contextMenu);
    }

    private void setContextMenut() {
        contextMenu = new ContextMenu();
        itm1 = new MenuItem("Show Details");
        itm1.setOnAction(event -> {
            if (teachertable.getSelectionModel().getSelectedItem() != null) {
                GLOBAL.PAGE_CTRL.loadPage(PageName.PersonDetails);
                PersonPreviewController ctrl = (PersonPreviewController) GLOBAL.PAGE_CTRL.getController();
                ctrl.loadData(teachertable.getSelectionModel().getSelectedItem());
            }
            else {
                teachertable.setSelectionModel(null);
            }

//            Person selected = table.getSelectionModel().getSelectedItem();
//            ProfileSettingController profSetCtrl = (ProfileSettingController) GLOBAL.PAGE_CTRL.loadFXML(GLOBAL.COMPONENTS_LOCATION + "/ProfileSetting.fxml");
//            profSetCtrl.createEnvironmentForSignup(selected.getEmail(), selected.getUsername(), selected.getPassword());
        });
        contextMenu.getItems().add(itm1);
        teachertable.setContextMenu(contextMenu);
    }

    private void adListener() {

    }

    private void setFactory() {
        usernamet.setCellValueFactory(new PropertyValueFactory<Teacher, String>("username"));
        coursecreated.setCellValueFactory(new PropertyValueFactory<Teacher, Integer>("courseCreated"));
        coursepurchased.setCellValueFactory(new PropertyValueFactory<Teacher, Integer>("coursePurchased"));
        usernames.setCellValueFactory(new PropertyValueFactory<Student, String>("username"));
        courseowned.setCellValueFactory(new PropertyValueFactory<Student, Integer>("courseOwned"));
    }

    private void loadData() {
        teacherList = FXCollections.observableArrayList();
        teacherList.addAll(Teacher.getTeacherList());
        teachertable.setItems(teacherList);
        studentList = FXCollections.observableArrayList();
        studentList.addAll(Student.getStudentList());
        studenttable.setItems(studentList);
    }
}
