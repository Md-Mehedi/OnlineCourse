package Course.Overflow.Global.Page;

import Course.Overflow.Course.Category;
import Course.Overflow.Course.Course;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Language;
import Course.Overflow.Global.Layout.PageByPageLayoutController;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.Teacher;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Md Mehedi Hasan
 */
public class SearchResultPage extends Page {

    private final HBox container;
    private VBox courseBoxContainer;
    private VBox filterContainer;
    private FXMLLoader loader;
    private VBox checkedFilterContainer;
    private ArrayList<CheckBox> chceckedCBList;
    private ArrayList<HBox> chceckedNameBoxList;
    private ArrayList<VBox> subFilterVBoxList;
    private PageByPageLayoutController courseBoxesCtrl;
    private ArrayList<Category> categoryList;
    private ArrayList<Teacher> teacherList;
    private ArrayList<Language> languageList;
    private String sql;

    public SearchResultPage() {
        super(PageName.SearchResult);
        sql = "SELECT ID FROM COURSE WHERE ";
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        chceckedCBList = new ArrayList<>();
        chceckedNameBoxList = new ArrayList<>();
        subFilterVBoxList = new ArrayList<>();

        courseBoxContainer = new VBox();
        filterContainer = new VBox();
        container = new HBox(filterContainer, courseBoxContainer);
        root.getChildren().add(container);
        ToolKit.setAnchor(container, 20, 0, 20, 0);

        root.getStyleClass().add("searchResultPageContainer");
        courseBoxContainer.getStyleClass().add("courseBoxContainer");
        filterContainer.getStyleClass().add("filterContainer");

        readyTheCheckedFilterContainer();
        readyCategoryFilterList();
        readyTeacherFilterList();
    }

    private void readyTheCheckedFilterContainer() {
        Label label = new Label("Courses are showing for these filter");
        label.getStyleClass().add("title7");

        Region region = new Region();
        region.setPrefHeight(15);

        checkedFilterContainer = new VBox(label, region);
        checkedFilterContainer.getStyleClass().add("checkedListVBox");
    }

    private void readyCourseBoxes(ArrayList<Course> list) {
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.LAYOUT_LOCATION + "/PageByPageLayout.fxml"));
            courseBoxContainer.getChildren().add(loader.load());
            courseBoxesCtrl = loader.<PageByPageLayoutController>getController();
            courseBoxesCtrl.setUpPage(list, PageByPageLayoutController.BoxViewType.GridView);
        } catch (IOException ex) {
            Logger.getLogger(SearchResultPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readyCategoryFilterList() {
        int idx = 0;
        Label label = new Label("Category");
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_DOUBLE_DOWN);
        icon.setSize("30");
        HBox box = new HBox(label, icon);
        box.getStyleClass().add("filterLabel");
        VBox subFilterContainer = new VBox(box);
        filterContainer.getChildren().add(subFilterContainer);
        
        VBox vbox = new VBox();
        vbox.getStyleClass().add("subFilterContainer");
        categoryList = Category.getMainCategories();
        for (int i = 0; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);
            CheckBox cb = new CheckBox(category.getName());
            vbox.getChildren().add(cb);
            cb.setOnMouseClicked((event) -> {
                if(cb.isSelected()){
                    sql += " CATEGORY_ID = " + category.getId();
                    System.out.println(sql);
                }
                else{
                    
                }
                updateCurrentFilters(cb);
            });
        }
        subFilterVBoxList.add(idx, vbox);
        
        box.setOnMouseClicked((event) -> {
            if (icon.getGlyphName() == "ANGLE_DOUBLE_DOWN") {
                icon.setGlyphName("ANGLE_DOUBLE_UP");
                subFilterContainer.getChildren().add(subFilterVBoxList.get(idx));
            } else {
                icon.setGlyphName("ANGLE_DOUBLE_DOWN");
                subFilterContainer.getChildren().remove(subFilterVBoxList.get(idx));
            }
        });
    }
    private void readyTeacherFilterList() {
        int idx = 0;
        Label label = new Label("Instructor");
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_DOUBLE_DOWN);
        icon.setSize("30");
        HBox box = new HBox(label, icon);
        box.getStyleClass().add("filterLabe2");
        VBox subFilterContainer = new VBox(box);
        filterContainer.getChildren().add(subFilterContainer);
        
        VBox vbox = new VBox();
        vbox.getStyleClass().add("subFilterContainer");
        teacherList = Teacher.getTeacherList();
        for (int i = 0; i < teacherList.size(); i++) {
            Teacher teacher = teacherList.get(i);
            CheckBox cb = new CheckBox(teacher.getFullName());
            vbox.getChildren().add(cb);
            cb.setOnMouseClicked((event) -> {
                if(cb.isSelected()){
                    sql += " TEACHER_ID = " + teacher.getUsername();
                    System.out.println(sql);
                }
                else{
                    
                }
                updateCurrentFilters(cb);
            });
        }
        subFilterVBoxList.add(idx, vbox);
        
        box.setOnMouseClicked((event) -> {
            if (icon.getGlyphName() == "ANGLE_DOUBLE_DOWN") {
                icon.setGlyphName("ANGLE_DOUBLE_UP");
                subFilterContainer.getChildren().add(subFilterVBoxList.get(idx));
            } else {
                icon.setGlyphName("ANGLE_DOUBLE_DOWN");
                subFilterContainer.getChildren().remove(subFilterVBoxList.get(idx));
            }
        });
    }
        private void readyLanguageFilterList() {
        int idx = 0;
        Label label = new Label("Language");
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_DOUBLE_DOWN);
        icon.setSize("30");
        HBox box = new HBox(label, icon);
        box.getStyleClass().add("filterLabe3");
        VBox subFilterContainer = new VBox(box);
        filterContainer.getChildren().add(subFilterContainer);
        
        VBox vbox = new VBox();
        vbox.getStyleClass().add("subFilterContainer");
        languageList = Language.getList();
        for (int i = 0; i < languageList.size(); i++) {
            Language language = languageList.get(i);
            CheckBox cb = new CheckBox(language.getName());
            vbox.getChildren().add(cb);
            cb.setOnMouseClicked((event) -> {
                if(cb.isSelected()){
                    sql += " LANGUAGE_ID = " + language.getId();
                    System.out.println(sql);
                }
                else{
                    
                }
                updateCurrentFilters(cb);
            });
        }
        subFilterVBoxList.add(idx, vbox);
        
        box.setOnMouseClicked((event) -> {
            if (icon.getGlyphName() == "ANGLE_DOUBLE_DOWN") {
                icon.setGlyphName("ANGLE_DOUBLE_UP");
                subFilterContainer.getChildren().add(subFilterVBoxList.get(idx));
            } else {
                icon.setGlyphName("ANGLE_DOUBLE_DOWN");
                subFilterContainer.getChildren().remove(subFilterVBoxList.get(idx));
            }
        });
    }

    private void updateCurrentFilters(CheckBox cb) {
        HBox box = new HBox();
        if (cb.isSelected()) {
            Text text = new Text(cb.getText());

            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon.setSize("20");

            box.getChildren().addAll(text, region, icon);
            box.getStyleClass().add("checkedListHbox");

            checkedFilterContainer.getChildren().add(box);
            chceckedCBList.add(cb);
            chceckedNameBoxList.add(box);

            if (!filterContainer.getChildren().contains(checkedFilterContainer)) {
                filterContainer.getChildren().add(0, checkedFilterContainer);
            }

            box.setOnMouseClicked((event2) -> {
                cb.setSelected(false);
                removeFilter(cb);
            });
        } else {
            removeFilter(cb);
        }
    }

    private void removeFilter(CheckBox cb) {
        int idx = chceckedCBList.indexOf(cb);
        checkedFilterContainer.getChildren().remove(
                chceckedNameBoxList.get(idx)
        );
        chceckedCBList.remove(idx);
        chceckedNameBoxList.remove(idx);
        if (chceckedNameBoxList.size() == 0) {
            filterContainer.getChildren().remove(checkedFilterContainer);
        }
    }

    public void search(String text) {
        if (!text.isEmpty()) {
            Set<Course> set = Course.searchCourse(text);
            ArrayList<Course> list = new ArrayList(set);
            readyCourseBoxes(list);
        }
    }
}
