package Course.Overflow.Admin;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.Show.CourseDetailsController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import java.net.URL;
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

public class CourseListController implements Initializable {

    @FXML
    private TableView<Course> approved;
    @FXML
    private TableColumn<Course, Integer> id_col;
    @FXML
    private TableColumn<Course, String> title_col;
    @FXML
    private TableColumn<Course, String> name_col;
    @FXML
    private Label countrylabel;
    @FXML
    private TableView<Course> unapproved;
    @FXML
    private TableColumn<Course, Integer> id_col1;
    @FXML
    private TableColumn<Course, String> title_col1;
    @FXML
    private TableColumn<Course, String> name_col1;
    @FXML
    private Label countrylabel1;
    private ObservableList<Course> list;
    private ContextMenu contextMenu;
    private MenuItem itm1;
    private MenuItem itm2;
    private ContextMenu contextMenu1;
    private MenuItem itm11;
    private MenuItem itm22;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFactory();
        adListener();
        loadData();
        setContextMenu1();
        setContextMenu2();
    }

    private void setFactory() {
        id_col.setCellValueFactory(new PropertyValueFactory<Course, Integer>("id"));
        title_col.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
        name_col.setCellValueFactory(new PropertyValueFactory<Course, String>("teacherName"));
        id_col1.setCellValueFactory(new PropertyValueFactory<Course, Integer>("id"));
        title_col1.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
        name_col1.setCellValueFactory(new PropertyValueFactory<Course, String>("teacherName"));

    }
    
    private void setContextMenu2() {
        contextMenu = new ContextMenu();
        itm1 = new MenuItem("Show Details");
        itm1.setOnAction(event -> {
            Course course = unapproved.getSelectionModel().getSelectedItem();
            GLOBAL.PAGE_CTRL.loadPage(PageName.Course);
            ((CourseDetailsController)GLOBAL.PAGE_CTRL.getController()).loadData(course);
            ((CourseDetailsController)GLOBAL.PAGE_CTRL.getController()).setAdminView(this);
        });
        itm2 = new MenuItem("Approve");
        itm2.setOnAction(event -> {
            approveCourse(unapproved.getSelectionModel().getSelectedItem().getId());
        });
        contextMenu.getItems().add(itm1);
        contextMenu.getItems().add(itm2);
        unapproved.setContextMenu(contextMenu);
    }
    public void approveCourse(Integer id){
        Course.approveCourse(id);
        loadData();
    }
    public void unapproveCourse(Integer id) {
        Course.unapproveCourse(id);
        loadData();
    }
    private void setContextMenu1() {
        contextMenu1 = new ContextMenu();
        itm11 = new MenuItem("Show Details");
        itm11.setOnAction(event -> {
            Course course = approved.getSelectionModel().getSelectedItem();
            GLOBAL.PAGE_CTRL.loadPage(PageName.Course);
            ((CourseDetailsController)GLOBAL.PAGE_CTRL.getController()).loadData(course);
            ((CourseDetailsController)GLOBAL.PAGE_CTRL.getController()).setAdminView(this);
        });
        itm22 = new MenuItem("Unapprove");
        itm22.setOnAction(event -> {
            unapproveCourse(approved.getSelectionModel().getSelectedItem().getId());
        });
        contextMenu1.getItems().add(itm11);
        contextMenu1.getItems().add(itm22);
        approved.setContextMenu(contextMenu1);
    }

    private void adListener() {

    }

    private void loadData() {
        list = FXCollections.observableArrayList();
        list.setAll(Course.getApprovedCourses());
        approved.setItems(list);

        list = FXCollections.observableArrayList();
        list.setAll(Course.getUnapprovedCourses());
        unapproved.setItems(list);

    }


}
