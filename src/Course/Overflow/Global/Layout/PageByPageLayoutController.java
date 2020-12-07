/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Layout;

import Course.Overflow.Course.Course;
import Course.Overflow.Global.Components.CourseBoxController;
import Course.Overflow.Global.Components.CourseBoxHorizontalController;
import Course.Overflow.Global.Components.PersonSmallViewController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.PersonPreviewController;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Student.PurchaseHistory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PageByPageLayoutController implements Initializable {

    private FXMLLoader loader;
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
    private AnchorPane itemContainer;

    private VBox vItemContainer;
    private ArrayList<?> itemCtrl;
    private ArrayList<?> items;
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
    private ArrayList<Course> courses;
    private Map<Course, PurchaseHistory> mp;
    @FXML
    private VBox rootContainer;
    private boolean estimationDone;


    /**
     * Initializes the controller class.
     */
    /*
     * For make different type view - 1. add a value in BoxViewType with the fxml name 2. add
     * ArrayList<> initialization in setUpPage
     */
    public enum BoxViewType {
        ListView(GLOBAL.COMPONENTS_LOCATION + "/CourseBoxHorizontal.fxml"),
        GridView(GLOBAL.COMPONENTS_LOCATION + "/CourseBox.fxml"),
        PersonGrid(GLOBAL.COMPONENTS_LOCATION + "/PersonSmallView.fxml");
        public String fxmlName;

        BoxViewType(String s) {
            this.fxmlName = s;
        }
    }
    BoxViewType type;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentPage = 0;
        vItemContainer = new VBox();
        column = 4;
        offset = 20;
        estimationDone = false;

        // if the item of the list is changed 
        listener = new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                if (new_value.intValue() != -1) {
                    totalItem1page = (int) Math.ceil(Integer.parseInt(numOfItemList.get(new_value.intValue())));
                } else {
                    totalItem1page = Integer.parseInt(numOfItemList.get(1));
                }
                makePageNumbers();
                currentPage = 0;
                loadPage(1);
            }
        };
        numOfItemChoiceBox.getSelectionModel().selectedIndexProperty().addListener(listener);
    }

    public <T> void setUpPage(ArrayList<T> items, BoxViewType type, int column, int offset) {
        this.type = type;
        this.items = items;
        this.column = column;
        this.offset = offset;

        if (type == BoxViewType.GridView) {
            itemCtrl = new ArrayList<CourseBoxController>();
        } else if (type == BoxViewType.PersonGrid) {
            itemCtrl = new ArrayList<PersonSmallViewController>();
        } else if (type == BoxViewType.ListView) {
            itemCtrl = new ArrayList<CourseBoxHorizontalController>();
        }
        if(items == null) {
            removeHeader();
            ToolKit.showNoDataFound(itemContainer);
            return;
        }
        if(items.size() == 0) {
            removeHeader();
            ToolKit.showNoDataFound(itemContainer);
            return;
        }
        else{
            addHeader();
        }
        updateIconOpacity();

        itemContainer.getChildren().clear();
        if (type == BoxViewType.ListView) {
            itemContainer.getChildren().add(vItemContainer);
            ToolKit.setAnchor(vItemContainer, 0, 0, 0, 0);
        } else {
            itemContainer.getChildren().add(grid);
        }
        readyNumOfItemList();
        if(items.size()<=column){
//            removeHeader();
            removeFooter();
        }
        else{
//            addHeader();
            addFooter();
            makePageNumbers();
        }
        loadPage(1);
        estimateContainerWidth();
        listIcon.setOnMouseClicked((event) -> {
            if (listIcon.getOpacity() != 1) {
                currentPage = 0;
                setUpPage(items, BoxViewType.ListView);
            }
        });
        gridIcon.setOnMouseClicked((event) -> {
            if (gridIcon.getOpacity() != 1) {
                currentPage = 0;
                setUpPage(items, BoxViewType.GridView, column, offset);
            }
        });
        if (type == BoxViewType.PersonGrid) {
            stopViewChange();
        }
    }

    public <T> void setUpPage(ArrayList<T> items, BoxViewType type) {
        setUpPage(items, type, column, offset);
    }

    public <T> void setUpPage(ArrayList<T> items, BoxViewType type, int column) {
        setUpPage(items, type, column, 20);
    }

    public <T> void setUpPage(ArrayList<T> items){
        if(type == null) type = BoxViewType.GridView;
        setUpPage(items, type);
    }
    @SuppressWarnings("unchecked")
    private void readyNumOfItemList() {
        numOfItemList = FXCollections.observableArrayList();
        if (type == BoxViewType.ListView) {
            numOfItemList.addAll("5", "10", "15", "20", "25", "30");
        } else {
            numOfItemList.add(String.valueOf(column));
            for(int i=1; i<=6; i++){
                numOfItemList.add(String.valueOf(2*i*column));
            }
        }
        numOfItemChoiceBox.setItems(numOfItemList);
        numOfItemChoiceBox.setValue(numOfItemList.get(1));
        totalItem1page = Integer.parseInt(numOfItemList.get(1));
    }

    private void makePageNumbers() {
        pageNumContainer.getChildren().clear();
        totalPageNums = (int) Math.ceil(1.0 * items.size() / totalItem1page);
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
        if (pageNumContainer.getChildren().size() == 1) {
            pageNumContainer.setVisible(false);
        } else {
            pageNumContainer.setVisible(true);
        }
    }

    private void loadPage(int pageNum) {
        if (pageNum != currentPage) {

            // Applying CSS to selected page
            if (currentPage != 0) {
                pageNumContainer.getChildren().get(currentPage - 1).setId("");
            }
            currentPage = pageNum;
            if(currentPage > 0) pageNumContainer.getChildren().get(currentPage - 1).setId("selectedPage");

            // Set which items are showing
            if (totalItem1page * (pageNum - 1) + 1 != Math.min(totalItem1page * pageNum, items.size())) {
                itemsNumDetails.setText("Showing ("
                      + (totalItem1page * (pageNum - 1) + 1)
                      + " to "
                      + (Math.min(totalItem1page * pageNum, items.size()))
                      + ") of "
                      + items.size()
                      + " itmes");
            } else {
                itemsNumDetails.setText("Showing "
                      + (totalItem1page * (pageNum - 1) + 1)
                      + " of "
                      + (totalItem1page * (pageNum - 1) + 1)
                      + (totalItem1page * (pageNum - 1) + 1 == 1 ? " item" : " items"));
            }

            // Starting Filling with Course Box
            String fxmlName = type.fxmlName;
            if (type == BoxViewType.ListView) {
                vItemContainer.getChildren().clear();
            } else {
                grid.getChildren().clear();
                grid.setVgap(offset);
                grid.setHgap(offset);
            }

            for (int i = totalItem1page * (pageNum - 1); i < Math.min(totalItem1page * pageNum, items.size()); i++) {
                try {
                    loader = new FXMLLoader(getClass().getResource(fxmlName));
                    AnchorPane pane = loader.load();
                    if (type == BoxViewType.ListView) {
                        vItemContainer.getChildren().add(pane);
                        VBox.setVgrow(pane, Priority.ALWAYS);
                        CourseBoxHorizontalController ctrl = loader.getController();
                        itemCtrl.add(loader.getController());
                        ctrl.loadData((Course) items.get(i));
                        if(mp != null){
                            ctrl.addPriceAndPurchaseDateColumn(mp.get(items.get(i)));
                        }
                    } else {
                        grid.add(pane, (i - totalItem1page * (pageNum - 1)) % column, (i - totalItem1page * (pageNum - 1)) / column);
                        itemCtrl.add(loader.getController());
                        if(type == BoxViewType.GridView){
                            ((CourseBoxController) loader.getController()).loadData((Course) items.get(i));
                        }
                        else{
                            ((PersonSmallViewController) loader.getController()).loadData(((PurchaseHistory)items.get(i)).getStudent());
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PersonPreviewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (type != BoxViewType.ListView) {
                Platform.runLater(()->{
                    Double width = ((Pane)grid.getChildren().get(0)).getPrefWidth();
                    int n = grid.getChildren().size();
                    if (n < column) {
                        grid.setPrefWidth(n * width + (n - 1) * offset);
                    } else {
                        grid.setPrefWidth(column * 250 + (column - 1) * offset);
                    }
                });
            }
        }
    }

    public void stopViewChange() {
        topContainer.getChildren().remove(viewChangerContainer);
    }

    private void updateIconOpacity() {
        if (type == BoxViewType.GridView) {
            gridIcon.setOpacity(1);
            listIcon.setOpacity(0.2);
        } else if (type == BoxViewType.ListView) {
            gridIcon.setOpacity(0.2);
            listIcon.setOpacity(1);
        }
    }
    
    private void estimateContainerWidth() {
        if(!estimationDone){
            estimationDone = true;
            Platform.runLater(()->{
                Double width = 250.0;
                if (type == BoxViewType.GridView) {
                    width = column*width + (column-1)*offset;
                }
                else if(type == BoxViewType.PersonGrid){
                    width = column*270.0 + (column-1)*offset;
                }
                else{
                    width = 4*width + offset*3;
                }
                rootContainer.setPrefWidth(width);
                rootContainer.setMaxWidth(width);
                rootContainer.setMinWidth(width);
            });
        }
    }
    
    public void setPurchasyHistory(Map<Course, PurchaseHistory> mp){
        this.mp = mp;
    }

    public void attachedWithContainer() {
        if(container != null){
            ToolKit.setAnchor(container, 0, 0, 0, 0);
        }
    }
    
    public void removeHeader(){
        ToolKit.removeNode(topContainer);
    }
    
    private void addFooter() {
        if(rootContainer.getChildren().contains(pageNumContainer)) return;
        rootContainer.getChildren().add(pageNumContainer);
    }
    
    private void removeFooter() {
        ToolKit.removeNode(pageNumContainer);
    }

    private void addHeader() {
        if(!rootContainer.getChildren().contains(topContainer)){
            rootContainer.getChildren().add(0, topContainer);
        }
    }
}
