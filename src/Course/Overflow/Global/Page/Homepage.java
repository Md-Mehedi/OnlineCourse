/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Global.Components.CarouselController;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Homepage extends Page{

    private FXMLLoader loader;
    private VBox verticalBox;
    private ArrayList<CarouselController> carousels;
    
    
    public Homepage(){
        super(PageName.Home);
        verticalBox = new VBox();
        root.getChildren().add(verticalBox);
        ToolKit.setAnchor(verticalBox, 0, 0, 0, 0);
        carousels = new ArrayList<>();
        addCarousels();
        
        System.out.println("Home page loaded...");
    }
    
    private void addCarousels() {
        for(int i=0;i<3;i++){
            try {
                loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/Carousel.fxml"));
                AnchorPane pane = loader.load();
                verticalBox.getChildren().add(pane);
                carousels.add(loader.<CarouselController>getController());
            } catch (IOException ex) {
                    Logger.getLogger(ContainerPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
