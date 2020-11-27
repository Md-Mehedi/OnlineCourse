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
    , PersonDetails
    , MyCourse("My Course")
    , Wishlist("Wishlist")
    , PurchaseHistory("Purchase History")
    , SearchResult
    , Messenger("Messenger")
    , FAQ("FAQ")
    , Review("Review")
    , Anouncement("Anouncement")
    , Overview("Overview")
    , EnrolledStudents("Enrolled students")
    , CreateCourse("Create a course")
    , CreateCourseTargetStudent("Target your student")
    , CreateCourseRecordVideo("Record your video")
    , CreateCourseCurriculumTips("Ready for course")
    , CreateCourseCurriculum("Set your curriculum")
    , CreateCourseLanding("Landing page")
    , CreateCoursePricing("Set course price")
    , Login()
    , Signup()
    , ProfileSetting()
    , Blank("Blank Page")
    , AdminPanel
    , AdminShowPerson("Users")
    , AdminMaintainCategory("Category")
    , AdminMaintainCountry("Country")
    , AdminMaintainDesignation("Designation")
    , AdminMaintainEduStatus("Educational Status")
    , AdminMaintainLanguage("Language")
    , AdminCourseList("Courses")
    , ForgetPassword()
    ;
    public String name;

    private PageName(String name) {
        this.name = name;
    }
    private PageName() {
        name = "";
    }
    public String getName(){
        return name;
    }
    
}
