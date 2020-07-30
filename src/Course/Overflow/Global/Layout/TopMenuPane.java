/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Layout;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class TopMenuPane extends AnchorPane{
    private double width;
    private double height;
    private double topHeight;
    
    private ArrayList<Label> buttonList;
    private ArrayList<AnchorPane> paneList;
    
    private HBox menuContainer;
    private AnchorPane runningPane;
    private AnchorPane centerPane;
    
    private Label selectedButton;
    private VBox topPane;
    private AnchorPane bottomPane;
    private ScrollPane scrollPane;
    private VBox wrapper;
    
    private boolean isAdded = false;

    /**
     * Initializes the controller class.
     */
    public TopMenuPane(){
        /**
         * Default value
         */
        width = 700;
        height = 400;
        topHeight = 60;
        
        
        paneList = new ArrayList<AnchorPane>();
        buttonList = new ArrayList<Label>();
        
        topPane = new VBox();
        runningPane = new AnchorPane();
        menuContainer = new HBox();
        bottomPane = new AnchorPane();
        scrollPane = new ScrollPane();
        centerPane = new AnchorPane();
        
        topPane.getChildren().addAll(menuContainer,new AnchorPane(runningPane));
        bottomPane.getChildren().addAll(scrollPane);
        scrollPane.setContent(centerPane);
//        
//        wrapper = new VBox(topPane,bottomPane);
        this.getChildren().addAll(topPane,bottomPane);
        
        this.setPrefSize(width, height);
        topPane.setPrefSize(AnchorPane.USE_COMPUTED_SIZE, topHeight);
        bottomPane.prefWidthProperty().bind(this.widthProperty());
        scrollPane.prefWidthProperty().bind(this.widthProperty());
        menuContainer.prefWidthProperty().bind(this.widthProperty());
        scrollPane.prefHeightProperty().bind(bottomPane.heightProperty());
        updateSize();
        
        runningPane.setStyle("" 
              +"-fx-pref-width: 80;" 
              +"-fx-pref-height: 8;" 
              +"-fx-background-color: orange;"
        );
        menuContainer.setStyle(""
              + "-fx-spacing: 40;"
              + "-fx-alignment: bottom-left;"
        );
        topPane.setStyle(""
              + "-fx-alignment: bottom-left;"
              + "-fx-padding: 0 0 10 10;"
        );
    }    
    
    private void updateSize(){
        AnchorPane.setTopAnchor(topPane, 0.0);
        AnchorPane.setBottomAnchor(bottomPane, 0.0);
        AnchorPane.setTopAnchor(bottomPane, topPane.getPrefHeight());
        
        AnchorPane.setRightAnchor(bottomPane, 0.0);
        AnchorPane.setLeftAnchor(bottomPane, 0.0);
        AnchorPane.setRightAnchor(topPane, 0.0);
        AnchorPane.setLeftAnchor(topPane, 0.0);
    }
    
    public void add(String labelText, AnchorPane pane) {
        paneList.add(pane);
        Label label = new Label(labelText);
        label.setStyle(""
              + "-fx-font: 22 \"Halvetica\";"
              + "-fx-cursor: hand;"
              + ""
        );
        buttonList.add(label);
        menuContainer.getChildren().add(label);
        makeEventHandler(label);
        if(!isAdded){
            select(label);
            isAdded = true;
        }
    }
    
    private void makeEventHandler(Label label) {
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                select(label);
            }
        });
    }
    
    public void select(Label label) {
        selectedButton = label;
        int idx = buttonList.indexOf(label);
        centerPane.getChildren().clear();
        centerPane.getChildren().add(paneList.get(idx));
        Platform.runLater(()->{
            new Timeline(new KeyFrame(Duration.millis(300), new KeyValue(runningPane.layoutXProperty(), selectedButton.getLayoutX()+selectedButton.getWidth()/2-runningPane.getWidth()/2))).play();
            new Timeline(new KeyFrame(Duration.millis(300), new KeyValue(runningPane.scaleXProperty(), selectedButton.getWidth()/runningPane.getWidth()))).play();
        });
        
    }
        
    public void setTopPaneHeight(double height){
        topPane.setPrefHeight(height);
        updateSize();
    }
    public void setTopPaneAlignment(Pos align){
        topPane.setAlignment(align);
    }
//    public ScrollPane getScrollPane(){
//        return scrollPane;
//    }
//    public VBox getTopPane(){
//        return topPane;
//    }
//    public AnchorPane getBottomPane(){
//        return bottomPane;
//    }
    public void setRunningPaneTopSpacing(double value){
        topPane.setSpacing(value);
    }
    public void centerPanePadding(Insets value){
        centerPane.setPadding(value);
    }
    public void setRunningPaneStyle(String css){
        runningPane.setStyle(runningPane.getStyle() + css);
    }
    public void setMenuButtonStyle(String css){
        for(Node node : menuContainer.getChildren()){
            node.setStyle(node.getStyle() + css);
        }
    }
    public void selectDefault(AnchorPane pane){
        int idx = 0;
        for(int i=0;i<paneList.size();i++){
            if(pane.equals(paneList.get(i))){
                idx = i;
                break;
            }
        }
        select(buttonList.get(idx));
    }
    public void selectDefault(String buttonName){
        int idx = 0;
        for(int i=0;i<buttonList.size();i++){
            if(buttonList.get(i).getText() == buttonName){
                idx = i;
                break;
            }
        }
        select(buttonList.get(idx));
    }
}