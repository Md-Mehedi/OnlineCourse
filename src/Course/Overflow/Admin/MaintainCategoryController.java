package Course.Overflow.Admin;

import Course.Overflow.Course.Category;
import Course.Overflow.DB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class MaintainCategoryController implements Initializable {

    @FXML
    private Label countrylabel;
    @FXML
    private TreeView<String> categoryTree;
    @FXML
    private Label countrylabel1;
    @FXML
    private Label countrylabel11;
    private TreeItem<String> rootItem;
    @FXML
    private JFXButton add;
    @FXML
    private JFXTextField Name;
    @FXML
    private Label countrylabel111;
    private String selected = "";
    private TreeItem<String> selectedItem = null;
    @FXML
    private JFXButton delete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        adListener();

        //rootItem.getChildren().addAll(Item1, Item2, Item3, Item4);
    }

    private void loadData() {
        categoryTree.setRoot(null);
        rootItem = new TreeItem<String>("Category List", createIcon(2));
        categoryTree.setRoot(rootItem);
        rootItem.setExpanded(true);
        ArrayList<Category> catagories = Category.getMainCategories();
        for (Category category : catagories) {
            TreeItem<String> item = new TreeItem<String>(category.getName(), createIcon(3));
            rootItem.getChildren().add(item);
            ArrayList<Category> subcatagories = Category.getSubCategories(category);
            for (Category subcategory : subcatagories) {
                TreeItem<String> subitem = new TreeItem<String>(subcategory.getName(), createNewIcon(subcategory.getImage()));
                item.getChildren().add(subitem);
                item.setExpanded(true);
            }
        }

    }

    private ImageView createNewIcon(Image img) {
        ImageView imgvw = new ImageView(img);
        return imgvw;
    }

    private ImageView createIcon(int id) {
        ImageView imgvw;
        if (id == 1) {
            imgvw = new ImageView(new Image(getClass().getResourceAsStream("/Course/Overflow/Files/Icon/category.png")));
        } else if (id == 2) {
            imgvw = new ImageView(new Image(getClass().getResourceAsStream("/Course/Overflow/Files/Icon/root.png")));
        } else {
            imgvw = new ImageView(new Image(getClass().getResourceAsStream("/Course/Overflow/Files/Icon/child.png")));
        }
        imgvw.setFitHeight(20);
        imgvw.setPreserveRatio(true);
        return imgvw;
    }

    private void adListener() {
        categoryTree.setOnMouseClicked(event -> {
            selectedItem = categoryTree.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                selected = selectedItem.getValue();
                System.out.println(selected);

            } else {
                selected = "";
            }
        });
        add.setOnMouseClicked(event -> {
            if (!selected.isEmpty() && !DB.valueExist("CATEGORY", "NAME", Name.getText())) {
                if (selected.equals("Category List")) {
                    Category.createNewCategory(Name.getText());
                } else {
                    Category.createNewSubCategory(Name.getText(), selected);
                }
                loadData();
                selected = "";
            }
        });
        delete.setOnMouseClicked(event -> {
            if (selectedItem != null) {
                if (selectedItem.getParent().getValue() == "Category List") {
                    int idx = JOptionPane.showConfirmDialog(null, "The SubCategories will Also be deleted !", "select", JOptionPane.CANCEL_OPTION);
                    if (idx == 0) {
                        Category.DeleteCategory(selectedItem.getValue());
                    }
                } else {
                    Category.DeleteCategory(selectedItem.getValue());
                }
            }
            loadData();
            selected="";
        });

    }

}
