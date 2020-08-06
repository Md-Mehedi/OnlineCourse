/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components.TopMenuBar;

import Course.Overflow.Global.Customize.Icon;
import Course.Overflow.Global.Customize.Icon.IconType;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Global.Tools.IconChooser.IconChooser;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MenuItemEditorController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private Label menuTypeLabel;
    @FXML
    private Label idNum;
    @FXML
    private Label selectIconBtn;
    @FXML
    private StackPane iconContainer;
    @FXML
    private TextField nameField;
    @FXML
    private Label deleteIcon;
    @FXML
    private Label upIcon;
    @FXML
    private Label downIcon;
    @FXML
    private VBox subContainer;

    @FXML
    private ImageView addIcon;
    @FXML
    private VBox mainContainer;
    private boolean isSubMenu;
    private VBox parentContainer;
    private ArrayList<MenuItemEditorController> ctrlList;
    private ArrayList<MenuItemEditorController> subCtrlrList;
    @FXML
    private HBox idContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        subCtrlrList = new ArrayList();
        container.getStyleClass().add("menuContainerCSS");
        Platform.runLater(() -> {
        for(Node node : idContainer.getChildren()){
            Label l = (Label) node;
            l.setStyle("");
//            l.setPrefWidth(AnchorPane.USE_COMPUTED_SIZE);
//            l.setMinWidth(AnchorPane.USE_COMPUTED_SIZE);
//            l.setMaxWidth(AnchorPane.USE_COMPUTED_SIZE);
        }
            parentContainer = (VBox) container.getParent();
            refreshId();
        });
    }

    @FXML
    private void mouseExited(MouseEvent event) {
    }

    @FXML
    private void mouseEntered(MouseEvent event) {
    }

    @FXML
    private void mouseClicked(MouseEvent event) throws IOException {
        Object src = event.getSource();
        if (src == addIcon) {
            createSubMenu();
        } else if (src == selectIconBtn) {
            IconChooser chooser = new IconChooser();
            Icon icon = chooser.showIconWindow();
            iconContainer.getChildren().clear();
            iconContainer.getChildren().add(icon);
        } else if (src == deleteIcon) {
            parentContainer.getChildren().remove(container);
            refreshId();
        } else if (src == upIcon) {
            ToolKit.moveRow(parentContainer, parentContainer.getChildren().indexOf(container), -1);
            refreshId();
        } else if (src == downIcon) {
            ToolKit.moveRow(parentContainer, parentContainer.getChildren().indexOf(container), 1);
            refreshId();
        }
    }

    public void makeAsSubMenu() {
        if (!isSubMenu) {
            container.getStyleClass().remove("menuContainerCSS");
            isSubMenu = true;
            menuTypeLabel.setText("Sub-menu item");
            mainContainer.getChildren().remove(1);
            mainContainer.getChildren().remove(1);
        }
    }

    public void setCtrlList(ArrayList<MenuItemEditorController> list) {
        ctrlList = list;
    }

    public void setId(int id) {
        idNum.setText("" + id);
    }

    public int getContainerIdx() {
        int idx = parentContainer.getChildren().indexOf(container);
        return idx;
    }

    private void refreshId() {
        Platform.runLater(() -> {
            for (MenuItemEditorController ctrl : ctrlList) {
                ctrl.setId(ctrl.getContainerIdx() + 1);
            }
        });
    }

    public void setName(String name) {
        nameField.setText(name);
    }

    public void setIcon(IconType type, String iconName) {
        iconContainer.getChildren().clear();
        iconContainer.getChildren().add(new Icon(type, iconName));
    }

    public void setIcon(Icon icon) {
        iconContainer.getChildren().clear();
        iconContainer.getChildren().add(icon);
    }

    public void setMenuItem(MenuItem item) {
        setName(item.getName());
        setIcon(item.getIcon());
    }

    public void setSubMenuItem(ArrayList<MenuItem> items) {
        for (MenuItem item : items) {
            MenuItemEditorController ctrl = createSubMenu();
            ctrl.setMenuItem(item);
        }
    }

    public void setTopMenu(TopMenu menu) {
        setSubMenuItem(menu.getSubMenus());
        setMenuItem(menu.getMenu());
    }

    private MenuItemEditorController createSubMenu() {
        MenuItemEditorController ctrl = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.TOP_MENU_LOACATION + "/MenuItemEditor.fxml"));
            AnchorPane pane;
            pane = loader.load();
            subContainer.getChildren().add(pane);
            ctrl = loader.<MenuItemEditorController>getController();
            ctrl.makeAsSubMenu();
            subCtrlrList.add(ctrl);
            ctrl.setCtrlList(subCtrlrList);
        } catch (IOException ex) {
            Logger.getLogger(MenuItemEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ctrl;
    }
}
