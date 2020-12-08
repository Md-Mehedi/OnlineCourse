package Course.Overflow.Global.Main;

/*
 * To change this license HEADER, choose License Headers in Project Properties. To change this
 * template file, choose ToolKit | Templates and open the template in the editor.
 */
import Course.Overflow.DB;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageController;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.Person.AccountType;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Student.Student;
import java.io.IOException;
import javafx.application.Application;
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
        GLOBAL.WIDTH = 1460;
        GLOBAL.HEIGHT = 900;
        boolean ligthTheme = true;
        GLOBAL.IS_LIGHT = !ligthTheme;
        GLOBAL.rootPane = root;
        root.setPrefWidth(GLOBAL.WIDTH);
        root.setPrefHeight(GLOBAL.HEIGHT);
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        root.setId("mainRoot");
        
// To show the main app, just toggle the bottom 2 line comments.
        System.out.println(System.getProperty("user.name"));
        if (System.getProperty("user.name").equals("ASUS")) {
            mehediTestPage();
            scene = new Scene(root, GLOBAL.WIDTH, GLOBAL.HEIGHT);
        } else {
            mehediTestPage();
//            shammyaTestPage();
            scene = new Scene(root);
        }

        GLOBAL.stage = primaryStage;

        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("FXML is loaded...");
        primaryStage.setOnCloseRequest((event) -> {
            DB.closeConnection();
            System.exit(1);
        });
    }

    private void mehediTestPage() throws IOException {
        PageController pageCtrl = null;
        int testing = 0;
        if(testing == 1){
            GLOBAL.ACCOUNT_TYPE = Person.AccountType.Student;
            GLOBAL.STUDENT = new Student("mehediS");
            pageCtrl = new PageController(PageName.Home);
        }
        else{
            pageCtrl = new PageController(PageName.Login);
        }
        GLOBAL.PAGE_CTRL = pageCtrl;
        pane = pageCtrl.getContainer();
        root.getChildren().add(pane);
        ToolKit.setAnchor(pane, 0, 0, 0, 0);
    }

    public static void main(String[] args) {
        launch(args);
        DB.closeConnection();
    }

    private void shammyaTestPage() throws IOException {
        GLOBAL.ACCOUNT_TYPE = AccountType.Student;
        GLOBAL.STUDENT = new Student("mehediS");
        PageController pageCtrl = new PageController(PageName.Login);
        //pageCtrl.loadFXML(GLOBAL.GLOBAL_LOCATION+"/MediaPlayer.fxml");
        GLOBAL.PAGE_CTRL = pageCtrl;
        pane = pageCtrl.getContainer();

        ScrollPane sc = new ScrollPane(pane);
        sc.setPrefWidth(GLOBAL.WIDTH);
        sc.setPrefHeight(GLOBAL.HEIGHT);
        sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.getChildren().add(sc);

//        GLOBAL.rootScroll = sc;
    }
}
