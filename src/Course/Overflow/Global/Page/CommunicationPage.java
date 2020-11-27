/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Global.Communication.Anouncement;
import Course.Overflow.Global.Communication.FAQs;
import Course.Overflow.Global.Communication.MessagePage;
import Course.Overflow.Global.Customize.SVG;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.LeftSlidingPane;
import Course.Overflow.Global.Layout.LeftSlidingPane.Type;
import Course.Overflow.Teacher.EnrolledStudentsView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Md Mehedi Hasan
 */
public class CommunicationPage extends Page{
    private static LeftSlidingPane slidingPaneCtrl;
    private FXMLLoader loader;
    private AnchorPane messengerPane;
    private MessagePage messengerPageCtrl;
    private FAQs faqCtrl;
    private AnchorPane faqPane;
    private AnchorPane reviewsPane;
    private Anouncement anouncementCtrl;
    private AnchorPane anouncementPane;
    private AnchorPane overviewPane;
    private EnrolledStudentsView studentCtrl;
    private AnchorPane enrolledStudentPane;
    private PageName pageName;
    private ReviewPage reviewsCtrl;
        
    public CommunicationPage(PageName pageName){
        super(pageName);
        slidingPaneCtrl = new LeftSlidingPane(Type.NO_HOVER);
        slidingPaneCtrl.removeHeader();
        slidingPaneCtrl.removeFooter();
        slidingPaneCtrl.removeTitleBar();
        root.getChildren().add(slidingPaneCtrl.getRoot());
        
        addOverviewPage();
        addStudentPage();
        addMessengerPage();
        addFAQPage();
        addReviews();
        addAnouncement();
        
        this.pageName = pageName;
        switch(pageName){
            case Messenger: slidingPaneCtrl.setPage(messengerPane); break;
            case FAQ: slidingPaneCtrl.setPage(faqPane); break;
            case Review: slidingPaneCtrl.setPage(reviewsPane); break;
            case Anouncement: slidingPaneCtrl.setPage(anouncementPane); break;
            case EnrolledStudents: slidingPaneCtrl.setPage(enrolledStudentPane); break;
        }
    }

    private void addMessengerPage() {
        /* 
         * To setup MessagePage width we need to change 3 width
         * 1. Scrollpane width from BorderPane.fxml
         * 2. pref-width from Layout.css
         * 3. pref-width from Communication.css for ChatHeadBox.
         */
        messengerPageCtrl = new MessagePage();
        messengerPane = messengerPageCtrl.getRoot();
        slidingPaneCtrl.addContent(messengerPane, SVG.MESSAGE, PageName.Messenger);
        Platform.runLater(()->{
            messengerPageCtrl.setPrefHeight(GLOBAL.HEIGHT - GLOBAL.HEADER.getRoot().getPrefHeight() - GLOBAL.TOP_MENU_BAR.getHeight()-10);
            System.out.println(messengerPageCtrl.getRoot().getPrefHeight());
        });
    }

    private void addFAQPage() {
        faqCtrl = new FAQs();
        faqPane = faqCtrl.getRoot();
        slidingPaneCtrl.addContent(faqPane, SVG.FAQ, PageName.FAQ);
    }

    private void addReviews() {
        reviewsCtrl = new ReviewPage();
        reviewsPane = reviewsCtrl.getRoot();
        slidingPaneCtrl.addContent(reviewsPane, SVG.REVIEW, PageName.Review);
    }

    private void addAnouncement() {
        anouncementCtrl = new Anouncement();
        anouncementPane = anouncementCtrl.getRoot();
        slidingPaneCtrl.addContent(anouncementPane, SVG.ANOUNCEMENT, PageName.Anouncement);
    }
    
    public LeftSlidingPane getSlidingPaneCtrl(){
        return slidingPaneCtrl;
    }

    private void addOverviewPage() {
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.TEACHER_LOCATION + "/Overview.fxml"));
            overviewPane = loader.load();
            slidingPaneCtrl.addContent(overviewPane, SVG.OVERVIEW, PageName.Overview);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addStudentPage(){
        studentCtrl = new EnrolledStudentsView();
        enrolledStudentPane = studentCtrl.getRoot();
        slidingPaneCtrl.addContent(enrolledStudentPane, SVG.ENROLL_STUDENT, PageName.EnrolledStudents);
        
    }
}
