/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Layout;

import Course.Overflow.Global.Components.CourseBoxController;
import Course.Overflow.Global.Components.CourseBoxHorizontalController;
import Course.Overflow.Global.Components.PersonSmallViewController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.TeacherPreviewController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
    int total;
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
    
    private VBox vItemContainer;
    private ArrayList<CourseBoxHorizontalController> itemHCtrls;
    private ArrayList<?> itemBCtrl;
    @FXML
    private FontAwesomeIconView listIcon;
    @FXML
    private FontAwesomeIconView gridIcon;
    @FXML
    private HBox viewChangerContainer;
    @FXML
    private HBox topContainer;
    private ChangeListener listener;
    private String fxmlName;


    /**
     * Initializes the controller class.
     */
    /*
     * For make different type view - 
     * 1. add a value in BoxType with the fxml name
     * 2. add ArrayList<> initialization in setUpPage
     */
    public enum BoxType{
        CourseVertical(GLOBAL.COMPONENTS_LOCATION + "/CourseBoxHorizontal.fxml"),
        CourseGrid(GLOBAL.COMPONENTS_LOCATION + "/CourseBox.fxml"),
        PersonGrid(GLOBAL.COMPONENTS_LOCATION + "/PersonSmallView.fxml");
        public String fxmlName;
        BoxType(String s){
            this.fxmlName = s;
        }
    }
    BoxType type;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        total = 43;
        totalItem1page = 12;
        column = 4;
        currentPage = 0;
        offset = 45;
        
        itemHCtrls = new ArrayList<>();
        vItemContainer = new VBox();
        listener = new ChangeListener<Number>() {
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) {
                if(new_value.intValue() != -1)
                    totalItem1page = (int) Math.ceil(Integer.parseInt(numOfItemList.get(new_value.intValue())));
                else {
                    totalItem1page = Integer.parseInt(numOfItemList.get(1));
                }
                makePageNumbers();
                currentPage = 0;
                loadPage(1);
            }
        };
        numOfItemChoiceBox.getSelectionModel().selectedIndexProperty().addListener(listener);
    
        
        listIcon.setOnMouseClicked((event) -> {
            if(listIcon.getOpacity()!=1){
                currentPage = 0;
                setUpPage(BoxType.CourseVertical, total);
            }
        });
        gridIcon.setOnMouseClicked((event) -> {
            if(gridIcon.getOpacity()!=1){
                currentPage = 0;
                setUpPage(BoxType.CourseGrid, total, column, offset);
            }
        });
    }
    
    public void setUpPage(BoxType type, int totalCourse, int column, int offset){
        this.type = type;
        if(type == BoxType.CourseGrid) itemBCtrl = new ArrayList<CourseBoxController>();
        else if(type==BoxType.PersonGrid) itemBCtrl = new ArrayList<PersonSmallViewController>();
        
        gridIcon.setOpacity(1);
        listIcon.setOpacity(0.2);
        this.total = totalCourse;
        this.column = column;
        this.offset = offset;
        if(!gridContainer.getChildren().contains(grid)){
            gridContainer.getChildren().remove(vItemContainer);
            gridContainer.getChildren().add(grid);
        }
        readyNumOfItemList();
        makePageNumbers();
        loadPage(1);
        if(type == BoxType.PersonGrid) stopViewChange();
    }
    
    public void setUpPage(BoxType type, int totalCourse){
        this.type = type;
        gridIcon.setOpacity(0.1);
        listIcon.setOpacity(1);
        this.total = totalCourse;
        if(!gridContainer.getChildren().contains(vItemContainer)){
            gridContainer.getChildren().remove(grid);
            gridContainer.getChildren().add(vItemContainer);
            ToolKit.setAnchor(vItemContainer, 0, 0, 0, 0);
        }
        
        readyNumOfItemList();
        makePageNumbers();
        loadPage(1);
    }

    @SuppressWarnings("unchecked")
    private void readyNumOfItemList() {
        numOfItemList = FXCollections.observableArrayList();
        if(type == BoxType.CourseVertical){
            numOfItemList.addAll("5", "10", "15", "20", "25", "30");
        }
        else{
            numOfItemList.addAll("4", "6", "8", "12", "16", "20", "30", "40");
        }
        numOfItemChoiceBox.setItems(numOfItemList);
        numOfItemChoiceBox.setValue(numOfItemList.get(1));
        totalItem1page = Integer.parseInt(numOfItemList.get(1));
        
        

    }
    
    private void makePageNumbers() {
        pageNumContainer.getChildren().clear();
        totalPageNums = (int) Math.ceil(1.0 * total / totalItem1page);
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

            if(totalItem1page*(pageNum-1)+1 != Math.min(totalItem1page*pageNum, total)){
                itemsNumDetails.setText("Showing (" +
                      (totalItem1page * (pageNum - 1)+1) +
                      " to " +
                      (Math.min(totalItem1page * pageNum, total)) +
                      ") of " +
                      total +
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
            String fxmlName = type.fxmlName;
            if(type == BoxType.CourseVertical){
                vItemContainer.getChildren().clear();
            }
            else{
                grid.getChildren().clear();
                grid.setVgap(offset);
                grid.setHgap(offset);
            }

            for (int i = totalItem1page * (pageNum - 1); i < Math.min(totalItem1page * pageNum, total); i++) {
                try {
                    loader = new FXMLLoader(getClass().getResource(fxmlName));
                    AnchorPane pane = loader.load();
                    if(type == BoxType.CourseVertical){
                        vItemContainer.getChildren().add(pane);
                        itemHCtrls.add(loader.getController());
                    }
                    else{
                        grid.add(pane, (i - totalItem1page * (pageNum - 1)) % column, (i - totalItem1page * (pageNum - 1)) / column);
                        itemBCtrl.add(loader.getController());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TeacherPreviewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(type != BoxType.CourseVertical){
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
        stopViewChange();
        if(type == BoxType.CourseVertical){
            for(CourseBoxHorizontalController ctrl : itemHCtrls){
                ctrl.addPriceAndPurchaseDateColumn();
            }
        }
    }
    
    public void stopViewChange(){
        topContainer.getChildren().remove(viewChangerContainer);
    }
}
