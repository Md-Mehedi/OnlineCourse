/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Layout;

import Course.Overflow.Global.Components.CourseBoxController;
import Course.Overflow.Global.Components.CourseBoxHorizontalController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.TeacherPreviewController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PageByPageLayoutController implements Initializable {

    private FXMLLoader loader;
    int totalCourse;
    int column;
    int totalItem1page;
    int totalPageNums;

    int currentPage;
    int offset;
    ArrayList<GridPane> grids;
    ArrayList<Label> pageNumbers;

    @FXML
    private GridPane grid;
    @FXML
    private HBox pageNumContainer;
    @FXML
    private ComboBox<String> numOfItemChoiceBox;
    private ObservableList<String> numOfItemList;
    @FXML
    private AnchorPane container;
    @FXML
    private Label itemsNumDetails;
    @FXML
    private AnchorPane gridContainer;
    
    private VBox vCourseContainer;
    private ArrayList<CourseBoxHorizontalController> courseHCtrls;
    private ArrayList<CourseBoxController> courseBCtrls;


    /**
     * Initializes the controller class.
     */
    
    public enum CourseBoxShowType{
        Vertical("Vertical"),
        Grid("Grid");
        private String name;
        CourseBoxShowType(String s){
            this.name = s;
        }
    }
    CourseBoxShowType type;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalCourse = 43;
        totalItem1page = 12;
        column = 4;
        currentPage = 0;
        offset = 45;
        courseBCtrls = new ArrayList<>();
        courseHCtrls = new ArrayList<>();
    }
    
    public void setUpPage(int totalCourse, int column, int offset){
        type = CourseBoxShowType.Grid;
        this.totalCourse = totalCourse;
        this.column = column;
        this.offset = offset;
        if(!gridContainer.getChildren().contains(grid)){
            gridContainer.getChildren().add(grid);
            ToolKit.setAnchor(grid, 0, ToolKit.NULL, 0, 0);
        }
        container.setPrefWidth(column*250 + (column-1)*offset);
        readyNumOfItemList();
        makePageNumbers();
        loadPage(1);
    }
    
    public void setUpPage(int totalCourse){
        type = CourseBoxShowType.Vertical;
        this.totalCourse = totalCourse;
        gridContainer.getChildren().remove(grid);
        vCourseContainer = new VBox();
        gridContainer.getChildren().add(vCourseContainer);
        ToolKit.setAnchor(vCourseContainer, 0, 0, 0, 0);
        
        readyNumOfItemList();
        makePageNumbers();
        loadPage(1);
    }

    @SuppressWarnings("unchecked")
    private void readyNumOfItemList() {
        numOfItemList = FXCollections.observableArrayList();
        if(type == CourseBoxShowType.Grid){
            numOfItemList.addAll("8", "12", "16", "20", "30", "40");
        }
        else{
            numOfItemList.addAll("5", "10", "15", "20", "25", "30");
        }
        numOfItemChoiceBox.setItems(numOfItemList);
        numOfItemChoiceBox.setItems(numOfItemList);
        numOfItemChoiceBox.setValue(numOfItemList.get(1));
        totalItem1page = Integer.parseInt(numOfItemList.get(1));

        numOfItemChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) {
                totalItem1page = (int) Math.ceil(Integer.parseInt(numOfItemList.get(new_value.intValue())));
                makePageNumbers();
                currentPage = 0;
                loadPage(1);
            }
        });
    }
    
    private void makePageNumbers() {
        pageNumContainer.getChildren().clear();
        totalPageNums = (int) Math.ceil(1.0 * totalCourse / totalItem1page);
        for (int i = 1; i <= totalPageNums; i++) {
            Label label = new Label(i + "");
            pageNumContainer.getChildren().add(label);

            label.setOnMouseEntered(event -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(100), label);
                st.setToX(1.2);
                st.setToY(1.2);
                st.play();
            });
            label.setOnMouseExited(event -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(100), label);
                st.setToX(1);
                st.setToY(1);
                st.play();
            });
            label.setOnMouseClicked((event) -> {
                int idx = pageNumContainer.getChildren().indexOf(label) + 1;
                if (currentPage != idx) {
                    loadPage(idx);
                }
            });
        }
        if(pageNumContainer.getChildren().size()==1) pageNumContainer.setVisible(false);
        else pageNumContainer.setVisible(true);
    }

    private void loadPage(int pageNum) {
        if (pageNum != currentPage) {
            if (currentPage != 0) {
                pageNumContainer.getChildren().get(currentPage - 1).setId("");
            }
            currentPage = pageNum;
            pageNumContainer.getChildren().get(currentPage - 1).setId("selectedPage");

            if(totalItem1page*(pageNum-1)+1 != Math.min(totalItem1page*pageNum, totalCourse)){
                itemsNumDetails.setText("Showing (" +
                      (totalItem1page * (pageNum - 1)+1) +
                      " to " +
                      (Math.min(totalItem1page * pageNum, totalCourse)) +
                      ") of " +
                      totalCourse +
                      " itmes");
            } 
            else{
                itemsNumDetails.setText("Showing " 
                      + (totalItem1page*(pageNum-1)+1) 
                      + " of " 
                      + (totalItem1page*(pageNum-1)+1) 
                      + (totalItem1page*(pageNum-1)+1==1 ? " item" : " items"));
            }
            
            // Starting Filling with Course Box
            String fxmlName = GLOBAL.COMPONENTS_LOCATION + "/CourseBoxHorizontal.fxml";
            if(type == CourseBoxShowType.Grid){
                grid.getChildren().clear();
                grid.setVgap(offset);
                grid.setHgap(offset);
                fxmlName = GLOBAL.COMPONENTS_LOCATION + "/CourseBox.fxml";
            }
            else{
                vCourseContainer.getChildren().clear();
            }

            for (int i = totalItem1page * (pageNum - 1); i < Math.min(totalItem1page * pageNum, totalCourse); i++) {
                try {
                    loader = new FXMLLoader(getClass().getResource(fxmlName));
                    AnchorPane pane = loader.load();
                    if(type == CourseBoxShowType.Grid){
                        grid.add(pane, (i - totalItem1page * (pageNum - 1)) % column, (i - totalItem1page * (pageNum - 1)) / column);
                        courseBCtrls.add(loader.getController());
                    }
                    else{
                        vCourseContainer.getChildren().add(pane);
                        courseHCtrls.add(loader.getController());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TeacherPreviewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(type == CourseBoxShowType.Grid){
                int n = grid.getChildren().size();
                if(n<column){
                    grid.setPrefWidth(n*250 + (n-1)*offset);
                }
                else{
                    grid.setPrefWidth(column*250 + (column-1)*offset);
                }
            }
        }
    }
    
    public void addPurchaseDateColumn() {
        if(type == CourseBoxShowType.Vertical){
            for(CourseBoxHorizontalController ctrl : courseHCtrls){
                ctrl.addPriceAndPurchaseDateColumn();
            }
        }
    }
}
