/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 *
 * @author Md Mehedi Hasan
 */
public class ToolKit {

    public final static double NULL = -99999999.99;

    public static void moveRow(VBox parent, int idx, int i) {
        if (idx + i < 0 || idx + i == parent.getChildren().size()) {
            return;
        }
        AnchorPane curBox = (AnchorPane) parent.getChildren().get(idx);
        AnchorPane toBox = (AnchorPane) parent.getChildren().get(idx + i);
        parent.getChildren().remove(i == -1 ? curBox : toBox);
        parent.getChildren().add(idx + (i == -1 ? i : 0), i == -1 ? curBox : toBox);
    }

    public static void makeDynamicTextArea(TextArea ta) {
        ta.textProperty().addListener((ov, prevText, currText) -> {
            // Do this in a Platform.runLater because of Textfield has no padding at first time and so on
            Platform.runLater(() -> {
                Text text = new Text(currText);
                text.setFont(ta.getFont()); // Set the same font, so the size is the same
                double height = text.getLayoutBounds().getHeight()// This big is the Text in the TextField
                      + ta.getPadding().getTop() + ta.getPadding().getBottom()// Add the padding of the TextField
                      + 200d; // Add some spacing
                ta.setPrefHeight(height); // Set the width
                ta.positionCaret(ta.getCaretPosition()); // If you remove this line, it flashes a little bit
            });
        });
    }

    public static void openLink(String link) {
        HostServices hostServices = (HostServices) GLOBAL.stage.getProperties().get("hostServices");
        hostServices.showDocument(link);
    }

    public static void openPDF(File file) {
        HostServices hostServices = (HostServices) GLOBAL.stage.getProperties().get("hostServices");
        hostServices.showDocument(file.getAbsolutePath());
    }

    public static File chooseFile(String fileType) {
        FileChooser fc = new FileChooser();
        if (fileType == "video") {
            fc.getExtensionFilters().addAll(
                  new FileChooser.ExtensionFilter("Video files", "*.mp4", "*.3gp")
            );
        } else if (fileType == "pdf") {
            fc.getExtensionFilters().add(
                  new FileChooser.ExtensionFilter("PDF files", "*.pdf")
            );
        } else if (fileType == "image") {
            fc.getExtensionFilters().addAll(
                  new FileChooser.ExtensionFilter("Image files", "*.jpeg", "*.jpg", "*.bmp", "*.png", "*.gif")
            );
        }
        return fc.showOpenDialog(null);
    }

    public static MediaPlayer getMediaPlayer(File file) {
        return new MediaPlayer(new Media(file.toURI().toString()));
    }

    private static int tooltipOffset = 10;
    private static double triangleBase = 12;
    private static double triangleHeight = Math.sqrt(3) / 2 * triangleBase;

    private static AnchorPane makeLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("toolTipLabel");
        AnchorPane wrapper = new AnchorPane(label);
        return wrapper;
    }

    private static Polygon toolTipFinalize(AnchorPane content, AnchorPane contentContainer, AnchorPane mainContainer) {
        contentContainer.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        contentContainer.getStyleClass().add("toolTipContainer");
        contentContainer.setDisable(true);
        contentContainer.setStyle(""
              + "-fx-stroke: black;"
        );
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
              0.0, 0.0,
              triangleBase, 0.0,
              triangleBase / 2, triangleHeight
        );
        triangle.setStyle(""
              + "-fx-fill: #ccffff;"
        );
        AnchorPane triangleWrapper = new AnchorPane(triangle);
        VBox box = new VBox(content, triangleWrapper);
        contentContainer.getChildren().add(box);
        mainContainer.getChildren().add(contentContainer);

        InnerShadow inShadow = new InnerShadow();
        inShadow.setRadius(2);
        DropShadow effect = new DropShadow();
        effect.setWidth(0);
        effect.setHeight(0);
        effect.setOffsetX(0);
        effect.setOffsetY(0);
        effect.setRadius(tooltipOffset);
        effect.setSpread(0.0001);
        effect.setInput(inShadow);
        contentContainer.setEffect(effect);
        contentContainer.setVisible(false);
        contentContainer.setDisable(true);
        return triangle;
    }
    
    public static void setUpLocation(FontAwesomeIconView obj, AnchorPane contentContainer, Polygon triangle){
        contentContainer.toFront();
        double posX = obj.localToScene(obj.getBoundsInLocal()).getMinX() - contentContainer.getWidth() / 2 + Double.parseDouble(obj.getSize()) / 2;
        double posY = obj.localToScene(obj.getBoundsInLocal()).getMinY() - contentContainer.getHeight() - tooltipOffset;
        setTriangleProperly(posY, triangle);
        triangle.setLayoutX(posX < tooltipOffset ? obj.localToScene(obj.getBoundsInLocal()).getMinX() + Double.parseDouble(obj.getSize()) / 2 - triangleBase / 2 - tooltipOffset : contentContainer.getWidth() / 2 - triangleBase / 2);
        contentContainer.setLayoutX(posX < tooltipOffset ? tooltipOffset : posX);
        contentContainer.setLayoutY(posY < tooltipOffset ? obj.localToScene(obj.getBoundsInLocal()).getMaxY() + tooltipOffset : posY);
    }

    private static void setHandler(FontAwesomeIconView obj, AnchorPane contentContainer, Polygon triangle) {
        obj.setOnScroll((event) -> {
            setUpLocation(obj, contentContainer, triangle);
        });
        obj.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpLocation(obj, contentContainer, triangle);
                contentContainer.setVisible(true);
                contentContainer.setDisable(false);
            }
        });
        obj.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                contentContainer.setVisible(false);
                contentContainer.setDisable(true);
            }
        });
    }

    public static void setTooltip(FontAwesomeIconView obj, String text, AnchorPane mainContainer) {
        AnchorPane contentContainer = new AnchorPane();
        AnchorPane content = makeLabel(text);
        Polygon triangle = toolTipFinalize(content, contentContainer, mainContainer);
        obj.toFront();
        setHandler(obj, contentContainer, triangle);
    }

    public static void setTooltip(FontAwesomeIconView obj, AnchorPane content, AnchorPane mainContainer) {
        AnchorPane contentContainer = new AnchorPane();
        Polygon triangle = toolTipFinalize(content, contentContainer, mainContainer);
        setHandler(obj, contentContainer, triangle);
    }
    public static void setUpLocation(ImageView obj, AnchorPane contentContainer, Polygon triangle){
        contentContainer.toFront();
        double posX = obj.localToScene(obj.getBoundsInLocal()).getMinX() - contentContainer.getWidth() / 2 + obj.getFitWidth() / 2;
        double posY = obj.localToScene(obj.getBoundsInLocal()).getMinY() - contentContainer.getHeight() - tooltipOffset;
        setTriangleProperly(posY, triangle);
        triangle.setLayoutX(posX < tooltipOffset ? obj.localToScene(obj.getBoundsInLocal()).getMinX() + obj.getFitWidth() / 2 - triangleBase / 2 - tooltipOffset : contentContainer.getWidth() / 2 - triangleBase / 2);
        contentContainer.setLayoutX(posX < tooltipOffset ? tooltipOffset : posX);
        contentContainer.setLayoutY(posY < tooltipOffset ? obj.localToScene(obj.getBoundsInLocal()).getMaxY() + tooltipOffset : posY);
    }

    private static void setHandler(ImageView obj, AnchorPane contentContainer, Polygon triangle) {
        obj.setOnScroll((event) -> {
            setUpLocation(obj, contentContainer, triangle);
        });
        obj.setOnMouseEntered((MouseEvent event) -> {
            setUpLocation(obj, contentContainer, triangle);
            contentContainer.setVisible(true);
            contentContainer.setDisable(false);
        });
        obj.setOnMouseExited((MouseEvent event) -> {
            contentContainer.setVisible(false);
            contentContainer.setDisable(true);
        });
    }

    public static void setTooltip(ImageView obj, String text, AnchorPane mainContainer) {
        AnchorPane contentContainer = new AnchorPane();
        AnchorPane content = makeLabel(text);
        Polygon triangle = toolTipFinalize(content, contentContainer, mainContainer);
        obj.toFront();
        setHandler(obj, contentContainer, triangle);
    }

    public static void setTooltip(ImageView obj, AnchorPane content, AnchorPane mainContainer) {
        AnchorPane contentContainer = new AnchorPane();
        Polygon triangle = toolTipFinalize(content, contentContainer, mainContainer);
        setHandler(obj, contentContainer, triangle);
    }
    
    public static void setUpLocation(Label obj, AnchorPane contentContainer, Polygon triangle){
        contentContainer.toFront();
        double posX = obj.localToScene(obj.getBoundsInLocal()).getMinX() - contentContainer.getWidth() / 2 + obj.getWidth() / 2;
        double posY = obj.localToScene(obj.getBoundsInLocal()).getMinY() - contentContainer.getHeight() - tooltipOffset;
        setTriangleProperly(posY, triangle);
        triangle.setLayoutX(posX < tooltipOffset ? obj.localToScene(obj.getBoundsInLocal()).getMinX() + obj.getWidth() / 2 - triangleBase / 2 - tooltipOffset : contentContainer.getWidth() / 2 - triangleBase / 2);
        contentContainer.setLayoutX(posX < tooltipOffset ? tooltipOffset : posX);
        contentContainer.setLayoutY(posY < tooltipOffset ? obj.localToScene(obj.getBoundsInLocal()).getMaxY() + tooltipOffset : posY);
    }

    private static void setHandler(Label obj, AnchorPane contentContainer, Polygon triangle) {
        obj.setOnScroll((event) -> {
            setUpLocation(obj, contentContainer, triangle);
        });
        obj.setOnMouseEntered((MouseEvent event) -> {
            setUpLocation(obj, contentContainer, triangle);        
            contentContainer.setVisible(true);
            contentContainer.setDisable(false);
        });
        obj.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                contentContainer.setVisible(false);
                contentContainer.setDisable(true);
            }
        });
    }

    public static void setTooltip(Label obj, String text, AnchorPane mainContainer) {
        AnchorPane contentContainer = new AnchorPane();
        AnchorPane content = makeLabel(text);
        Polygon triangle = toolTipFinalize(content, contentContainer, mainContainer);
        obj.toFront();
        setHandler(obj, contentContainer, triangle);
    }

    public static void setTooltip(Label obj, AnchorPane content, AnchorPane mainContainer) {
        AnchorPane contentContainer = new AnchorPane();
        Polygon triangle = toolTipFinalize(content, contentContainer, mainContainer);
        setHandler(obj, contentContainer, triangle);
    }
    
    private static void setTriangleProperly(double posY, Polygon triangle) {
        AnchorPane triangleWrapper = (AnchorPane) triangle.getParent();
        VBox box = (VBox) triangleWrapper.getParent();
        if(posY<tooltipOffset){
            if(box.getChildren().indexOf(triangleWrapper)==1){
                box.getChildren().remove(triangleWrapper);
                box.getChildren().add(0, triangleWrapper);
                triangle.getPoints().clear();
                triangle.getPoints().addAll(
                      0.0, 0.0,
                      triangleBase, 0.0,
                      triangleBase / 2, -triangleHeight
                );
            }
        }
        else{
            if(box.getChildren().indexOf(triangleWrapper)==0){
                box.getChildren().remove(triangleWrapper);
                box.getChildren().add(triangleWrapper);
                triangle.getPoints().clear();
                triangle.getPoints().addAll(
                      0.0, 0.0,
                      triangleBase, 0.0,
                      triangleBase / 2, triangleHeight
                );
            }
        }
    }

    public static void setAnchor(Node node, double topAnchor, double rightAnchor, double bottomAnchor, double leftAnchor) {
        if (topAnchor != NULL) {
            AnchorPane.setTopAnchor(node, topAnchor);
        }
        if (topAnchor != NULL) {
            AnchorPane.setRightAnchor(node, rightAnchor);
        }
        if (topAnchor != NULL) {
            AnchorPane.setBottomAnchor(node, bottomAnchor);
        }
        if (topAnchor != NULL) {
            AnchorPane.setLeftAnchor(node, leftAnchor);
        }
    }
    
    public static String getCurTime(){
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        //System.out.println(df.format(dateobj));
        
        return "TO_DATE('" + df.format(dateobj) + "', 'dd/MM/yy hh24:mi:ss')";
    }
    
}
