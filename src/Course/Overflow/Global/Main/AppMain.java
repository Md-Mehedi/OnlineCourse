package Course.Overflow.Global.Main;

/*
 * To change this license HEADER, choose License Headers in Project Properties. To change this
 * template file, choose ToolKit | Templates and open the template in the editor.
 */
import Course.Overflow.DB;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageController;
import Course.Overflow.Global.Page.PageName;
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
        DB.startConnection();
        complete = true;
        primaryStage.setTitle("Course Overflow");
        primaryStage.getProperties().put("hostServices", this.getHostServices());
        root = new AnchorPane();
        pane = new AnchorPane();
        GLOBAL.stage = primaryStage;
        GLOBAL.rootPane = root;
        GLOBAL.WIDTH = 1460;
        GLOBAL.HEIGHT = 900;
        

//// To show the main app, just toggle the bottom 2 line comments.
        if(System.getProperty("user.name").equals("ASUS")){
            System.out.println("USER : MEHEDI");
//            CreateCourse mp = new CreateCourse();
//            pane = mp.getRootPane();
//            root.getChildren().add(pane);
            mehediTestPage();
            scene = new Scene(root,GLOBAL.WIDTH, GLOBAL.HEIGHT);
        }
        else{
            System.out.println("USER : SHAMMYA");
            shammyaTestPage();
            scene = new Scene(root);
        }
//        
        primaryStage.setScene(scene);
        primaryStage.show();
//        
//        primaryStage.setOnCloseRequest((event) -> {
//            System.out.println(GLOBAL.HEIGHT);
//            System.out.println(GLOBAL.WIDTH);
//            System.exit(1);
        //       });
        System.out.println("FXML is loaded...");
    }

    private void mehediTestPage() throws IOException {
        PageController pageCtrl = new PageController(PageName.Signup);
        GLOBAL.PAGE_CTRL = pageCtrl;
        pane = pageCtrl.getContainer();

        ScrollPane sc = new ScrollPane(pane);
        sc.setPrefWidth(GLOBAL.WIDTH);
        sc.setPrefHeight(GLOBAL.HEIGHT);
        sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.getChildren().add(sc);

        GLOBAL.rootScroll = sc;
    }

    public static void main(String[] args) {
        launch(args);
        DB.closeConnection();
    }

    private void shammyaTestPage() throws IOException {
       pane = FXMLLoader.load(getClass().getResource(GLOBAL.ADMIN_LOCATION + "/MaintainCountry.fxml"));
       root.getChildren().add(pane);
    }

}
