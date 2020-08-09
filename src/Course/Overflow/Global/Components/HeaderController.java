/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Global.Components.Notification.NotificationView;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.ContainerPage;
import Course.Overflow.Global.ToolKit;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        leftArrow.setOpacity(0.1);
        rightArrow.setOpacity(0.1);
        leftArrow.setOnMouseClicked((event) -> {
            if(ContainerPage.pageIdx>0){
                ContainerPage.pageIdx--;
                ContainerPage.loadFromHistory();
            }
        });
        rightArrow.setOnMouseClicked((event) -> {
            if(ContainerPage.pageIdx < ContainerPage.pages.size()-1){
                ContainerPage.pageIdx++;
                ContainerPage.loadFromHistory();
            }
        });
        searchField.setOnKeyReleased((event) -> {
            if(event.getCode()==KeyCode.ENTER){
                GLOBAL.PAGE_CTRL.loadPage(ContainerPage.PageName.SearchResult);
            }
        });
        Platform.runLater(()->{
            createCategories();
        });
    }    
    
    
    public void setNotiPane(AnchorPane noti){
        this.noti = noti;
        noti.setVisible(false);
        setNotiPanePosition();
    }
    
    public void setProfilePane(AnchorPane profile){
        this.profilePane = profile;
        profilePane.setVisible(false);
        setProfilePanePosition();
    }
    
    private void setNotiPanePosition(){
        Platform.runLater(()->{
            noti.setLayoutY(header.getHeight());
            noti.setLayoutX(header.getWidth() - noti.getWidth());
            
            logo.setOnMouseClicked((event)->{
                GLOBAL.PAGE_CTRL.loadPage(ContainerPage.PageName.Home);
            });
        });
        notificationIcon.setOnMouseClicked((MouseEvent)->{
            FadeTransition t = new FadeTransition(Duration.millis(500), noti);
            if(noti.isVisible()){
                t.setFromValue(1);
                t.setToValue(0);
                t.play();
                t.setOnFinished((event)->{
                    noti.setVisible(false);
                });
            }
            else{
                if(profilePane.isVisible()) profilePane.setVisible(false);
                noti.setVisible(true);
                t.setFromValue(0);
                t.setToValue(1);
                t.play();
            }
        });
//        notificationIcon.setOnMouseExited((MouseEvent)->{
//            FadeTransition t = new FadeTransition(Duration.millis(500), noti);
//            t.setFromValue(1);
//            t.setToValue(0);
//            t.play();
//            t.setOnFinished((event) -> {
//                noti.setVisible(false);
//            });
//        });
    }

    private void setProfilePanePosition() {
        Platform.runLater(()->{
            profilePane.setLayoutY(header.getHeight());
            profilePane.setLayoutX(header.getWidth() - profilePane.getWidth());
        });
        profile.setOnMouseClicked((MouseEvent)->{
            FadeTransition t = new FadeTransition(Duration.millis(500), profilePane);
            if(profilePane.isVisible()){
                t.setFromValue(1);
                t.setToValue(0);
                t.play();
                t.setOnFinished((event)->{
                    profilePane.setVisible(false);
                });
            }
            else{
                if(noti.isVisible()) noti.setVisible(false);
                profilePane.setVisible(true);
                t.setFromValue(0);
                t.setToValue(1);
                t.play();
            }
        });
    }
    
    private void addSubCategory(VBox subCatContainer, int pos){
        Label label = new Label("Sub Category "+ pos);
//        Region r = new Region();
//        FontAwesomeIconView iv = new FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT);
//        HBox box = new HBox(label,r,iv);
        HBox box = new HBox(label);
//        HBox.setHgrow(r, Priority.ALWAYS);
        subCatContainer.getChildren().add(box);
        box.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(ContainerPage.PageName.SearchResult);
        });
    }
    
    private void addMainCategory(AnchorPane catRoot, VBox mainContainer, int pos){
        Label label = new Label("Main Category "+ pos);
        Region r = new Region();
        FontAwesomeIconView iv = new FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT);
        HBox box = new HBox(label,r,iv);
        HBox.setHgrow(r, Priority.ALWAYS);
        mainContainer.getChildren().add(box);
        
        VBox subCatContainer = new VBox();
        AnchorPane subCatRoot = new AnchorPane(subCatContainer);
        subCatRoot.getStylesheets().add(GLOBAL.COMPONENTS_LOCATION + "/Components.css");
        ToolKit.setAnchor(subCatContainer, 0, 0, 0, 0);
        int subCatNum = (int) ((Math.random()*100)%15);
        for(int i=1;i<=subCatNum;i++){
            addSubCategory(subCatContainer,i);
        }
        subCatContainer.getStyleClass().add("catContainer");
        GLOBAL.rootPane.getChildren().add(subCatRoot);
        subCatRoot.toBack();
        
        box.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(ContainerPage.PageName.SearchResult);
        });
        
        box.setOnMouseMoved((event) -> {
            subCatRoot.toFront();
            catRoot.toFront();
        });
        subCatRoot.setOnMouseMoved((event) -> {
            subCatRoot.toFront();
            catRoot.toFront();
        });
        box.setOnMouseExited((event) -> {
            subCatRoot.toBack();
            catRoot.toBack();
        });
        subCatRoot.setOnMouseExited((event) -> {
            subCatRoot.toBack();
            catRoot.toBack();
        });
        
        Platform.runLater(()->{
            subCatRoot.setLayoutX(categoriesBtn.getLayoutX() + ((HBox)mainContainer.getChildren().get(0)).getPrefWidth());
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
        catRoot.getStylesheets().add(GLOBAL.COMPONENTS_LOCATION + "/Components.css");
        ToolKit.setAnchor(catContainer, 0, 0, 0, 0);
        for(int i=0; i<10; i++){
            addMainCategory(catRoot, catContainer,i+1);
        }
        catContainer.getStyleClass().add("catContainer");
        GLOBAL.rootPane.getChildren().add(catRoot);
        catRoot.setLayoutX(categoriesBtn.getLayoutX());
        catRoot.setLayoutY(header.getPrefHeight());
        catRoot.toBack();
        categoriesBtn.setOnMouseMoved((event) -> {
            catRoot.toFront();
        });
        categoriesBtn.setOnMouseExited((event) -> {
            catRoot.toBack();
        });
        catRoot.setOnMouseMoved((event) -> {
            catRoot.toFront();
        });
        catRoot.setOnMouseExited((event) -> {
            catRoot.toBack();
        });
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
}
