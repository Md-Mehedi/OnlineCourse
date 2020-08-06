/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Customize;

import Course.Overflow.Global.GLOBAL;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Md Mehedi Hasan
 */
public class ToolTip {

    private EventType<MouseEvent> event;
    private AnchorPane rootContainer;
    private AnchorPane tipPane;
    private Node baseNode;
    private double offsetY = 74567;
    private double offsetX = 74567;
    private double paramOffsetY;
    private double paramOffsetX;

    private double baseNodeWidth;
    private double minOffset = 10;
    private static int tooltipOffset = 10;
    private double triangleBase = 12;
    private double triangleHeight = Math.sqrt(3) / 2 * triangleBase;
    private boolean isCustom;
    private Polygon triangle;
    private Label tipLabel;

    private static ArrayList<AnchorPane> toolPaneList = new ArrayList<>();
    private boolean bottom = false;
    private boolean top = true;
    private DropShadow effect;
    private double trianleLayoutX;

    private void build() {
        Platform.runLater(() -> {
                rootContainer = (AnchorPane) GLOBAL.stage.getScene().getRoot();
                rootContainer.getChildren().add(tipPane);
                //tipPane.toFront();
                addHandler();
                //updatePosition();
                tipPane.setVisible(false);
                toolPaneList.add(tipPane);
                setDefaultOffsetY();
                setDefaultOffsetX();

        });
    }

    public ToolTip(EventType<MouseEvent> event, Node baseNode, AnchorPane tipPane) {
        this.baseNode = baseNode;
        this.tipPane = tipPane;
        this.event = event;
        this.isCustom = true;
        this.offsetX = 0;
        build();
        this.tipPane.getStyleClass().add("toolTipContainer");
    }

    public ToolTip(EventType<MouseEvent> event, Node baseNode, AnchorPane tipPane, double offsetX) {
        this(event, baseNode, tipPane);
        this.paramOffsetX = offsetX;
    }

    public ToolTip(EventType<MouseEvent> event, Node baseNode, AnchorPane tipPane, double offsetX, double OffsetY) {
        this(event, baseNode, tipPane, offsetX);
        this.paramOffsetY = offsetY;
    }

    public ToolTip(EventType<MouseEvent> event, Node baseNode, String text) {
        this.baseNode = baseNode;
        this.event = event;
        this.isCustom = false;
        toolTipFinalize(text);
        build();
    }

    public ToolTip(EventType<MouseEvent> event, Node baseNode, String text, double offsetX) {
        this(event, baseNode, text);
        this.paramOffsetX = offsetX;
    }

    public ToolTip(EventType<MouseEvent> event, Node baseNode, String text, double offsetX, double offsetY) {
        this(event, baseNode, text, offsetX);
        this.paramOffsetY = offsetY;
    }

    private void toolTipFinalize(String text) {
        tipPane = new AnchorPane();
        Label label = new Label(text);
        this.tipLabel = label;
        label.getStyleClass().add("toolTipLabel");
        AnchorPane labelWrapper = new AnchorPane(label);
        tipPane.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        tipPane.getStyleClass().add("toolTipContainer");
        tipPane.setStyle(""
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
        VBox box = new VBox(labelWrapper, triangleWrapper);
        tipPane.getChildren().add(box);

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
        tipPane.setEffect(effect);
        this.effect = effect;
        tipPane.setVisible(false);
        this.triangle = triangle;
    }

    public void addHandler() {
        baseNode.localToSceneTransformProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                updatePosition();
            }
        });
        if (event == MouseEvent.MOUSE_ENTERED) {
            baseNode.addEventFilter(MouseEvent.MOUSE_ENTERED, (MouseEvent event1) -> {
                //invisibleAll();
                updatePosition();
                tipPane.setVisible(true);
            });
            baseNode.addEventFilter(MouseEvent.MOUSE_EXITED, (MouseEvent event1) -> {
                //invisibleAll();
                tipPane.setVisible(false);
            });
            if (isCustom) {
                tipPane.setOnMouseMoved((event) -> {
                    tipPane.setVisible(true);
                });
                tipPane.setOnMouseExited((event) -> {
                    tipPane.setVisible(false);
                });
            } else {
                tipPane.setDisable(true);
            }
        } else if (event == MouseEvent.MOUSE_CLICKED) {
            baseNode.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    boolean isVisible = tipPane.isVisible();
                    invisibleAll();
                    updatePosition();
                    tipPane.setVisible(!isVisible);
                }
            });
        }
    }

    public void setText(String text) {
        tipLabel.setText(text);
    }

    public void updatePosition() {
        baseNodeWidth = baseNode.getBoundsInLocal().getMaxX() - baseNode.getBoundsInLocal().getMinX();
        Platform.runLater(() -> tipPane.toFront());
        if (paramOffsetX == 0 && isCustom == false) {
            offsetX = -tipPane.getWidth() / 2 + baseNodeWidth / 2;
            trianleLayoutX = -offsetX - triangleBase / 2 + baseNodeWidth / 2;
        } else {
            offsetX = paramOffsetX;
        }
        if (paramOffsetY == 0) {
            offsetY = -tipPane.getHeight();
        } else {
            offsetY = paramOffsetY;
        }

        double posX = baseNode.localToScene(baseNode.getLayoutBounds()).getMinX() + offsetX;
        double posY = baseNode.localToScene(baseNode.getBoundsInLocal()).getMinY() + offsetY;

        if (posX < minOffset) {
            if (!isCustom) {
                posX = minOffset;
                trianleLayoutX = baseNode.localToScene(baseNode.getLayoutBounds()).getMinX() - minOffset - triangleBase / 2 + baseNodeWidth / 2;
            }
        } else if (posX + tipPane.getWidth() > GLOBAL.WIDTH) {
            if (!isCustom) {
                posX = GLOBAL.WIDTH - tipPane.getWidth() - minOffset;
                trianleLayoutX = baseNode.localToScene(baseNode.getLayoutBounds()).getMinX() - posX + baseNodeWidth / 2 - triangleBase / 2;
            }
        }
        if (posY < minOffset) {
            posY = baseNode.localToScene(baseNode.getLayoutBounds()).getMaxY();
            if (!isCustom) {
                posY += triangleHeight;
                VBox box = (VBox) tipPane.getChildren().get(0);
                if (box.getChildren().get(1).equals(triangle.getParent())) {
                    AnchorPane labelWrapper = (AnchorPane) box.getChildren().get(0);
                    box.getChildren().remove(0);
                    box.getChildren().add(labelWrapper);
                    triangle.getPoints().set(5, -triangle.getPoints().get(5));
                }
            }
        } else {
            posY = baseNode.localToScene(baseNode.getLayoutBounds()).getMinY() - tipPane.getHeight();
            if (!isCustom) {
                VBox box = (VBox) tipPane.getChildren().get(0);
                if (box.getChildren().get(0).equals(triangle.getParent())) {
                    AnchorPane triangleWrapper = (AnchorPane) box.getChildren().get(0);
                    box.getChildren().remove(0);
                    box.getChildren().add(triangleWrapper);
                    triangle.getPoints().set(5, -triangle.getPoints().get(5));
                }
            }
        }
        tipPane.setLayoutX(posX);
        tipPane.setLayoutY(posY);
        if (!isCustom) {
            triangle.setLayoutX(trianleLayoutX);
        }
    }

    public static void invisibleAll() {
        for (AnchorPane pane : toolPaneList) {
            pane.setVisible(false);
        }
    }

    private void setDefaultOffsetY() {
        if (offsetY == 74567) {
            Platform.runLater(() -> {
                Platform.runLater(() -> {
                    offsetY = tipPane.getBoundsInLocal().getMaxY() - tipPane.getBoundsInLocal().getMinY();
                });
            });
        }
    }

    private void setDefaultOffsetX() {
        if (offsetY == 74567) {
            Platform.runLater(() -> {
                offsetX = tipPane.getWidth() / 2 - baseNodeWidth / 2;
            });
        }
    }
}
