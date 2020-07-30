/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose ToolKit | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.TargetStudentPage;

import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PropertiesController implements Initializable {

    private Label uploadBtn;
    @FXML
    private Label deleteIcon;
    @FXML
    private Label upIcon;
    @FXML
    private Label downIcon;
    private AnchorPane mainContainer;
    private VBox parentContainer;
    private TargetStudentPageController parentController;
    @FXML
    private AnchorPane container;
    @FXML
    private Label iconBox;
    @FXML
    private Label selectIconBtn;

    private AnchorPane iconPack;
    private AnchorPane parentControllerContainer;
    @FXML
    private FontAwesomeIconView iconHolder;
    @FXML
    private ImageView iconPicBox;
    @FXML
    private TextField answerField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iconPicBox.setVisible(false);
        parentContainer = new VBox();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/CreateIconPack.fxml"));
            iconPack = loader.load();
            PropertiesController ctrl = this;
            Platform.runLater(() -> {
                loader.<CreateIconPackController>getController().setParent(ctrl, (AnchorPane) GLOBAL.stage.getScene().getRoot());
            });
            ToolTip t = new ToolTip(MouseEvent.MOUSE_CLICKED, selectIconBtn, iconPack);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mouseExited(MouseEvent event) {
    }

    @FXML
    private void mouseEntered(MouseEvent event) {
    }

    @FXML
    private void mouseClicked(MouseEvent event) {
        Object src = event.getSource();
        if (src == deleteIcon) {
            parentContainer.getChildren().remove(container);
        } else if (src == upIcon) {
            ToolKit.moveRow(parentContainer, parentContainer.getChildren().indexOf(container), -1);
        } else if (src == downIcon) {
            ToolKit.moveRow(parentContainer, parentContainer.getChildren().indexOf(container), 1);
        }
    }

    void setParent(TargetStudentPageController parentController, VBox parentContainer, AnchorPane mainContainer) {
        this.parentController = parentController;
        this.parentContainer = parentContainer;
        this.mainContainer = mainContainer;
    }
//
//      private void load() {
//            HBox root = new HBox();
//            ScrollPane scroller = new ScrollPane();
//            VBox vbox = new VBox(20);
//            scroller.setContent(vbox);
//            Label bindingLabel = new Label("Binding here");
//            for (int i = 0; i < 4; i++) {
//                  vbox.getChildren().add(new Label("Item " + (i + 1)));
//            }
//            vbox.getChildren().add(bindingLabel);
//            for (int i = 0; i < 4; i++) {
//                  vbox.getChildren().add(new Label("Item " + (i + 6)));
//            }
//            Label anchor = new Label("Anchor");
//            anchor.setStyle("-fx-background-color: antiquewhite");
//            StackPane stack = new StackPane(anchor);
//
//            root.getChildren().addAll(scroller, stack);
//
//            Line line = new Line();
//            line.setManaged(false);
//            root.getChildren().add(line);
//
//            ChangeListener<Object> listener = (obs, oldValue, newValue)
//                  -> updateLine(line, anchor, bindingLabel);
//
//            anchor.boundsInLocalProperty().addListener(listener);
//            anchor.localToSceneTransformProperty().addListener(listener);
//            bindingLabel.boundsInLocalProperty().addListener(listener);
//            bindingLabel.localToSceneTransformProperty().addListener(listener);
//      }
//
//      private void updateLine(Line line, Node start, Node end) {
//            Node target = line.getParent();
//
//            Bounds startBounds = convertBoundsToTarget(start, target);
//            Bounds endBounds = convertBoundsToTarget(end, target);
//
//            line.setStartX(startBounds.getMinX() + startBounds.getWidth() / 2);
//            line.setStartY(startBounds.getMinY() + startBounds.getHeight() / 2);
//            line.setEndX(endBounds.getMinX() + endBounds.getWidth() / 2);
//            line.setEndY(endBounds.getMinY() + endBounds.getHeight() / 2);
//      }
//
//      private Bounds convertBoundsToTarget(Node node, Node target) {
//            Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
//            return target.sceneToLocal(boundsInScene);
//      }

    public void setIcon(String iconName) {
        iconBox.setVisible(true);
        iconPicBox.setVisible(false);
        iconHolder.setGlyphName(iconName);
    }

    void setIconPackVisibility(boolean b) {
        iconPack.setVisible(b);
    }

    void setIconPic(File file) throws FileNotFoundException {
        iconBox.setVisible(false);
        iconPicBox.setVisible(true);
        iconPicBox.setImage(new Image(new FileInputStream(file)));
    }

}
