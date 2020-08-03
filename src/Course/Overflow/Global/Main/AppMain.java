package Course.Overflow.Global.Main;

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose ToolKit | Templates and open the template in the editor.
 */
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Teacher.CreateCourse.CreateCourse;
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
public class AppMain extends Application {
    AnchorPane root;
    AnchorPane pane;
    Scene scene;
    public static boolean complete = false;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        complete = true;
        primaryStage.setTitle("Course Overflow");
        primaryStage.getProperties().put("hostServices", this.getHostServices());
        root = new AnchorPane();
        pane = new AnchorPane();
        GLOBAL.stage = primaryStage;
        GLOBAL.rootPane = root;

        CreateCourse mp = new CreateCourse();
        pane = mp.getRootPane();

// To show the main app, just toggle the bottom 2 line comments.
        //root.getChildren().add(pane);
        testPage();
        
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        GLOBAL.WIDTH = root.getWidth();
        GLOBAL.HEIGHT = root.getHeight();
        
        primaryStage.setOnCloseRequest((event) -> {
            System.exit(1);
        });
        System.out.println("FXML is loaded...");
    }
    private void testPage() throws IOException{
        pane = (AnchorPane) FXMLLoader.load(getClass().getResource(GLOBAL.MESSAGING_LOCATION+ "/Messenger.fxml"));
//        ContainerPage page = new ContainerPage();
//        GLOBAL.PAGE_CTRL = page;
//        pane = page.getContainer();
        
        ScrollPane sc = new ScrollPane(pane);
        sc.setMaxHeight(800);
        root.getChildren().add(sc);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
