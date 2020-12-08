/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Course.Category;
import Course.Overflow.Course.Course;
import Course.Overflow.Global.Components.Notification.NotificationView;
import Course.Overflow.Global.Customize.MyFadeTransition;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.PageByPageLayoutController;
import Course.Overflow.Global.Page.CourseListShowPage;
import Course.Overflow.Global.Page.PageController;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Page.SearchResultPage;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.ToolKit;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class HeaderController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label myCourse;
    @FXML
    private StackPane notificationIcon;
    @FXML
    private StackPane profile;
    @FXML
    private Circle profileCircle;
    @FXML
    private Label profileName;
    private NotificationView notificationCtrl;
    private AnchorPane notificationPane;
    @FXML
    private AnchorPane header;
    private AnchorPane profilePane;
    @FXML
    private Label leftArrow;
//    public static Label leftArrow;
    @FXML
    private Label rightArrow;
//    public static Label rightArrow;
    @FXML
    private Label categoriesBtn;
    private MyFadeTransition mainCatTransition;
    @FXML
    private Label notificationCount;
    private Integer notifcationCC;
    @FXML
    private ImageView themeToggle;
    private boolean isLight;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isLight = false;
        leftArrow.setOpacity(0.4);
        rightArrow.setOpacity(0.4);
        loadProfilePhoto();
        addListener();
        Platform.runLater(()->{
            createCategories();
        });
    }    
    
    public void addListener(){
        leftArrow.setOnMouseClicked((event) -> {
            if(PageController.pageIdx>0){
                PageController.pageIdx--;
                PageController.loadFromHistory();
            }
        });
        rightArrow.setOnMouseClicked((event) -> {
            if(PageController.pageIdx < PageController.pages.size()-1){
                PageController.pageIdx++;
                PageController.loadFromHistory();
            }
        });
        searchField.setOnKeyReleased((event) -> {
            if(event.getCode()==KeyCode.ENTER){
                if(searchField.getText().isEmpty()) return;
                GLOBAL.PAGE_CTRL.loadPage(PageName.SearchResult);
                SearchResultPage ctrl = (SearchResultPage) GLOBAL.PAGE_CTRL.getPage();
                ctrl.search(searchField.getText());
            }
        });
        themeToggle.setOnMouseClicked((event) -> {
            if(this.isLight){
                isLight = false;
                themeToggle.setImage(new Image(GLOBAL.ICON_LOCATION + "/half-moon.png"));
                if(GLOBAL.rootPane.getStylesheets().contains(GLOBAL.GLOBAL_LOCATION + "/LightTheme.css")){
                    GLOBAL.rootPane.getStylesheets().remove(GLOBAL.GLOBAL_LOCATION + "/LightTheme.css");
                }
                GLOBAL.rootPane.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/DarkTheme.css");
            }
            else{
                isLight = true;
                themeToggle.setImage(new Image(GLOBAL.ICON_LOCATION + "/sunrise.png"));
                if(GLOBAL.rootPane.getStylesheets().contains(GLOBAL.GLOBAL_LOCATION + "/DarkTheme.css")){
                    GLOBAL.rootPane.getStylesheets().remove(GLOBAL.GLOBAL_LOCATION + "/DarkTheme.css");
                }
                GLOBAL.rootPane.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/LightTheme.css");
            }
        });
        notificationIcon.setOnMouseClicked((event) -> {
            notificationCtrl.refresh();
        });
    }
    
    
    public void setNotificationCtrl(NotificationView ctrl){
        this.notificationCtrl = ctrl;
        this.notificationPane = ctrl.getContainer();
        setCount(ctrl.getUnseenCount());
        setNotificationPanePosition();
    }
    
    public void setCount(Integer count){
        this.notifcationCC = count;
        notificationCount.setText(count.toString());
    }
    
    public void setProfilePane(AnchorPane profile){
        this.profilePane = profile;
        setProfilePanePosition();
    }
    
    public void setNotificationPanePosition(){
        Platform.runLater(()->{
            notificationPane.setLayoutY(header.getHeight());
            notificationPane.setLayoutX(header.getWidth() - notificationPane.getWidth());
            
            logo.setOnMouseClicked((event)->{
                GLOBAL.PAGE_CTRL.loadPage(PageName.Home);
            });
        });
        new MyFadeTransition(notificationIcon, notificationPane);
    }

    private void setProfilePanePosition() {
        Platform.runLater(()->{
            profilePane.setLayoutY(header.getPrefHeight());
            profilePane.setLayoutX(header.getWidth() - profilePane.getWidth());
        });
        
        new MyFadeTransition(profile, profilePane);
    }
    
    private void addSubCategory(Category category, VBox subCatContainer){
        int pos = subCatContainer.getChildren().size() + 1;
        Label label = new Label(category.getName());
        HBox box = new HBox(label);
        subCatContainer.getChildren().add(box);
        box.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.CategorySearchPage);
            CourseListShowPage ctrl = (CourseListShowPage) GLOBAL.PAGE_CTRL.getPage();
            ctrl.loadData(Course.getList(category), PageByPageLayoutController.BoxViewType.GridView, 4);
        });
    }
    
    private void addMainCategory(Pair<Category, ArrayList<Category>> pair, AnchorPane catRoot, VBox mainContainer){
        int pos = mainContainer.getChildren().size() + 1;
        Label label = new Label(pair.getKey().getName());
        Region r = new Region();
        FontAwesomeIconView iv = new FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT);
        HBox box = new HBox(label,r,iv);
        HBox.setHgrow(r, Priority.ALWAYS);
        mainContainer.getChildren().add(box);
        box.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.CategorySearchPage);
            CourseListShowPage ctrl = (CourseListShowPage) GLOBAL.PAGE_CTRL.getPage();
            ctrl.loadData(Course.getList(pair.getKey()), PageByPageLayoutController.BoxViewType.GridView, 4);
        });
        
        VBox subCatContainer = new VBox();
        AnchorPane subCatRoot = new AnchorPane(subCatContainer);
        subCatRoot.getStylesheets().add(GLOBAL.COMPONENTS_LOCATION + "/Components.css");
        subCatRoot.getStyleClass().add("shadow");
        ToolKit.setAnchor(subCatContainer, 0, 0, 0, 0);
        int subCatNum = pair.getValue().size();
        if(subCatNum == 0) box.getChildren().remove(2);
        for(int i=0;i<subCatNum;i++){
            addSubCategory(pair.getValue().get(i), subCatContainer);
        }
        subCatContainer.getStyleClass().add("catContainer");
        GLOBAL.rootPane.getChildren().add(subCatRoot);
        
        MyFadeTransition ft = new MyFadeTransition(box, subCatRoot, Duration.millis(200), mainCatTransition.getHide());
        
        Platform.runLater(()->{
            subCatRoot.setLayoutX(categoriesBtn.getLayoutX() + 300);
            int mainCatNum = mainContainer.getChildren().size();
            double itemHeight = ((HBox)mainContainer.getChildren().get(0)).getPrefHeight();
            if(mainCatNum-pos+1< subCatNum && mainCatNum>subCatNum){
                subCatRoot.setLayoutY(header.getPrefHeight() + itemHeight*(mainCatNum-subCatNum));
            }
            else if(mainCatNum<=subCatNum){
                subCatRoot.setLayoutY(header.getPrefHeight());
            }
            else{
                subCatRoot.setLayoutY(header.getPrefHeight() + itemHeight*(pos-1));
            }
        });
    }
    
    public void createCategories(){
        VBox catContainer = new VBox();
        AnchorPane catRoot = new AnchorPane(catContainer);
        catRoot.getStyleClass().add("shadow");
        catRoot.setLayoutX(categoriesBtn.getLayoutX());
        catRoot.setLayoutY(header.getPrefHeight());
        catRoot.getStylesheets().add(GLOBAL.COMPONENTS_LOCATION + "/Components.css");
        mainCatTransition = new MyFadeTransition(categoriesBtn, catRoot, Duration.millis(200));
        ToolKit.setAnchor(catContainer, 0, 0, 0, 0);
        ArrayList<Pair<Category,ArrayList<Category>>> list = Category.getTreeList();
        for(int i=0; i<list.size(); i++){
            addMainCategory(list.get(i), catRoot, catContainer);
        }
        catContainer.getStyleClass().add("catContainer");
        GLOBAL.rootPane.getChildren().add(catRoot);
    }

    public Label getLeftArrow() {
        return leftArrow;
    }

    public Label getRightArrow() {
        return rightArrow;
    }
    
    public AnchorPane getRoot(){
        return header;
    }

    private void loadProfilePhoto() {
        Person person = ToolKit.getCurrentPerson();
        if(person.getPhoto() != null){
            if(profile.getChildren().contains(profileName)) profile.getChildren().remove(profileName);
            Image photo = ToolKit.makeImage(person.getPhoto());
            profileCircle.setFill(new ImagePattern(photo));
        }
        else {
            if(!profile.getChildren().contains(profileName)) profile.getChildren().add(profileName);
            profileName.setText(ToolKit.getCurrentPerson().getShortName());
        }
    }

    public void decreaseNotification() {
        setCount(notifcationCC-1);
    }
}
