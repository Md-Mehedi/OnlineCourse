package Course.Overflow.Admin;

import Course.Overflow.Course.Category;
import Course.Overflow.Course.Course;
import Course.Overflow.Global.Language;
import Course.Overflow.Teacher.Teacher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class FilteringController implements Initializable {

    @FXML
    private TreeView<String> filterList;
    @FXML
    private JFXButton search;
    @FXML
    private JFXTextField searchField;
    @FXML
    private TableView<Course> courseList;
    @FXML
    private TableColumn<Course, String> courseTitle;
    @FXML
    private TableColumn<Course, String> teacher;
    private CheckBoxTreeItem<String> rootItem;
    private CheckBoxTreeItem<String> category;
    private CheckBoxTreeItem<String> language;
    private CheckBoxTreeItem<String> ratings;
    private CheckBoxTreeItem<String> instructor;
    private ArrayList<CheckBoxTreeItem<String>> checkedItems;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFactory();
        createFilterList();
        adListener();
    }

    private void setFactory() {
        courseTitle.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
        teacher.setCellValueFactory(new PropertyValueFactory<Course, String>("teacherName"));
    }

    private void createFilterList() {
        filterList.setRoot(null);
        rootItem = new CheckBoxTreeItem<String>("Filter List");
        filterList.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        filterList.setRoot(rootItem);
        category = new CheckBoxTreeItem<String>("Category");
        language = new CheckBoxTreeItem<String>("Language");
        ratings = new CheckBoxTreeItem<String>("Ratings");
        instructor = new CheckBoxTreeItem<String>("Instructor");
        checkedItems = new ArrayList<CheckBoxTreeItem<String>>();

        setCategoryFilter();
        setLanguageFilter();
        setInstructorFilter();
        rootItem.getChildren().addAll(category, instructor, language);
        rootItem.setExpanded(true);

    }

    private CheckBox createCheckBox(String name) {
        CheckBox chkbox = new CheckBox(name);
        chkbox.setSelected(false);
        return chkbox;
    }

    private void setCategoryFilter() {
        ArrayList<Category> catglist = Category.getMainCategories();
        for (Category it : catglist) {
            CheckBoxTreeItem<String> item = new CheckBoxTreeItem<String>(it.getName());
            category.getChildren().add(item);
            checkedItems.add(item);
            item.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
                System.out.println(it.getName());
                System.out.println("mehedi boss");
            });
        }
        category.setExpanded(false);
    }

    private void setLanguageFilter() {
        ArrayList<Language> langlist = Language.getList();
        for (Language it : langlist) {
            CheckBoxTreeItem<String> item = new CheckBoxTreeItem<String>(it.getName());
            language.getChildren().add(item);
            checkedItems.add(item);
        }
        language.setExpanded(false);
    }

    private void setInstructorFilter() {
        ArrayList<Teacher> inst = Teacher.getTeacherList();
        for (Teacher it : inst) {
            CheckBoxTreeItem<String> item = new CheckBoxTreeItem<String>(it.getFirstName() + " " + it.getLastName());
            instructor.getChildren().add(item);
            checkedItems.add(item);
        }
        instructor.setExpanded(false);
    }

    private void adListener() {
        filterList.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
        
        });
        filterList.setOnMouseClicked(event -> {
               
        });
    }

    
    

}
