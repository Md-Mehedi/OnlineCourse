/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course.Home;

import Course.Overflow.Global.GLOBAL;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 *
 * @author Md Mehedi Hasan
 */
public class Main extends Application {
    AnchorPane root;
    ScrollPane scroll;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        root = new AnchorPane();
        scroll = new ScrollPane();
        GLOBAL.stage = primaryStage;
        GLOBAL.rootPane = root;
        AnchorPane root1 = (AnchorPane) FXMLLoader.load(getClass().getResource("CourseDetails.fxml"));
        //AnchorPane root = new AnchorPane();
        scroll.setContent(root1);
        root.getChildren().add(scroll);
        Scene scene = new Scene(root,GLOBAL.WIDTH,200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
