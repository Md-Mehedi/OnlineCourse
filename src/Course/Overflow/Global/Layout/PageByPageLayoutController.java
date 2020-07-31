/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Layout;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Teacher.TeacherPreviewController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PageByPageLayoutController implements Initializable {

    private FXMLLoader loader;
    int totalCourse;
    int column;
    int totalItem1page;
    int totalPageNums;

    int currentPage;
    ArrayList<GridPane> grids;
    ArrayList<Label> pageNumbers;

    @FXML
    private GridPane grid;
    @FXML
    private HBox pageNumContainer;
    @FXML
    private ComboBox<String> numOfItemChoiceBox;
    private ObservableList<String> numOfItemList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalCourse = 43;
        totalItem1page = 12;
        column = 4;
        currentPage = 0;
        readyNumOfItemList();

        makePageNumbers();
        loadPage(1);
    }

    @SuppressWarnings("unchecked")
    private void readyNumOfItemList() {
        numOfItemList = FXCollections.observableArrayList();
        numOfItemList.addAll("8", "12", "16", "20", "30", "40");
        numOfItemChoiceBox.setItems(numOfItemList);
        numOfItemChoiceBox.setItems(numOfItemList);
        numOfItemChoiceBox.setValue("12");
        totalItem1page = 12;

        numOfItemChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) {
                totalItem1page = (int) Math.ceil(Integer.parseInt(numOfItemList.get(new_value.intValue())));
                makePageNumbers();
                currentPage = 0;
                loadPage(1);
            }
        });
    }
    
    private void makePageNumbers() {
        pageNumContainer.getChildren().clear();
        totalPageNums = (int) Math.ceil(1.0 * totalCourse / totalItem1page);
        for (int i = 1; i <= totalPageNums; i++) {
            Label label = new Label(i + "");
            pageNumContainer.getChildren().add(label);

            label.setOnMouseEntered(event -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(100), label);
                st.setToX(1.2);
                st.setToY(1.2);
                st.play();
            });
            label.setOnMouseExited(event -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(100), label);
                st.setToX(1);
                st.setToY(1);
                st.play();
            });
            label.setOnMouseClicked((event) -> {
                int idx = pageNumContainer.getChildren().indexOf(label) + 1;
                if (currentPage != idx) {
                    loadPage(idx);
                }
            });
        }
    }

    private void loadPage(int pageNum) {
        if (pageNum != currentPage) {
            if (currentPage != 0) {
                pageNumContainer.getChildren().get(currentPage - 1).setId("");
            }
            currentPage = pageNum;
            System.out.println(currentPage);
            pageNumContainer.getChildren().get(currentPage - 1).setId("selectedPage");

            grid.getChildren().clear();
            for (int i = totalItem1page * (pageNum - 1); i < Math.min(totalItem1page * pageNum, totalCourse); i++) {
                //System.out.println(i);
                try {
                    loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/CourseBox.fxml"));
                    AnchorPane pane = loader.load();
                    grid.add(pane, (i - totalItem1page * (pageNum - 1)) % column, (i - totalItem1page * (pageNum - 1)) / column);
                } catch (IOException ex) {
                    Logger.getLogger(TeacherPreviewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            int n = grid.getChildren().size();
            if(n<4){
                grid.setPrefWidth(n*250 + (n-1)*45);
            }
        }
    }


    public void setTotalCourse(int totalCourse) {
        this.totalCourse = totalCourse;
    }
}
