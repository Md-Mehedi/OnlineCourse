/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

    public PurchaseHistoryPage() {
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
                
        for(int i=0; i<20; i++){
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
        ImageView coursePhoto = new ImageView(new Image(GLOBAL.PICTURE_LOCATION + "/Cover.jpg"));
        coursePhoto.setFitHeight(70);
        coursePhoto.setFitWidth(120);
        coursePhoto.setPreserveRatio(true);
        
        Label courseName = new Label("Course klsjdfl fljlf skjf lsjfklsjdf lsdkjfl ksdjf ksldjflksdjf lksjdf skdjflkjwejr lkwejr lkwjrklewj Name");
        courseName.setStyle(courseName.getStyle() + ""
              + "-fx-max-width: 350;"
              + font
              + bold
              + "");
        
        ImageView studentPhoto = new ImageView(new Image(GLOBAL.PICTURE_LOCATION + "/Person 1.jpg"));
        studentPhoto.setFitHeight(70);
        studentPhoto.setFitWidth(70);
        studentPhoto.setPreserveRatio(true);
        
        Label studentFirstName = new Label("Md Mehedi");
        Label studentLastName = new Label("Hasan" + (row+1));
        VBox nameBox = new VBox(studentFirstName,studentLastName);
        nameBox.setStyle("-fx-alignment: center-left; -fx-spacing: 0;");
        studentFirstName.setStyle(studentFirstName.getStyle() + ""
              + "-fx-max-width: 200;"
              + font
              + bold
              + "");
        studentLastName.setStyle(studentFirstName.getStyle());
        
        Label purchaseTime = new Label("09:12 AM");
        Label purchaseDate = new Label("12 September, 2020");
        VBox purchaseBox = new VBox(purchaseTime,purchaseDate);
        purchaseBox.setStyle("-fx-alignment: center; -fx-spacing: 0;");
        purchaseDate.setStyle(purchaseDate.getStyle() + ""
              + "-fx-alignment: center;"
              + "-fx-pref-width: 170;"
              + "-fx-font-size: 17;"
              + "");
        purchaseTime.setStyle(purchaseDate.getStyle());
        
        Label cost = new Label("22222");
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
