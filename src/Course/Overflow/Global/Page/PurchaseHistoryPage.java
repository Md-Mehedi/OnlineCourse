/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Global.ToolKit;
import Course.Overflow.Student.PurchaseHistory;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class PurchaseHistoryPage extends Page{
    String font = "-fx-font-size: 20;";
    String bold = "-fx-font-weight: bold;";
    
    private VBox container;
    private GridPane grid;
    private GridPane colorGrid;
    private ArrayList<PurchaseHistory> list;

    public PurchaseHistoryPage() {
        super(PageName.PurchaseHistory);
        grid = new GridPane();
        colorGrid = new GridPane();
        root.getChildren().add(colorGrid);
        root.getChildren().add(grid);
        ToolKit.setAnchor(grid, 0, 0, 0, 0);
        ToolKit.setAnchor(colorGrid, 0, 0, 0, 0);
        
        grid.setStyle(grid.getStyle()
              + "-fx-alignment: center;"
              + "-fx-hgap: 20;"
              + "-fx-padding: 5;"
//              + "-fx-background-color: cyan;"
              + "");
        colorGrid.setStyle(grid.getStyle());
        grid.setVgap(30);
        colorGrid.setVgap(10);
        
        createLayout();
    }

    private void createLayout() {
        ColumnConstraints courseNameColumn = new ColumnConstraints();
        courseNameColumn.setHgrow( Priority.ALWAYS );
        
        list = PurchaseHistory.getPurchasedStudentInfo();
        if(list.size() == 0){
            ToolKit.showNoDataFound(root);
            return;
        }
        
        for(int i=0; i<list.size(); i++){
            RowConstraints rc = new RowConstraints();
            rc.setValignment(VPos.CENTER);
            rc.setPrefHeight(70);
            grid.getRowConstraints().add(rc);
            
            Region rg = new Region();
            rg.setStyle("-fx-background-color: #e5e5ea;");
  
            Platform.runLater(()->{
                rg.setPrefSize(1130, 90);
            });
            colorGrid.add(rg, 0, i);
            
            addAContent(i);
        }
    }

    private void addAContent(int row) {
        ImageView coursePhoto = new ImageView(ToolKit.makeImage(list.get(row).getCourse().getCourseImage()));
        coursePhoto.setFitHeight(70);
        coursePhoto.setFitWidth(120);
        coursePhoto.setPreserveRatio(true);
        
        Label courseName = new Label(list.get(row).getCourse().getTitle());
        courseName.setStyle(courseName.getStyle() + ""
              + "-fx-max-width: 350;"
              + font
              + bold
              + "");
        
        ImageView studentPhoto = new ImageView(ToolKit.makeImage(list.get(row).getStudent().getPhoto()));
        studentPhoto.setFitHeight(70);
        studentPhoto.setFitWidth(70);
        studentPhoto.setPreserveRatio(true);
        
        Label studentFirstName = new Label(list.get(row).getStudent().getFirstName());
        Label studentLastName = new Label(list.get(row).getStudent().getLastName());
        VBox nameBox = new VBox(studentFirstName,studentLastName);
        nameBox.setStyle("-fx-alignment: center-left; -fx-spacing: 0;");
        studentFirstName.setStyle(studentFirstName.getStyle() + ""
              + "-fx-max-width: 200;"
              + font
              + bold
              + "");
        studentLastName.setStyle(studentFirstName.getStyle());
        
        ToolKit.print(list.get(row).getTime().toString());
        Label purchaseTime = new Label(ToolKit.makeDateStructured(list.get(row).getTime(), "hh:mm aa"));
        Label purchaseDate = new Label(ToolKit.makeDateStructured(list.get(row).getTime(), "dd MMMMM, yyyy"));
        VBox purchaseBox = new VBox(purchaseTime,purchaseDate);
        purchaseBox.setStyle("-fx-alignment: center; -fx-spacing: 0;");
        purchaseDate.setStyle(purchaseDate.getStyle() + ""
              + "-fx-alignment: center;"
              + "-fx-pref-width: 170;"
              + "-fx-font-size: 17;"
              + "");
        purchaseTime.setStyle(purchaseDate.getStyle());
        
        Label cost = new Label(ToolKit.DoubleToString(list.get(row).getCost()));
        cost.setStyle(cost.getStyle() + ""
              + "-fx-alignment: center-right;"
              + "-fx-pref-width: 100;"
              + "-fx-padding: 0;"
              + "-fx-font-size: 25;"
              + "-fx-font-weight: bold;"
              + "");
        
        grid.add(coursePhoto, 0, row);
        grid.add(courseName, 1, row);
        grid.add(studentPhoto, 2, row);
        grid.add(nameBox, 3, row);
        grid.add(purchaseBox, 4, row);
        grid.add(cost, 5, row);
        
    }
    
    

}
