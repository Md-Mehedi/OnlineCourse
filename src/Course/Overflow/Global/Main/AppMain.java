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
        root.setPrefWidth(GLOBAL.WIDTH);
        root.setPrefHeight(GLOBAL.HEIGHT);
        
        
//        ImageView iv = new ImageView(new Image("/Course/Overflow/Files/Picture/No Data Found.jpg"));
//        iv.setFitHeight(GLOBAL.HEIGHT);
//        iv.setPreserveRatio(true);
//        System.out.println("adding");
//        root.getChildren().add(iv);
//        iv.toFront();
//        System.out.println("new thread is running");

// To show the main app, just toggle the bottom 2 line comments.
        System.out.println(System.getProperty("user.name"));
        if (System.getProperty("user.name").equals("ASUS")) {
            mehediTestPage();
            scene = new Scene(root, GLOBAL.WIDTH, GLOBAL.HEIGHT);
        } else {
            shammyaTestPage();
            scene = new Scene(root);
        }

        GLOBAL.stage = primaryStage;
        GLOBAL.rootPane = root;
        
        cssEffect();

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
        int testing = 1;
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
        PageController pageCtrl = new PageController(PageName.Home);
//        pageCtrl.loadFXML(GLOBAL.ADMIN_LOCATION+"/Filtering.fxml");
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

    private void cssEffect() {
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/LightTheme.css");
        root.setId("mainRoot");
//        ImagePattern ip = new ImagePattern(new Image(GLOBAL.ICON_LOCATION + "/White bg.png"), 100, 100, 500, 500, false);
//        root.setBackground(new Background(new BackgroundFill(ip, CornerRadii.EMPTY, Insets.EMPTY)));    
    }
}
