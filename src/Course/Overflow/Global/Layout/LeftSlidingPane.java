/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Layout;

import Course.Overflow.Global.Customize.SVG;
import Course.Overflow.Global.Customize.SVGView;
import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.ToolKit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class LeftSlidingPane extends BorderPaneController{
    //Field from Super class
    private AnchorPane container;
    private AnchorPane headerPane;
    private AnchorPane leftPane;
    private AnchorPane scrollPaneWrapper;
    private ScrollPane scrollPane;
    private AnchorPane centerContainer;
    private AnchorPane centerPane;
    private AnchorPane footerPane;
    //Field from Super class

    
    
    private HBox centerHbox;
    private VBox mainVbox;

    private Duration animTime;
    private double leftMinWidth;
    private double leftMaxWidth;
    private VBox iconContainer;
    private VBox labelContainer;
    private Label titleLabel;
    
    private Map<Label, AnchorPane> iconToPane;
    private Map<AnchorPane, Label> paneToIcon;
    private Map<Label, AnchorPane> nameToPane;
    private Map<AnchorPane, Label> paneToName;
    private AnchorPane selectedPane;
    private AnchorPane runningPane;
    private final String FXML_PATH = GLOBAL.LAYOUT_LOCATION + "/BorderPane.fxml";
    private VBox centerBox;


    public enum Type {
        LEFT_HOVER_CENTER_DYNAMIC,
        LEFT_HOVER_CENTER_STATIC,
        NO_HOVER;        
    }
    Type type;
    
    /**
     *  To make a Left Sliding Pane, You have to - 
     *  1. Need the type of Sliding in constructor.
     *  2. removeHeader()
     *  3. removeFooter()
     *  4. removeTitleBar() if need to remove Title
     *  5. finally there need to add getRoot() as a children of main root
     *  6. Have to add page using addContent()
     *  7. To show a page at first, have to use setDefaultContent(AnchorPane)
     */
    public LeftSlidingPane(Type type) {
        loadParentField();
        addMoreNodes();
        animTime = new Duration(300);
        iconToPane = new HashMap<Label, AnchorPane>();
        paneToIcon = new HashMap<AnchorPane, Label>();
        nameToPane = new HashMap<Label, AnchorPane>();
        paneToName = new HashMap<AnchorPane, Label>();
        setType(type);
        addEventListenerForMenu();
        Platform.runLater(()->{
            setCenterHeight(
                  GLOBAL.HEIGHT 
                  - GLOBAL.HEADER.getRoot().getPrefHeight() 
                  - GLOBAL.TOP_MENU_BAR.getHeight()
            );
        });
    }

    private void loadParentField() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(LeftSlidingPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        BorderPaneController ctrl = loader.getController();
        container = ctrl.getRoot();
        headerPane = ctrl.getHeaderPane();
        leftPane = ctrl.getLeftPane();
        scrollPaneWrapper = ctrl.getScrollPaneWrapper();
        scrollPane = ctrl.getScrollPane();
        centerPane = ctrl.getCenterPane();
        footerPane = ctrl.getFooterPane();
        setContainer(container);
        setHeaderPane(headerPane);
        setLeftPane(leftPane);
        setScrollPaneWrapper(scrollPaneWrapper);
        setScrollPane(scrollPane);
        setCenterPane(centerPane);
        setFooterPane(footerPane);
    }
    
    private void addMoreNodes(){
        iconContainer = new VBox();
        labelContainer = new VBox();
        HBox menuContainer = new HBox(iconContainer,labelContainer);
        leftPane.getChildren().add(menuContainer);
        ToolKit.setAnchor(menuContainer, 0, 0, 0, 0);
        menuContainer.setId("menuContainer");
        iconContainer.setId("iconContainer");
        labelContainer.setId("labelContainer");
        iconContainer.getChildren().clear();
        labelContainer.getChildren().clear();
        runningPane = new AnchorPane();
        leftPane.getChildren().add(runningPane);
        runningPane.setId("runningPane");
        ToolKit.setAnchor(runningPane, ToolKit.NULL, ToolKit.NULL, ToolKit.NULL, 0);
        scrollPane.setPrefSize(scrollPaneWrapper.getWidth(), scrollPaneWrapper.getHeight());
        
        titleLabel = new Label();
        centerContainer = new AnchorPane();
        centerBox = new VBox(titleLabel,centerContainer);
        centerBox.setId("centerPageContainer");
        centerPane.getChildren().add(centerBox);
        ToolKit.setAnchor(centerBox, 0, 0, 0, 0);
    }
    
    public void removeTitleBar(){
        centerBox.getChildren().remove(titleLabel);
    }

    
    public void setType(Type type) {
        this.type = type;
        Platform.runLater(() -> {
            this.leftMinWidth = leftPane.getMinWidth();
            this.leftMaxWidth = leftPane.getMaxWidth();
            double Width = container.getWidth();
            double Height = container.getHeight();
            double CenterHeight = Height - headerPane.getHeight() - footerPane.getHeight();
            leftPane.setPrefHeight(CenterHeight);
            scrollPaneWrapper.setPrefHeight(CenterHeight);
            scrollPane.setPrefHeight(CenterHeight);
            if (type == Type.NO_HOVER || type == Type.LEFT_HOVER_CENTER_DYNAMIC) {
                leftPane.toBack();
                scrollPaneWrapper.setPrefWidth(Width - leftPane.getWidth());
                scrollPane.setPrefWidth(Width - leftPane.getWidth());
                Platform.runLater(()->{
                    AnchorPane.setLeftAnchor(scrollPaneWrapper, leftPane.getWidth());
                    //System.out.println(leftPane.getWidth());
                });
            } else if (type == Type.LEFT_HOVER_CENTER_STATIC) {
                leftPane.toFront();
                scrollPaneWrapper.setPrefWidth(Width - leftPane.getMinWidth());
                scrollPane.setPrefWidth(Width - leftPane.getMinWidth());
                AnchorPane.setLeftAnchor(scrollPaneWrapper, leftPane.getMinWidth());
            }
            if(type == Type.LEFT_HOVER_CENTER_DYNAMIC || type == Type.LEFT_HOVER_CENTER_STATIC)
                slideInLeftBox();
            AnchorPane.setTopAnchor(leftPane, headerPane.getHeight());
            AnchorPane.setBottomAnchor(leftPane, footerPane.getHeight());
            AnchorPane.setTopAnchor(scrollPaneWrapper, headerPane.getHeight());
            AnchorPane.setBottomAnchor(scrollPaneWrapper, footerPane.getHeight());
        });
    }

    private void slideInLeftBox() {
        Timeline t1 = new Timeline();
            KeyValue kv = new KeyValue(leftPane.prefWidthProperty(), leftMinWidth);
            KeyFrame kf = new KeyFrame(animTime, kv);
            t1.getKeyFrames().add(kf);
            t1.play();
            if (type == Type.LEFT_HOVER_CENTER_DYNAMIC) {
                new Timeline(new KeyFrame(animTime, new KeyValue(scrollPane.prefWidthProperty(), scrollPane.getPrefWidth() + leftMaxWidth - leftMinWidth))).play();
                new Timeline(new KeyFrame(animTime, new KeyValue(scrollPaneWrapper.prefWidthProperty(), scrollPaneWrapper.getPrefWidth() + leftMaxWidth - leftMinWidth))).play();
                new Timeline(new KeyFrame(animTime, new KeyValue(scrollPaneWrapper.translateXProperty(), - leftMaxWidth + leftMinWidth))).play();
            }
            FadeTransition transition = new FadeTransition(Duration.millis(50), labelContainer);
            transition.setFromValue(1);
            transition.setToValue(0);
            transition.play();
    }
    private void slideOutLeftBox() {
        Timeline t1 = new Timeline();
        KeyValue kv = new KeyValue(leftPane.prefWidthProperty(), leftMaxWidth);
        KeyFrame kf = new KeyFrame(animTime, kv);
        t1.getKeyFrames().add(kf);
        t1.play();
        if (type == Type.LEFT_HOVER_CENTER_DYNAMIC) {
            new Timeline(new KeyFrame(animTime, new KeyValue(
                  scrollPane.prefWidthProperty(), scrollPane.getPrefWidth() - leftMaxWidth + leftMinWidth))).play();
            new Timeline(new KeyFrame(animTime, new KeyValue(
                  scrollPaneWrapper.prefWidthProperty(), scrollPaneWrapper.getPrefWidth() - leftMaxWidth + leftMinWidth))).play();
            new Timeline(new KeyFrame(animTime, new KeyValue(
                  scrollPaneWrapper.translateXProperty(), 0))).play();
        }
        FadeTransition transition = new FadeTransition();
        transition.setNode(labelContainer);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.setDuration(Duration.millis(1000));
        transition.play();
    }
    
    private Label iconWrapper(Node icon){
        Label iconLabel = new Label();
        iconLabel.setGraphic(icon);
        return iconLabel;
    }
    
    public void addContent(AnchorPane pane, FontAwesomeIcon icon, PageName pageName){
        Label iconLabel = iconWrapper(new FontAwesomeIconView(icon));
        Label nameLabel = new Label(pageName.name);
        addContent(pane, iconLabel, nameLabel, pageName);
    }
    public void addContent(AnchorPane pane, SVG svgName, PageName pageName){
        Label iconLabel = iconWrapper(new SVGView(svgName));
        Label nameLabel = new Label(pageName.name);
        addContent(pane, iconLabel, nameLabel, pageName);
    }
    public void addContent(AnchorPane pane, MaterialIcon icon, PageName pageName){
        Label iconLabel = iconWrapper(new MaterialIconView(icon));
        Label nameLabel = new Label(pageName.name);
        addContent(pane, iconLabel, nameLabel, pageName);
    }
    public void addContent(AnchorPane pane, Image icon, PageName pageName){
        Label iconLabel = iconWrapper(new ImageView(icon));
        Label nameLabel = new Label(pageName.name);
        addContent(pane, iconLabel, nameLabel, pageName);
    }
    private void addContent(AnchorPane pane, Label iconLabel, Label paneName, PageName pageName){
        paneToIcon.put(pane, iconLabel);
        iconToPane.put(iconLabel, pane);
        paneToName.put(pane, paneName);
        nameToPane.put(paneName, pane);
        iconContainer.getChildren().add(iconLabel);
        labelContainer.getChildren().add(paneName);
        addEventListenerForVerticalMenuItem(iconLabel,pane, pageName);
        addEventListenerForVerticalMenuItem(paneName,pane, pageName);
        runningPane.prefHeightProperty().bind(paneName.heightProperty());
    }
    public void setDefaultContent(AnchorPane defaultPane) {
        labelClickTask(defaultPane);
    }
    private void addEventListenerForMenu(){
        leftPane.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(type == Type.LEFT_HOVER_CENTER_DYNAMIC || type == Type.LEFT_HOVER_CENTER_STATIC) slideOutLeftBox();
            }
        });
        leftPane.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(type == Type.LEFT_HOVER_CENTER_DYNAMIC || type == Type.LEFT_HOVER_CENTER_STATIC) slideInLeftBox();
            }
        });
    }
    private void labelClickTask(AnchorPane pane){
        ToolTip.invisibleAll();
        selectedPane = pane;
        centerContainer.getChildren().clear();
        centerContainer.getChildren().add(selectedPane);
        ToolKit.setAnchor(selectedPane, 0, 0, 0, 0);
        int idx = (iconContainer.getChildren().contains(paneToIcon.get(pane)) ? iconContainer.getChildren().indexOf(paneToIcon.get(pane)) : labelContainer.getChildren().indexOf(paneToName.get(pane)));
        Label selectedLabel = (Label) labelContainer.getChildren().get(idx);
        titleLabel.setText(selectedLabel.getText());
        Platform.runLater(()->{
            new Timeline(new KeyFrame(animTime, new KeyValue(runningPane.layoutYProperty(), iconContainer.getChildren().get(idx).getLayoutY()))).play();
        });
    }
    private void addEventListenerForVerticalMenuItem(Label btn, AnchorPane pane, PageName pageName){
        btn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                labelClickTask(pane);
                GLOBAL.PAGE_CTRL.curPage = pageName;
            }
        });
        btn.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int idx = (iconContainer.getChildren().contains(btn) ? iconContainer.getChildren().indexOf(btn) : labelContainer.getChildren().indexOf(btn));
                iconContainer.getChildren().get(idx).getStyleClass().add("leftMenuHovering");
                labelContainer.getChildren().get(idx).getStyleClass().add("leftMenuHovering");
            }
        });
        btn.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int idx = (iconContainer.getChildren().contains(btn) ? iconContainer.getChildren().indexOf(btn) : labelContainer.getChildren().indexOf(btn));
                iconContainer.getChildren().get(idx).getStyleClass().remove("leftMenuHovering");
                labelContainer.getChildren().get(idx).getStyleClass().remove("leftMenuHovering");
            }
        });
    }
    
    public void setPage(AnchorPane pane){
        labelClickTask(pane);
    }
}
