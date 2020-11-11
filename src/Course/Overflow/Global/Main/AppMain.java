package Course.Overflow.Global.Main;

/*
 * To change this license HEADER, choose License Headers in Project Properties. To change this
 * template file, choose ToolKit | Templates and open the template in the editor.
 */
import Course.Overflow.Course.Course;
import Course.Overflow.DB;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageController;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import Course.Overflow.Teacher.CreateCourse.CreateCourse;
import Course.Overflow.Teacher.Teacher;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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


// To show the main app, just toggle the bottom 2 line comments.
        if(System.getProperty("user.name").equals("ASUS")){
            int Testing = 1;
            if(Testing == 1){
                System.out.println("USER : MEHEDI");
                GLOBAL.TEACHER = new Teacher("MehediHasan");
                GLOBAL.ACCOUNT_TYPE = Person.AccountType.Teacher;
                GLOBAL.PAGE_CTRL = new PageController();

                CreateCourse mp = new CreateCourse();
                Course course = new Course(19);
                mp.loadData(course);
                pane = mp.getRootPane();
                root.getChildren().add(pane);
            }
            else mehediTestPage();
        }
        else{
            System.out.println("USER : SHAMMYA");
            shammyaTestPage();
        }
        
        GLOBAL.stage = primaryStage;
        GLOBAL.rootPane = root;
        scene = new Scene(root,GLOBAL.WIDTH, GLOBAL.HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("FXML is loaded...");
        
        primaryStage.setOnCloseRequest((event) -> {
            DB.closeConnection();
            System.exit(1);
        });
    }
    private void mehediTestPage() throws IOException{
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        DB.closeConnection();
    }

    private void shammyaTestPage() {
        Button button = new Button();
        pane = new AnchorPane(button);
        
        root.getChildren().add(pane);
    }

}
