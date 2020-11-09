/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse;

import Course.Overflow.Course.Course;
import Course.Overflow.Global.Components.TopMenuBar.MenuBar;
import Course.Overflow.Global.Customize.SVG;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.LeftSlidingPane;
import Course.Overflow.Global.Layout.LeftSlidingPane.Type;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Teacher.CreateCourse.CourseLandingPage.DetailsController;
import Course.Overflow.Teacher.CreateCourse.Curriculum.CurriculumController;
import Course.Overflow.Teacher.CreateCourse.Pricing.PricingController;
import Course.Overflow.Teacher.CreateCourse.TargetStudentPage.TargetStudentPageController;
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
    
    private FXMLLoader loader;
    private TargetStudentPageController targetStudentCtrl;
    private CurriculumController curriculumCtrl;
    private DetailsController detailsController;
    private PricingController pricingController;

    public CreateCourse() throws IOException {
        lsPane = new LeftSlidingPane(Type.NO_HOVER);
        loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_TARGET_STUDENT_PAGE + "/TargetStudentPage.fxml"));
        targetStudentPane = loader.load();
        targetStudentCtrl = loader.<TargetStudentPageController>getController();
        
        loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_CURRICULUM_LOCATION + "/Curriculum.fxml"));
        curriculumPane = loader.load();
        curriculumCtrl = loader.getController();
        
        loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_LANDING_PAGE_LOCATION + "/Details.fxml"));
        landingPagePane = loader.load();
        detailsController = loader.getController();
        
        loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_PRICING_LOCATION + "/Pricing.fxml"));
        pricingPane = loader.load();
        pricingController = loader.getController();
        pricingController.setParent(this);

        videoLectureTipsPane = FXMLLoader.load(getClass().getResource(GLOBAL.CREATE_COURSE_LOCATION + "/VideoLectureTips.fxml"));
        curriculumTipsPane = FXMLLoader.load(getClass().getResource(GLOBAL.CREATE_COURSE_LOCATION + "/CourseCurriculumTips.fxml"));
        
        detailsController.setControllers(targetStudentCtrl, curriculumCtrl, pricingController);
        targetStudentCtrl.setControllers(curriculumCtrl, detailsController, pricingController);
        curriculumCtrl.setControllers(targetStudentCtrl, detailsController, pricingController);
        pricingController.setControllers(targetStudentCtrl, curriculumCtrl, detailsController);
//        lsPane.removeHeader();
//        lsPane.removeFooter();
        
        MenuBar menu = new MenuBar();
        topMenu = menu.getMenuContainer();
        lsPane.setHeader(topMenu);

        lsPane.addContent(targetStudentPane, SVG.TARGET, PageName.CreateCourseTargetStudent, () -> targetStudentCtrl.isPassedCondition());
        lsPane.addContent(videoLectureTipsPane, SVG.VIDEO_CAMERA, PageName.CreateCourseRecordVideo);
        lsPane.addContent(curriculumTipsPane, SVG.IDEA_BULB, PageName.CreateCourseCurriculumTips);
        lsPane.addContent(curriculumPane, SVG.TASK_LIST, PageName.CreateCourseCurriculum, () -> curriculumCtrl.isPassedCondition());
        lsPane.addContent(landingPagePane, SVG.PROFILE_DETAILS, PageName.CreateCourseLanding, () -> detailsController.isPassedCondition());
        lsPane.addContent(pricingPane, SVG.COST, PageName.CreateCoursePricing, () -> pricingController.isPassedCondition());
        
        lsPane.setDefaultContent(targetStudentPane);
        container = lsPane.getRoot();
    }
    public AnchorPane getRootPane(){
        return container;
    }
    
    public boolean isTargetStudentPageComplete(){
        return true;
    }
    
    public void uploadToDB(){
        if(!isPassedCondition()) return;
        Course course = detailsController.uploadToDB();
        curriculumCtrl.uploadToDB(course);
        course.setOutcomes(targetStudentCtrl.getOutcomes());
        course.setPrerequisitive(targetStudentCtrl.getPrerequisitives());
        targetStudentCtrl.uploadProperties(course); //new Course(3));
    }

    private boolean isPassedCondition() {
        return targetStudentCtrl.isPassedCondition() 
              && curriculumCtrl.isPassedCondition()
              && detailsController.isPassedCondition()
              && pricingController.isPassedCondition();
    }
}
