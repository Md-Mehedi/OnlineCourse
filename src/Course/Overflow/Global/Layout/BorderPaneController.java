/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Layout;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class BorderPaneController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane scrollPaneWrapper;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane centerPane;
    @FXML
    private AnchorPane footerPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()->{
            centerPane.setPrefWidth(GLOBAL.WIDTH - leftPane.getPrefWidth());
        });
    }

    @FXML
    private void mouseExited(MouseEvent event) {
    }

    @FXML
    private void mouseEntered(MouseEvent event) {
    }

    public void setCenter(AnchorPane centerPane) {
        this.centerPane.getChildren().add(centerPane);
        ToolKit.setAnchor(centerPane, 0, 0, 0, 0);
    }

    public void setHeader(AnchorPane headerPane) {
        this.headerPane.getChildren().add(headerPane);
        ToolKit.setAnchor(headerPane, 0, 0, 0, 0);
    }

    public void setFooter(AnchorPane footerPane) {
        this.footerPane.getChildren().add(footerPane);
        ToolKit.setAnchor(footerPane, 0, 0, 0, 0);
    }

    public void setLeft(AnchorPane leftPane) {
        this.leftPane.getChildren().add(leftPane);
        ToolKit.setAnchor(leftPane, 0, 0, 0, 0);
    }

    public void clearCenter() {
        this.centerPane.getChildren().clear();
    }

    public void clearHeader() {
        this.headerPane.getChildren().clear();
    }

    public void clearFooter() {
        this.footerPane.getChildren().clear();
    }

    public void clearLeft() {
        this.leftPane.getChildren().clear();
    }

    public void removeHeader() {
        Platform.runLater(() -> {
            clearHeader();
            leftPane.setPrefHeight(leftPane.getPrefHeight() + headerPane.getPrefHeight());
            scrollPaneWrapper.setPrefHeight(scrollPaneWrapper.getPrefHeight() + headerPane.getPrefHeight());
            AnchorPane.setTopAnchor(leftPane, 0.0);
            AnchorPane.setTopAnchor(scrollPaneWrapper, 0.0);
            AnchorPane.setTopAnchor(headerPane, -headerPane.getHeight());
        });
    }

    public void removeFooter() {
        Platform.runLater(() -> {
            clearFooter();
            leftPane.setPrefHeight(leftPane.getPrefHeight() + footerPane.getPrefHeight());
            scrollPaneWrapper.setPrefHeight(scrollPaneWrapper.getPrefHeight() + footerPane.getPrefHeight());
            AnchorPane.setBottomAnchor(leftPane, 0.0);
            AnchorPane.setBottomAnchor(scrollPaneWrapper, 0.0);
            AnchorPane.setBottomAnchor(footerPane, -footerPane.getHeight());
        });
    }

    public AnchorPane getRoot() {
        return root;
    }

    public AnchorPane getHeaderPane() {
        return headerPane;
    }

    public AnchorPane getLeftPane() {
        return leftPane;
    }

    public AnchorPane getScrollPaneWrapper() {
        return scrollPaneWrapper;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public AnchorPane getCenterPane() {
        return centerPane;
    }

    public AnchorPane getFooterPane() {
        return footerPane;
    }

    protected void setContainer(AnchorPane container) {
        this.root = container;
    }

    protected void setHeaderPane(AnchorPane headerPane) {
        this.headerPane = headerPane;
    }

    protected void setLeftPane(AnchorPane leftPane) {
        this.leftPane = leftPane;
    }

    protected void setScrollPaneWrapper(AnchorPane scrollPaneWrapper) {
        this.scrollPaneWrapper = scrollPaneWrapper;
    }

    protected void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    protected void setCenterPane(AnchorPane centerPane) {
        this.centerPane = centerPane;
    }

    protected void setFooterPane(AnchorPane footerPane) {
        this.footerPane = footerPane;
    }

    public void showFooter() {
        Platform.runLater(() -> {
            leftPane.setPrefHeight(leftPane.getPrefHeight() - footerPane.getPrefHeight());
            scrollPane.setPrefHeight(leftPane.getPrefHeight());
            scrollPane.setPrefHeight(leftPane.getPrefHeight());
            AnchorPane.setBottomAnchor(leftPane, footerPane.getPrefHeight());
            AnchorPane.setBottomAnchor(scrollPaneWrapper, footerPane.getPrefHeight());
            AnchorPane.setBottomAnchor(footerPane, 0.0);
        });
    }

    public void setFooterHeight(double value) {
        Platform.runLater(()->{
            footerPane.setPrefHeight(value);
            showFooter();
        });
    }
    public void showHeader() {
        Platform.runLater(() -> {
            leftPane.setPrefHeight(leftPane.getPrefHeight() - headerPane.getPrefHeight());
            scrollPane.setPrefHeight(leftPane.getPrefHeight());
            scrollPane.setPrefHeight(leftPane.getPrefHeight());
            AnchorPane.setTopAnchor(leftPane, headerPane.getPrefHeight());
            AnchorPane.setTopAnchor(scrollPaneWrapper, headerPane.getPrefHeight());
            AnchorPane.setTopAnchor(headerPane, 0.0);
        });
    }

    public void setHeaderHeight(double value) {
        Platform.runLater(()->{
            headerPane.setPrefHeight(value);
            showHeader();
        });
    }
    
    public void setCenterHeight(double value){
        Platform.runLater(()->{
            scrollPaneWrapper.setPrefHeight(value-4);
            root.setStyle("-fx-pref-height: " + scrollPaneWrapper.getPrefHeight() + ";");
        });
    }
    
    public double getHeight(){
        return scrollPaneWrapper.getPrefHeight();
    }
}
