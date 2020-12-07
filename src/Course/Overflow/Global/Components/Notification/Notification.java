package Course.Overflow.Global.Components.Notification;

import Course.Overflow.Course.Course;
import Course.Overflow.Course.Show.CourseDetailsController;
import Course.Overflow.DB;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.PersonPreviewController;
import Course.Overflow.Global.ToolKit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Notification {

    private void makeRegistrationPane() {
        String s = "Md. Mehedi Hasan registered.";
        TextFlow container = new TextFlow();
        Person person = new Person(from_id);
        Text t1 = makeBoldText(new Person(from_id).getFullName());
        Text t2 = new Text(" regisered.");
        container.getChildren().addAll(t1, t2);
        pane.getChildren().add(container);
        pane.setOnMouseClicked((event) -> {
            GLOBAL.HEADER.decreaseNotification();
            seenUpdate();
            GLOBAL.PAGE_CTRL.loadPage(PageName.PersonDetails);
            PersonPreviewController ctrl = (PersonPreviewController) GLOBAL.PAGE_CTRL.getController();
            ctrl.loadData(person);
        });
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    private void makeCourseUploadPane() {
        String s = "Md. Mehedi Hasan uploaded a course.";
        TextFlow container = new TextFlow();
        Text t1 = makeBoldText(new Person(from_id).getFullName());
        Text t2 = new Text(" uploaded a course.");
        container.getChildren().addAll(t1, t2);
        pane.getChildren().add(container);
        pane.setOnMouseClicked((event) -> {
            GLOBAL.HEADER.decreaseNotification();
            seenUpdate();
            GLOBAL.PAGE_CTRL.loadPage(PageName.Course);
            CourseDetailsController ctrl = (CourseDetailsController) GLOBAL.PAGE_CTRL.getController();
            ctrl.loadData(new Course(courseId));
        });
    }

    private void makeReviewPane() {
        String s = "Md. Mehedi Hasan reviewed courseName.";
        TextFlow container = new TextFlow();
        Text t1 = makeBoldText(new Person(from_id).getFullName());
        Text t2 = new Text(" reviewed ");
        Text t3 = makeBoldText(new Course(courseId).getTitle());
        container.getChildren().addAll(t1, t2, t3);
        pane.getChildren().add(container);
        pane.setOnMouseClicked((event) -> {
            GLOBAL.HEADER.decreaseNotification();
            seenUpdate();
            GLOBAL.PAGE_CTRL.loadPage(PageName.Review);
            CourseDetailsController ctrl = (CourseDetailsController) GLOBAL.PAGE_CTRL.getController();
            ctrl.loadData(new Course(courseId));
        });
    }

    private void makeFaqQuestionPane() {
        String s = "Md. Mehedi Hasan reviewed courseName.";
        TextFlow container = new TextFlow();
        Text t1 = makeBoldText(new Person(from_id).getFullName());
        Text t2 = new Text(" asked a question about ");
        Text t3 = makeBoldText(new Course(courseId).getTitle());
        container.getChildren().addAll(t1, t2, t3);
        pane = new AnchorPane(container);
        pane.setOnMouseClicked((event) -> {
            GLOBAL.HEADER.decreaseNotification();
            seenUpdate();
            GLOBAL.PAGE_CTRL.loadPage(PageName.FAQ);
//            CourseDetailsController ctrl = (CourseDetailsController) GLOBAL.PAGE_CTRL.getController();
//            ctrl.loadData(new Course(courseId));
        });
    }

    private void makeFaqAnswerPane() {
        String s = "Md. Mehedi Hasan answered your question in courseName.";
        TextFlow container = new TextFlow();
        Text t1 = makeBoldText(new Person(from_id).getFullName());
        Text t2 = new Text(" answered your question in ");
        Text t3 = makeBoldText(new Course(courseId).getTitle());
        container.getChildren().addAll(t1, t2, t3);
        pane.getChildren().add(container);
        pane.setOnMouseClicked((event) -> {
            GLOBAL.HEADER.decreaseNotification();
            seenUpdate();
            GLOBAL.PAGE_CTRL.loadPage(PageName.FAQ);
//            CourseDetailsController ctrl = (CourseDetailsController) GLOBAL.PAGE_CTRL.getController();
//            ctrl.loadData(new Course(courseId));
        });
    }

    private void seenUpdate() {
        DB.execute("UPDATE NOTIFICATION SET SEEN = 'T' WHERE ID = #", id.toString());
    }

    public enum NotificationType {
        REVIEW,
        REGISTRATION,
        FAQQUESTION,
        FAQANSWER,
        COURSEUPLOAD;
    }

    private Integer id;
    private Integer courseId;
    private String userId;
    private String from_id;
    private Date time;
    private boolean seen;
    private NotificationType type;
    private AnchorPane pane;

    public static ArrayList<Notification> getList() {
        try {
            ArrayList<Notification> list = new ArrayList<Notification>();
            ResultSet rs = DB.executeQuery("SELECT * FROM NOTIFICATION WHERE USER_ID = '#' ORDER BY TIME DESC", ToolKit.getCurrentPerson().getUsername());
            while (rs.next()) {
                list.add(new Notification(rs.getInt("ID")));
            }
            rs.close();
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void setRegistration(String from_id) {
        String sql = "INSERT INTO NOTIFICATION (USER_ID,FROM_ID,TIME,SEEN,COURSE_ID,TYPE)"
                + "VALUES('shammya','#',#,'F',NULL,'REGISTRATION')";
        DB.execute(sql, from_id, ToolKit.getCurTimeDB());
    }

    public static void setCourseUpload(Course course) {
        String sql = "INSERT INTO NOTIFICATION (USER_ID, FROM_ID, TIME, SEEN, COURSE_ID, TYPE)"
                + "VALUES('shammya','#',#,'F',#,'COURSEUPLOAD')";
        DB.execute(sql, course.getTeacher().getUsername(), ToolKit.getCurTimeDB(), course.getId().toString());
    }

    public static void setFaqQuestion(Course course, String from_id) {
        String sql = "INSERT INTO NOTIFICATION (USER_ID,FROM_ID,TIME,SEEN,COURSE_ID,TYPE)"
                + "VALUES('#','#',#,'F',#,'FAQQUESTION')";
        DB.execute(sql, course.getTeacher().getUsername(), from_id, ToolKit.getCurTimeDB(), course.getId().toString());
    }

    public static void setFaqAnswer(Course course, String from_id) {
        String sql = "INSERT INTO NOTIFICATION (USER_ID,FROM_ID,TIME,SEEN,COURSE_ID,TYPE)"
                + "VALUES('#','#',#,'F',#,'FAQANSWER')";
        DB.execute(sql, from_id, course.getTeacher().getUsername(), ToolKit.getCurTimeDB(), course.getId().toString());
    }

    public static void setReview(Course course, String from_id) {
        String sql = "INSERT INTO NOTIFICATION (USER_ID,FROM_ID,TIME,SEEN,COURSE_ID,TYPE)"
                + "VALUES('#','#',#,'F',#,'REVIEW')";
        DB.execute(sql, course.getTeacher().getUsername(), from_id, ToolKit.getCurTimeDB(), course.getId().toString());
    }

    public Notification(Integer id) {
        try {
            this.id = id;
            this.pane = new AnchorPane();
            pane.getStyleClass().add("notification");
            pane.getStyleClass().add("textNotification");
            ResultSet rs = DB.executeQuery("SELECT * FROM NOTIFICATION WHERE ID = #", id.toString());
            if (rs.next()) {
                this.courseId = rs.getInt("COURSE_ID");
                this.userId = rs.getString("USER_ID");
                this.from_id = rs.getString("FROM_ID");
                this.time = rs.getTimestamp("TIME");
                this.seen = rs.getString("SEEN").equals("T") ? true : false;
                this.type = NotificationType.valueOf(rs.getString("TYPE"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch (type) {
            case REGISTRATION:
                makeRegistrationPane();
                break;
            case COURSEUPLOAD:
                makeCourseUploadPane();
                break;
            case REVIEW:
                makeReviewPane();
                break;
            case FAQQUESTION:
                makeFaqQuestionPane();
                break;
            case FAQANSWER:
                makeFaqAnswerPane();
                break;
        }
    }

    public Notification() {
    }

    public void messageText(String sender) {
        TextFlow container = new TextFlow();
        Text t1 = makeBoldText(sender);
        Text t2 = new Text(" send you a message.");
        container.getChildren().addAll(t1, t2);
        pane = new AnchorPane(container);
        pane.getStyleClass().add("notification");
        pane.getStyleClass().add("textNotification");
    }

    public void courseBought(String buyer, String courseName) {
        TextFlow container = new TextFlow();
        Text t1 = makeBoldText(buyer);
        Text t2 = new Text(" buy your ");
        Text t3 = makeBoldText(courseName);
        Text t4 = new Text(" course.");
        container.getChildren().addAll(t1, t2, t3, t4);
        pane = new AnchorPane(container);
        pane.getStyleClass().add("notification");
        pane.getStyleClass().add("textNotification");
    }

    public void makeTextNotification(String text) {
        Text t = new Text(text);
        TextFlow textContainer = new TextFlow();
        textContainer.getChildren().add(t);

        pane = new AnchorPane(textContainer);
        ToolKit.setAnchor(textContainer, 0, 0, 0, 0);

        pane.getStyleClass().add("notification");
        pane.getStyleClass().add("textNotification");
    }

    public void makeNotificationWithPicture(String text) {
        Text label = new Text(text);
        ImageView image = new ImageView(new Image(GLOBAL.ICON_LOCATION + "/placeholder.png"));
        image.setFitWidth(170);
        image.setPreserveRatio(true);
        TextFlow textContainer = new TextFlow();
        textContainer.getChildren().addAll(new Text(text), new Text(text), new Text(text), makeBoldText(text));
        HBox box = new HBox(textContainer, image);

        pane = new AnchorPane(box);
        ToolKit.setAnchor(box, 0, 0, 0, 0);

        pane.getStyleClass().add("notification");
        pane.getStyleClass().add("picNotification");
        pane.getStyleClass().add("unread");
    }

    public Text makeBoldText(String text) {
        Text t = new Text(text);
        t.setStyle("-fx-font-weight: bold;");
        return t;
    }

    public AnchorPane getPane() {
        return pane;
    }
}
