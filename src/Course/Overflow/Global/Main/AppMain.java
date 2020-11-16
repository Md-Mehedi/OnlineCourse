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
import Course.Overflow.Student.Student;
import Course.Overflow.Teacher.CreateCourse.CreateCourse;
import Course.Overflow.Teacher.Teacher;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        GLOBAL.WIDTH = 1460;
        GLOBAL.HEIGHT = 900;
        


// To show the main app, just toggle the bottom 2 line comments.
        System.out.println(System.getProperty("user.name"));
        if(System.getProperty("user.name").equals("ASUS")){
            int Testing = 0;
            if(Testing == 1){
                GLOBAL.TEACHER = new Teacher("MehediHasan");
                GLOBAL.ACCOUNT_TYPE = Person.AccountType.Teacher;
                GLOBAL.PAGE_CTRL = new PageController();

                CreateCourse mp = new CreateCourse();
                Course course = new Course(19);
                mp.loadData(course);
                pane = mp.getRoot();
                root.getChildren().add(pane);
            }
            else mehediTestPage();
        }
        else{
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

    private void mehediTestPage() throws IOException {
//        GLOBAL.TEACHER = new Teacher("MehediHasan");
//        GLOBAL.ACCOUNT_TYPE = Person.AccountType.Teacher;
        GLOBAL.STUDENT = new Student("mm");
        GLOBAL.ACCOUNT_TYPE = Person.AccountType.Student;
        
        PageController pageCtrl = new PageController(PageName.Home);
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

    private void shammyaTestPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.ADMIN_LOCATION + "/MaintainLanguage.fxml"));
            AnchorPane pane = loader.load();
            root.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(AppMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
