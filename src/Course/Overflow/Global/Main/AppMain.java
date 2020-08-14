package Course.Overflow.Global.Main;

/*
 * To change this license HEADER, choose License Headers in Project Properties. To change this
 * template file, choose ToolKit | Templates and open the template in the editor.
 */
import Course.Overflow.Global.GLOBAL;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
        GLOBAL.WIDTH = 1460;
        GLOBAL.HEIGHT = 900;

//        CreateCourse mp = new CreateCourse();
//        pane = mp.getRootPane();

// To show the main app, just toggle the bottom 2 line comments.
        //root.getChildren().add(pane);
        testPage();
        
        scene = new Scene(root,GLOBAL.WIDTH, GLOBAL.HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest((event) -> {
            System.out.println(GLOBAL.HEIGHT);
            System.out.println(GLOBAL.WIDTH);
            System.exit(1);
        });
        System.out.println("FXML is loaded...");
    }
    private void testPage() throws IOException{
        pane = (AnchorPane) FXMLLoader.load(getClass().getResource(GLOBAL.LOGIN_SIGNUP_LOCATION+ "/Signup.fxml"));
        StackPane sp = new StackPane(pane);
        sp.setPrefWidth(GLOBAL.WIDTH);
        sp.setPrefHeight(GLOBAL.HEIGHT);

//        ContainerPage page = new ContainerPage();
//        GLOBAL.PAGE_CTRL = page;
//        pane = page.getContainer();
        
        ScrollPane sc = new ScrollPane(sp);
        sc.setPrefWidth(GLOBAL.WIDTH);
        sc.setPrefHeight(GLOBAL.HEIGHT);
        sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.getChildren().add(sc);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
