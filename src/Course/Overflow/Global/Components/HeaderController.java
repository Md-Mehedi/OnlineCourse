/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Global.Components.Notification.NotificationView;
import Course.Overflow.Global.Customize.MyFadeTransition;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageController;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.ToolKit;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.net.URL;
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
    private Label notificationNum;
    @FXML
    private StackPane profile;
    @FXML
    private Circle profileCircle;
    @FXML
    private Label profileName;
    private NotificationView notiCtrl;
    private AnchorPane noti;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        leftArrow.setOpacity(0.1);
        rightArrow.setOpacity(0.1);
        loadProfilePhoto();
        addListener();
        Platform.runLater(()->{
            //createCategories();
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
                //GLOBAL.PAGE_CTRL.loadPage(PageName.SearchResult);
            }
        });
    }
    
    
    public void setNotiPane(AnchorPane noti){
        this.noti = noti;
        setNotiPanePosition();
    }
    
    public void setProfilePane(AnchorPane profile){
        this.profilePane = profile;
        setProfilePanePosition();
    }
    
    public void setNotiPanePosition(){
        Platform.runLater(()->{
            noti.setLayoutY(header.getHeight());
            noti.setLayoutX(header.getWidth() - noti.getWidth());
            
            logo.setOnMouseClicked((event)->{
                GLOBAL.PAGE_CTRL.loadPage(PageName.Home);
            });
        });
        new MyFadeTransition(notificationIcon, noti);
    }

    private void setProfilePanePosition() {
        Platform.runLater(()->{
            profilePane.setLayoutY(header.getHeight());
            profilePane.setLayoutX(header.getWidth() - profilePane.getWidth());
        });
        
        new MyFadeTransition(profile, profilePane);
    }
    
    private void addSubCategory(VBox subCatContainer){
        int pos = subCatContainer.getChildren().size() + 1;
        Label label = new Label("Sub Category "+ pos);
        HBox box = new HBox(label);
        subCatContainer.getChildren().add(box);
        box.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.SearchResult);
        });
    }
    
    private void addMainCategory(AnchorPane catRoot, VBox mainContainer){
        int pos = mainContainer.getChildren().size() + 1;
        Label label = new Label("Main Category "+ pos);
        Region r = new Region();
        FontAwesomeIconView iv = new FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT);
        HBox box = new HBox(label,r,iv);
        HBox.setHgrow(r, Priority.ALWAYS);
        mainContainer.getChildren().add(box);
        box.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.SearchResult);
        });
        
        VBox subCatContainer = new VBox();
        AnchorPane subCatRoot = new AnchorPane(subCatContainer);
        subCatRoot.getStylesheets().add(GLOBAL.COMPONENTS_LOCATION + "/Components.css");
        ToolKit.setAnchor(subCatContainer, 0, 0, 0, 0);
        int subCatNum = (int) ((Math.random()*100)%15);
        if(subCatNum == 0) box.getChildren().remove(2);
        for(int i=1;i<=subCatNum;i++){
            addSubCategory(subCatContainer);
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
        catRoot.setLayoutX(categoriesBtn.getLayoutX());
        catRoot.setLayoutY(header.getPrefHeight());
        catRoot.getStylesheets().add(GLOBAL.COMPONENTS_LOCATION + "/Components.css");
        mainCatTransition = new MyFadeTransition(categoriesBtn, catRoot, Duration.millis(200));
        ToolKit.setAnchor(catContainer, 0, 0, 0, 0);
        for(int i=0; i<10; i++){
            addMainCategory(catRoot, catContainer);
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
        System.out.println(person);
        if(person.getPhoto() != null){
            System.out.println(person.getAccountType());
            System.out.println(person.getPhoto().getContent());
            if(profile.getChildren().contains(profileName)) profile.getChildren().remove(profileName);
            File photoFile = new File(ToolKit.makeAbsoluteLocation(person.getPhoto().getContent()));
            Image photo = new Image(photoFile.toURI().toString());
            profileCircle.setFill(new ImagePattern(photo));
        }
        else {
            if(!profile.getChildren().contains(profileName)) profile.getChildren().add(profileName);
            profileName.setText(ToolKit.userShortName());
        }
    }
}
