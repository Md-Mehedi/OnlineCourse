/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global;

import Course.Overflow.Global.Page.ContainerPage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Md Mehedi Hasan
 */
public class GLOBAL {

    public static final String ROOT_LOCATION = System.getProperty("user.dir") + "/src";
    public static final String HOME_LOCATION = "/Course/Overflow";

    public static final String ADMIN_LOCATION = HOME_LOCATION + "/Admin";
    public static final String STUDENT_LOCATION = HOME_LOCATION + "/Student";
    public static final String TEACHER_LOCATION = HOME_LOCATION + "/Teacher";
    public static final String GLOBAL_LOCATION = HOME_LOCATION + "/Global";
    public static final String COURSE_LOCATION = HOME_LOCATION + "/Course";

    public static final String MAIN_LOCATION = GLOBAL_LOCATION + "/Main";
    public static final String LAYOUT_LOCATION = GLOBAL_LOCATION + "/Layout";
    public static final String COMMUNICATION_LOCATION = GLOBAL_LOCATION + "/Communication";
    public static final String COMPONENTS_LOCATION = GLOBAL_LOCATION + "/Components";
    public static final String TOP_MENU_LOACATION = COMPONENTS_LOCATION + "/TopMenuBar";
    public static final String NOTIFICATION_LOCATION = COMPONENTS_LOCATION + "/Notification";
    
    public static final String COURSE_HOME_LOCATION = COURSE_LOCATION + "/Home";
    public static final String COURSE_CONTENTS_LOCATION = COURSE_LOCATION + "/Contents";

    public static final String ICON_LOCATION = HOME_LOCATION + "/Files/Icon";
    public static final String PDF_LOCATION = HOME_LOCATION + "/Files/PDF";
    public static final String PICTURE_LOCATION = HOME_LOCATION + "/Files/Picture";
    public static final String VIDEO_LOCATION = HOME_LOCATION + "/Files/Video";

    public static final String CREATE_COURSE_LOCATION = TEACHER_LOCATION + "/CreateCourse";
    public static final String COURSE_CURRICULUM_LOCATION = CREATE_COURSE_LOCATION + "/Curriculum";
    public static final String COURSE_LANDING_PAGE_LOCATION = CREATE_COURSE_LOCATION + "/CourseLandingPage";
    public static final String COURSE_PRICING_LOCATION = CREATE_COURSE_LOCATION + "/Pricing";
    public static final String COURSE_TARGET_STUDENT_PAGE = CREATE_COURSE_LOCATION + "/TargetStudentPage";

    public static Stage stage;
    public static double LEC_BOX_CON_LEFT_PADDING = 30;
    public static double LEC_BOX_CON_RIGHT_PADDING = 0;
    public static double AVAILABLE_CONTENT_CON_PADDING = 10;
    public static double LABEL_PREF_WIDTH = 1005 - LEC_BOX_CON_LEFT_PADDING - LEC_BOX_CON_RIGHT_PADDING - 2 * AVAILABLE_CONTENT_CON_PADDING;

    public static double WIDTH = 1360;
    public static double HEIGHT = 800;
    public static final double CENTER_RIGHT_WIDTH = 1055;
    public static final double LEFT_WIDTH = 300;
    public static AnchorPane rootPane;
    public static AnchorPane scrollingPane;
    public static ContainerPage PAGE_CTRL;
}
