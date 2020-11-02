/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Page;

/**
 *
 * @author Md Mehedi Hasan
 */
public enum PageName {
      Home
    , Course
    , TeacherDetails
    , MyCourse
    , Wishlist
    , PurchaseHistory
    , SearchResult
    , Messenger("Messenger")
    , FAQ("FAQ")
    , Review("Review")
    , Anouncement("Anouncement")
    , Overview("Overview")
    , EnrolledStudents("Enrolled students")
    , CreateCourseTargetStudent("Target your student")
    , CreateCourseRecordVideo("Record your video")
    , CreateCourseCurriculumTips("Ready for course")
    , CreateCourseCurriculum("Set your curriculum")
    , CreateCourseLanding("Landing page")
    , CreateCoursePricing("Set course price")
    , Login()
    , Signup()
    , ProfileSetting()
    , FrogetPassword()
    ;
    public String name;

    private PageName(String name) {
        this.name = name;
    }
    private PageName() {
        name = "";
    }
    
}
