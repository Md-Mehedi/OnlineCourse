package Course.Overflow.Global.Page;

import Course.Overflow.Course.Category;
import Course.Overflow.Course.Course;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Language;
import Course.Overflow.Global.Layout.PageByPageLayoutController;
import Course.Overflow.Global.Layout.PageByPageLayoutController.BoxViewType;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.Teacher;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.controlsfx.control.RangeSlider;

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
    private ArrayList<Teacher> teacherList;
    private ArrayList<Language> languageList;
    private String SQLmain;
    private String SQLcondition;
    private String SQLorder;
    private Map<String, VBox> subContainers;
    private VBox categorySubFilterContainer;
    private VBox teacherSubFilterContainer;
    private VBox languageSubFilterContainer;
    private VBox ratingSubFilterContainer;
    private VBox priceSubFilterContainer;
    private Map<String, ArrayList<String>> conditionLists;
    private BoxViewType boxType;
    private ComboBox<String> orderByCB;
    
    private enum Sort{
         NEW_REALESED("New realesed", "ORDER BY PUBLISH_DATE DESC\n")
        ,BEST_SELLER("Best seller", "ORDER BY NUM_OF_STD DESC\n")
        ,MOST_REVIEWED("Most reviewed", "ORDER BY REVIEW_VALUE DESC\n")
        ,MOST_RATED("Most rated", "ORDER BY RATING_VALUE DESC")
        ,PRICE_LOW_TO_HIGH("Price low to high", "ORDER BY MAIN_PRICE ASC\n")
        ,PRICE_HIGH_TO_LOW("Price high to low", "ORDER BY MAIN_PRICE DESC\n");

        String query;
        String text;
        private Sort(String text, String query) {
            this.text = text;
            this.query = query;
        }
        
        public String getText(){
            return text;
        }
        
        public String getQuery(){
            return query;
        }
        
        public static Sort getEnum(String text){
            for(Sort sort : Sort.values()){
                if(sort.getText().equals(text)){
                    return sort;
                }
            }
            return Sort.NEW_REALESED;
        }
    }

    public SearchResultPage() {
        super(PageName.SearchResult);
        conditionLists = new HashMap<>();
        conditionLists.put("OTHERS", new ArrayList<String>());
        conditionLists.put("CATEGORY", new ArrayList<String>());
        conditionLists.put("LANGUAGE", new ArrayList<String>());
        conditionLists.put("TEACHER", new ArrayList<String>());
        conditionLists.put("RATING", new ArrayList<String>());
        conditionLists.put("PRICING", new ArrayList<String>());
        SQLmain = "SELECT DISTINCT C.ID, C.PUBLISH_DATE, (PRICE - PRICE*OFFER/100) MAIN_PRICE, GET_RATING(C.ID) RATING_VALUE, GET_REVIEW(C.ID) REVIEW_VALUE, NUM_OF_STUDENT(C.ID) NUM_OF_STD\n"
                + "FROM COURSE C, COURSE_LANGUAGE L\n"
                + "WHERE C.ID = L.COURSE_ID\n"
                + "AND IS_APPROVED = 'T'\n";
        SQLorder = Sort.NEW_REALESED.getQuery();
        boxType = BoxViewType.GridView;
        
        subContainers = new HashMap<>();
        categorySubFilterContainer = new VBox();
        teacherSubFilterContainer = new VBox();
        languageSubFilterContainer = new VBox();
        ratingSubFilterContainer = new VBox();
        priceSubFilterContainer = new VBox();

        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        chceckedCBList = new ArrayList<>();
        chceckedNameBoxList = new ArrayList<>();
        subFilterVBoxList = new ArrayList<>();

        courseBoxContainer = new VBox();
        filterContainer = new VBox();
        container = new HBox(filterContainer, courseBoxContainer);
//        HBox.setMargin(filterContainer, new Insets(0, 0, 0 , 50));
        root.getChildren().add(container);
        ToolKit.setAnchor(container, 20, 0, 20, 60);

        root.getStyleClass().add("searchResultPageContainer");
        courseBoxContainer.getStyleClass().add("courseBoxContainer");
        filterContainer.getStyleClass().add("filterContainer");

        readySortingChoiceBoxes();
        readyTheCheckedFilterContainer();
        readyCategoryFilters();
        readyTeacherFilters();
        readyLanguageFilters();
        readyRatingFilters();
        readyPriceFilters();
    }
    
    private String generateQuery(){
        SQLcondition = "";
        if(conditionLists.get("OTHERS").size()!=0){
            for(int i=0; i<conditionLists.get("OTHERS").size(); i++){
                SQLcondition += "AND " + conditionLists.get("OTHERS").get(i) + "\n";
            }
        }
        ArrayList<String> conTypeName = new ArrayList();
        conTypeName.add("CATEGORY");
        conTypeName.add("TEACHER");
        conTypeName.add("LANGUAGE");
        conTypeName.add("RATING");
        conTypeName.add("PRICING");
        for(String type : conTypeName){
            if(conditionLists.get(type).size()!=0){
                SQLcondition += "AND (";
                for (int i=0; i<conditionLists.get(type).size(); i++) {
                    if(i!=0) SQLcondition += "OR ";
                    SQLcondition += conditionLists.get(type).get(i) + "\n";
                }
                SQLcondition += ")";
            }
        }
        return SQLcondition;
    }

    private void createAndRunQuery() {
        String sql = SQLmain + generateQuery() + SQLorder;
        System.out.println(sql);
        ArrayList<Course> list = Course.searchCourse(sql);
        if (courseBoxesCtrl == null) {
            readyCourseBoxes(list);
        } else {
            courseBoxesCtrl.setUpPage(list);
        }
    }
    
    private void readySortingChoiceBoxes(){
        orderByCB = new ComboBox<>();
        ObservableList<String> orderByList = FXCollections.observableArrayList();
        for(Sort sort : Sort.values()){
            orderByList.add(sort.getText());
        }
        orderByCB.setItems(orderByList);
        orderByCB.setValue(orderByList.get(0));
        
        ChangeListener listener = new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                Sort sort = Sort.getEnum(orderByList.get(new_value.intValue()));
                SQLorder = sort.getQuery();
                createAndRunQuery();
            }
        };
        orderByCB.getSelectionModel().selectedIndexProperty().addListener(listener);
        
        Label label = new Label("Sorted by ");
        VBox box = new VBox(label, orderByCB);
        box.setStyle(""
              + "-fx-padding: 10;"
              + "-fx-border-color: black;"
              + "-fx-alignment: center;"
              + "-fx-spacing: 10;");
        filterContainer.getChildren().add(box);
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

    private HBox makeFilterBox(String name) {
        Label label = new Label(name);
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_DOUBLE_DOWN);
        icon.setSize("30");
        HBox box = new HBox(label, icon);
        box.getStyleClass().add("filterLabel");
//        VBox subFilterContainer = new VBox();
//        VBox container = new VBox(box, subFilterContainer);
//        AnchorPane root = new AnchorPane(container);
//        ToolKit.setAnchor(container, 0, 0, 0, 0);
//        subContainers.put(name, container);
        return box;
    }
    
    private void readyCategoryFilters(){
        HBox catNameBox = makeFilterBox("Category");
        filterContainer.getChildren().add(catNameBox);
        readyCategorySubFilters();
        triggerForSubfilters(catNameBox, categorySubFilterContainer);
    }
    
    private void readyTeacherFilters(){
        HBox teacherNameBox = makeFilterBox("Teacher");
        filterContainer.getChildren().add(teacherNameBox);
        readyTeacherSubFilters();
        triggerForSubfilters(teacherNameBox, teacherSubFilterContainer);
    }
    
    private void readyLanguageFilters(){
        HBox languageNameBox = makeFilterBox("Language");
        filterContainer.getChildren().add(languageNameBox);
        readyLanguageSubFilters();
        triggerForSubfilters(languageNameBox, languageSubFilterContainer);
    }
    
    private void readyRatingFilters(){
        HBox ratingNameBox = makeFilterBox("Rating");
        filterContainer.getChildren().add(ratingNameBox);
        readyRatingSubFilters();
        triggerForSubfilters(ratingNameBox, ratingSubFilterContainer);
    }
    
    private void readyPriceFilters(){
        HBox priceNameBox = makeFilterBox("Price");
        filterContainer.getChildren().add(priceNameBox);
        readyPriceSubFilters();
        triggerForSubfilters(priceNameBox, priceSubFilterContainer);
    }
    
    private void triggerForSubfilters(HBox trigger, VBox action){
        trigger.setOnMouseClicked((event) -> {
            FontAwesomeIconView icon = (FontAwesomeIconView)trigger.getChildren().get(1);
            if (icon.getGlyphName() == "ANGLE_DOUBLE_DOWN") {
                Integer idx = filterContainer.getChildren().indexOf(trigger);
                filterContainer.getChildren().add(idx+1, action);
                icon.setGlyphName("ANGLE_DOUBLE_UP");
            } else {
                filterContainer.getChildren().remove(action);
                icon.setGlyphName("ANGLE_DOUBLE_DOWN");
            }
        });
    }
    
    private void readyTeacherSubFilters(){
        teacherSubFilterContainer.getChildren().clear();
        teacherSubFilterContainer.getStyleClass().add("subFilterContainer");
        ArrayList<Teacher> teacherList = Teacher.getTeacherList();
        for (int i = 0; i < teacherList.size(); i++) {
            Teacher teacher = teacherList.get(i);
            CheckBox teacherCB = new CheckBox(teacher.getFullName());
            teacherSubFilterContainer.getChildren().add(teacherCB);
            VBox.setMargin(teacherCB, new Insets(5, 0, 5, 0));
            teacherCB.setOnMouseClicked((event) -> {
                String con = "TEACHER_ID = '" + teacher.getUsername() + "'";
                if (teacherCB.isSelected()) {
                    conditionLists.get("TEACHER").add(con);
                } else {
                    if (conditionLists.get("TEACHER").contains(con)) {
                        conditionLists.get("TEACHER").remove(con);
                    }
                }
                createAndRunQuery();
                HBox box = updateCurrentFilters(teacherCB);
                box.setOnMouseClicked((event2) -> {
                    teacherCB.setSelected(false);
                    if (conditionLists.get("TEACHER").contains(con)) {
                        conditionLists.get("TEACHER").remove(con);
                    }
                    removeFilter(teacherCB);
                    createAndRunQuery();
                });
            });
        }
    }
    
    private void readyLanguageSubFilters(){
        languageSubFilterContainer.getChildren().clear();
        languageSubFilterContainer.getStyleClass().add("subFilterContainer");
        ArrayList<Language> languageList = Language.getList();
        for (int i = 0; i < languageList.size(); i++) {
            Language language = languageList.get(i);
            CheckBox languageCB = new CheckBox(language.getName());
            languageSubFilterContainer.getChildren().add(languageCB);
            VBox.setMargin(languageCB, new Insets(5, 0, 5, 0));
            languageCB.setOnMouseClicked((event) -> {
                String con = "LANGUAGE_ID = " + language.getId();
                if (languageCB.isSelected()) {
                    conditionLists.get("LANGUAGE").add(con);
                } else {
                    if (conditionLists.get("LANGUAGE").contains(con)) {
                        conditionLists.get("LANGUAGE").remove(con);
                    }
                }
                createAndRunQuery();
                HBox box = updateCurrentFilters(languageCB);
                box.setOnMouseClicked((event2) -> {
                    languageCB.setSelected(false);
                    if (conditionLists.get("LANGUAGE").contains(con)) {
                        conditionLists.get("LANGUAGE").remove(con);
                    }
                    removeFilter(languageCB);
                    createAndRunQuery();
                });
            });
        }
    }
    
    private void readyRatingSubFilters(){
        ratingSubFilterContainer.getChildren().clear();
        ratingSubFilterContainer.getStyleClass().add("subFilterContainer");
        RangeSlider slider = new RangeSlider(0, 5, 0, 5);
        slider.setMajorTickUnit(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        ratingSubFilterContainer.getChildren().add(slider);
        VBox.setMargin(slider, new Insets(5));
        Label lowLabel = new Label("Low value");
        Label highLabel = new Label("High value");
        highLabel.setPrefWidth(200);
        lowLabel.setPrefWidth(200);
        JFXTextField lowField = new JFXTextField("0");
        JFXTextField highField = new JFXTextField("5");
        GridPane grid = new GridPane();
        grid.add(lowLabel, 0, 0);
        grid.add(lowField, 1, 0);
        grid.add(highLabel, 0, 1);
        grid.add(highField, 1, 1);
        ratingSubFilterContainer.getChildren().add(grid);
  
        slider.lowValueProperty().addListener((observable, oldValue, newValue) -> {
            lowField.setText(ToolKit.DoubleToString(newValue.doubleValue()));
        });
        slider.highValueProperty().addListener((observable, oldValue, newValue) -> {
            highField.setText(ToolKit.DoubleToString(newValue.doubleValue()));
        });
        slider.setOnMouseReleased((event) -> {
            makeRatingQuery(Double.parseDouble(lowField.getText()), Double.parseDouble(highField.getText()));
        });
        lowField.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER){
                if(lowField.getText().isEmpty()) return;
                slider.setLowValue(Double.parseDouble(lowField.getText()));
                makeRatingQuery(Double.parseDouble(lowField.getText()), Double.parseDouble(highField.getText()));
            }
        });
        lowField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()) return;
            if (!newValue.matches("[0-5]([\\.]\\d{0,2})?")) {
                lowField.setText(oldValue);
            }
        });
        highField.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER){
                if(highField.getText().isEmpty()) return;
                slider.setHighValue(Double.parseDouble(highField.getText()));
                makeRatingQuery(Double.parseDouble(lowField.getText()), Double.parseDouble(highField.getText()));
            }
        });
        highField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()) return;
            if (!newValue.matches("[0-5]([\\.]\\d{0,2})?")) {
                highField.setText(oldValue);
            }
        });
//        for (int i = 1; i <= 5; i++) {
//            Integer value = i;
//            String name = value.toString();
//            if(value!=5) name += " and up";
//            CheckBox ratingCB = new CheckBox(name);
//            ratingSubFilterContainer.getChildren().add(ratingCB);
//            VBox.setMargin(ratingCB, new Insets(5, 0, 5, 0));
//            ratingCB.setOnMouseClicked((event) -> {
//                String con = "(SELECT AVG(R.VALUE) FROM RATING R WHERE R.COURSE_ID = C.ID GROUP BY COURSE_ID) >= " + value;
//                if (ratingCB.isSelected()) {
//                    conditionLists.get("RATING").add(con);
//                } else {
//                    if (conditionLists.get("RATING").contains(con)) {
//                        conditionLists.get("RATING").remove(con);
//                    }
//                }
//                makeAndRunQuery();
//                HBox box = updateCurrentFilters(ratingCB);
//                box.setOnMouseClicked((event2) -> {
//                    ratingCB.setSelected(false);
//                    if (conditionLists.get("RATING").contains(con)) {
//                        conditionLists.get("RATING").remove(con);
//                    }
//                    removeFilter(ratingCB);
//                    makeAndRunQuery();
//                });
//            });
//        }
    }
    
    private void readyPriceSubFilters(){
        priceSubFilterContainer.getChildren().clear();
        priceSubFilterContainer.getStyleClass().add("subFilterContainer");
        Integer lowValue = 0;
        Integer highValue = 10000;
        Integer rangeDif = highValue - lowValue;
        RangeSlider slider = new RangeSlider(lowValue, highValue, lowValue, highValue);
//        slider.setShowTickLabels(true);
//        slider.setShowTickMarks(true);
        priceSubFilterContainer.getChildren().add(slider);
        VBox.setMargin(slider, new Insets(5));
        Label lowLabel = new Label("Low value");
        Label highLabel = new Label("High value");
        JFXTextField lowField = new JFXTextField(lowValue.toString());
        JFXTextField highField = new JFXTextField(highValue.toString());
        lowLabel.setPrefWidth(200);
        highLabel.setPrefWidth(200);
        GridPane grid = new GridPane();
        grid.add(lowLabel, 0, 0);
        grid.add(lowField, 1, 0);
        grid.add(highLabel, 0, 1);
        grid.add(highField, 1, 1);
        priceSubFilterContainer.getChildren().add(grid);
  
        slider.lowValueProperty().addListener((observable, oldValue, newValue) -> {
            lowField.setText(new Integer(newValue.intValue()).toString());
        });
        slider.highValueProperty().addListener((observable, oldValue, newValue) -> {
            highField.setText(new Integer(newValue.intValue()).toString());
        });
        slider.setOnMouseReleased((event) -> {
            makePricingQuery(new Integer(lowField.getText()), new Integer(highField.getText()));
        });
        lowField.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER){
                if(lowField.getText().isEmpty()) return;
                if(new Integer(lowField.getText()) < lowValue) lowField.setText(lowValue.toString());
                slider.setLowValue(Double.parseDouble(lowField.getText()));
                makePricingQuery(new Integer(lowField.getText()), new Integer(highField.getText()));
            }
        });
        lowField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()) return;
            if (!newValue.matches("\\d*")) {
                lowField.setText(oldValue);
            }
        });
        highField.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER){
                if(highField.getText().isEmpty()) return;
                if(new Integer(highField.getText()) > highValue) highField.setText(highValue.toString());
                slider.setHighValue(Double.parseDouble(highField.getText()));
                makePricingQuery(new Integer(lowField.getText()), new Integer(highField.getText()));
            }
        });
        highField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()) return;
            if (!newValue.matches("\\d*")) {
                highField.setText(oldValue);
            }
        });
    }
    
    private void makePricingQuery(Integer lowValue, Integer highValue){
        String con = "(PRICE - PRICE*OFFER/100) BETWEEN " + lowValue + " AND " + highValue;
        conditionLists.get("PRICING").clear();
        conditionLists.get("PRICING").add(con);
        createAndRunQuery();
    }
    
    private void makeRatingQuery(Double lowValue, Double highValue){
        String con = "GET_RATING(C.ID) BETWEEN " + lowValue + " AND " + highValue;
        conditionLists.get("RATING").clear();
        conditionLists.get("RATING").add(con);
        createAndRunQuery();
    }

    private void readyCategorySubFilters() {
        categorySubFilterContainer.getChildren().clear();
        categorySubFilterContainer.getStyleClass().add("subFilterContainer");
        ArrayList<Pair<Category, ArrayList<Category>>> categoryList = Category.getAllCategories();
        for (int i = 0; i < categoryList.size(); i++) {
            Category mainCat = categoryList.get(i).getKey();
//            CheckBox mainCB = new CheckBox(mainCat.getName());
            Label mainCB = new Label(mainCat.getName());
            categorySubFilterContainer.getChildren().add(mainCB);
            ArrayList<Category> list = categoryList.get(i).getValue();
            for (int j = 0; j < list.size(); j++) {
                Category subCat = list.get(j);
                CheckBox subCB = new CheckBox(subCat.getName());
                categorySubFilterContainer.getChildren().add(subCB);
                VBox.setMargin(subCB, new Insets(5, 0, 5, 15));
                subCB.setOnMouseClicked((event) -> {
                    String con = "CATEGORY_ID = " + subCat.getId();
                    if (subCB.isSelected()) {
                        conditionLists.get("CATEGORY").add(con);
                    } else {
                        if (conditionLists.get("CATEGORY").contains(con)) {
                            conditionLists.get("CATEGORY").remove(con);
                        }
                    }
                    createAndRunQuery();
                    HBox box = updateCurrentFilters(subCB);
                    box.setOnMouseClicked((event2) -> {
                        subCB.setSelected(false);
                        if (conditionLists.get("CATEGORY").contains(con)) {
                            conditionLists.get("CATEGORY").remove(con);
                        }
                        removeFilter(subCB);
                        createAndRunQuery();
                    });
                });
            }
//            mainCB.setOnMouseClicked((event) -> {
//                String con = "CATEGORY_ID = " + mainCat.getId();
//                if (mainCB.isSelected()) {
//                    conditionLists.get("CATEGORY").add(con);
//                    for(int j=0; j<subCBlist.size(); j++){
//                        if(!subCBlist.get(j).isSelected()){
//                            subCBlist.get(j).setSelected(true);
//                            con = "CATEGORY_ID = " + list.get(j).getId();
//                            conditionLists.get("CATEGORY").add(con);
//                        }
//                    }
//                } else {
//                    if (conditionLists.get("CATEGORY").contains(con)) {
//                        conditionLists.get("CATEGORY").remove(con);
//                    }
//                }
//                generateAndRunQuery();
//                updateCurrentFilters(mainCB);
//            });
        }
    }
    
    private HBox updateCurrentFilters(CheckBox cb) {
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
                filterContainer.getChildren().add(1, checkedFilterContainer);
            }
        } else {
            removeFilter(cb);
        }
        return box;
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
            conditionLists.get("OTHERS").add("INSTR(LOWER(C.TITLE), LOWER('" + text + "')) >= 1");
            createAndRunQuery();
//            Set<Course> set = Course.searchCourse(sql);
//            ArrayList<Course> list = new ArrayList(set);
//            if (courseBoxesCtrl == null) {
//                readyCourseBoxes(list);
//            } else {
//                courseBoxesCtrl.setUpPage(list, BoxViewType.GridView);
//            }
        }
    }

}
