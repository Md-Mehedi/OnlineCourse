/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse;

import Course.Overflow.Global.Components.TopMenuBar.MenuBar;
import Course.Overflow.Global.Customize.SVG;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.LeftSlidingPane;
import Course.Overflow.Global.Layout.LeftSlidingPane.Type;
import Course.Overflow.Global.Page.PageName;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Md Mehedi Hasan
 */
public class CreateCourse {

    AnchorPane mainPane;
    AnchorPane leftMenu;
    private LeftSlidingPane lsPane;

    private AnchorPane targetStudentPane;
    private AnchorPane videoLectureTipsPane;
    private AnchorPane curriculumTipsPane;
    private AnchorPane curriculumPane;
    private AnchorPane landingPagePane;
    private AnchorPane pricingPane;
    private AnchorPane container;
    private AnchorPane topMenu;

    public CreateCourse() throws IOException {
        lsPane = new LeftSlidingPane(Type.LEFT_HOVER_CENTER_STATIC);
        targetStudentPane = FXMLLoader.load(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/TargetStudentPage.fxml"));
        videoLectureTipsPane = FXMLLoader.load(getClass().getResource(GLOBAL.CREATE_COURSE_LOCATION + "/VideoLectureTips.fxml"));
        curriculumTipsPane = FXMLLoader.load(getClass().getResource(GLOBAL.CREATE_COURSE_LOCATION + "/CourseCurriculumTips.fxml"));
        curriculumPane = FXMLLoader.load(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/Curriculum.fxml"));
        landingPagePane = FXMLLoader.load(getClass().getResource(GLOBAL.COURSE_LANDING_PAGE_LOCATION + "/Details.fxml"));
        pricingPane = FXMLLoader.load(getClass().getResource(GLOBAL.COURSE_PRICING_LOCATION + "/Pricing.fxml"));
        lsPane.removeFooter();
        
        MenuBar menu = new MenuBar();
        topMenu = menu.getMenuContainer();
        lsPane.setHeader(topMenu);

        lsPane.addContent(targetStudentPane, SVG.TARGET, PageName.CreateCourseTargetStudent);
        lsPane.addContent(videoLectureTipsPane, SVG.VIDEO_CAMERA, PageName.CreateCourseRecordVideo);
        lsPane.addContent(curriculumTipsPane, SVG.IDEA_BULB, PageName.CreateCourseCurriculumTips);
        lsPane.addContent(curriculumPane, SVG.TASK_LIST, PageName.CreateCourseCurriculum);
        lsPane.addContent(landingPagePane, SVG.PROFILE_DETAILS, PageName.CreateCourseLanding);
        lsPane.addContent(pricingPane, SVG.COST, PageName.CreateCoursePricing);
        lsPane.addContent(pricingPane, SVG.COST, PageName.CreateCoursePricing);
        lsPane.addContent(pricingPane, SVG.COST, PageName.CreateCoursePricing);
        lsPane.addContent(pricingPane, SVG.COST, PageName.CreateCoursePricing);
        lsPane.addContent(pricingPane, SVG.COST, PageName.CreateCoursePricing);
        lsPane.setDefaultContent(targetStudentPane);
        container = lsPane.getRoot();
    }
    public AnchorPane getRootPane(){
        return container;
    }
}
