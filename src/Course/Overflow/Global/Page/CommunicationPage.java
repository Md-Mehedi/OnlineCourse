/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Global.Communication.Anouncement;
import Course.Overflow.Global.Communication.FAQ;
import Course.Overflow.Global.Communication.MessagePage;
import Course.Overflow.Global.Communication.Reviews;
import Course.Overflow.Global.Customize.SVG;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.LeftSlidingPane;
import Course.Overflow.Global.Layout.LeftSlidingPane.Type;
import Course.Overflow.Global.Page.ContainerPage.PageName;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Md Mehedi Hasan
 */
public class CommunicationPage extends Page{
    private AnchorPane page;
    private LeftSlidingPane slidingPaneCtrl;
    private FXMLLoader loader;
    private AnchorPane messengerPane;
    private MessagePage messengerPageCtrl;
    private FAQ faqCtrl;
    private AnchorPane faqPane;
    private Reviews reviewsCtrl;
    private AnchorPane reviewsPane;
    private Anouncement anouncementCtrl;
    private AnchorPane anouncementPane;
    
    public CommunicationPage(){
        slidingPaneCtrl = new LeftSlidingPane(Type.NO_HOVER);
        slidingPaneCtrl.removeHeader();
        slidingPaneCtrl.removeFooter();
        slidingPaneCtrl.removeTitleBar();
        root.getChildren().add(slidingPaneCtrl.getRoot());
        
        addMessengerPage();
        addFAQPage();
        addReviews();
        addAnouncement();
        slidingPaneCtrl.setDefaultContent(faqPane);
    }
    
    public CommunicationPage(PageName pageName){
        this();
        switch(pageName){
            case Messenger: slidingPaneCtrl.setPage(messengerPane); break;
            case FAQ: slidingPaneCtrl.setPage(faqPane); break;
            case Review: slidingPaneCtrl.setPage(reviewsPane); break;
            case Anouncement: slidingPaneCtrl.setPage(anouncementPane); break;
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
        slidingPaneCtrl.addContent(messengerPane, SVG.MESSAGE, "Messenger");
        Platform.runLater(()->{
//            messengerPageCtrl.setPrefHeight(slidingPaneCtrl.getHeight());
            messengerPageCtrl.setPrefHeight(GLOBAL.HEIGHT - GLOBAL.HEADER.getRoot().getPrefHeight() - GLOBAL.TOP_MENU_BAR.getHeight()-10);
            System.out.println(messengerPageCtrl.getRoot().getPrefHeight());
        });
    }

    private void addFAQPage() {
        faqCtrl = new FAQ();
        faqPane = faqCtrl.getRoot();
        slidingPaneCtrl.addContent(faqPane, SVG.FAQ, "FAQ");
    }

    private void addReviews() {
        reviewsCtrl = new Reviews();
        reviewsPane = reviewsCtrl.getRoot();
        slidingPaneCtrl.addContent(reviewsPane, SVG.REVIEW, "Reviews");
    }

    private void addAnouncement() {
        anouncementCtrl = new Anouncement();
        anouncementPane = anouncementCtrl.getRoot();
        slidingPaneCtrl.addContent(anouncementPane, SVG.ANOUNCEMENT, "Anouncement");
    }
}
