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
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.LeftSlidingPane;
import Course.Overflow.Global.Layout.LeftSlidingPane.Type;
import Course.Overflow.Global.ToolKit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
        slidingPaneCtrl.setDefaultContent(messengerPane);
        
        
        slidingPaneCtrl.setHeader(new AnchorPane(new Label("klfjlsd fljas lksjf lsjfk lkfjksfj lksjf sjfk sjdklfj ")));
    }

    private void addMessengerPage() {
        messengerPageCtrl = new MessagePage();
        messengerPane = messengerPageCtrl.getRoot();
        slidingPaneCtrl.addContent(messengerPane, FontAwesomeIcon.ANGLE_DOUBLE_UP, "Messenger");
        Platform.runLater(()->{
//            messengerPageCtrl.setPrefHeight(slidingPaneCtrl.getHeight());
            messengerPageCtrl.setPrefHeight(GLOBAL.HEIGHT - GLOBAL.HEADER.getRoot().getPrefHeight() - GLOBAL.TOP_MENU_BAR.getHeight()-10);
            System.out.println(messengerPageCtrl.getRoot().getPrefHeight());
        });
    }

    private void addFAQPage() {
        VBox box = new VBox();
        faqPane = new AnchorPane();
        box.setStyle("-fx-background-color: yellow;");
        box.setAlignment(Pos.CENTER);
        ToolKit.setAnchor(box, 0, 0, 0, 0);
        for(int i=0; i<10; i++){
            faqCtrl = new FAQ();
            //box.getChildren().add(new Label("lasjflksdj flsjf dsljf "+(i+1)));
            box.getChildren().add(faqCtrl.getRoot());
        }
        faqPane.getChildren().add(box);
        slidingPaneCtrl.addContent(faqPane, FontAwesomeIcon.ANGLE_DOUBLE_UP, "FAQ");
    }

    private void addReviews() {
        reviewsCtrl = new Reviews();
        reviewsPane = reviewsCtrl.getRoot();
        slidingPaneCtrl.addContent(reviewsPane, FontAwesomeIcon.ANGLE_DOUBLE_UP, "Reviews");
    }

    private void addAnouncement() {
        anouncementCtrl = new Anouncement();
        anouncementPane = anouncementCtrl.getRoot();
        slidingPaneCtrl.addContent(anouncementPane, FontAwesomeIcon.ANGLE_DOUBLE_UP, "Anouncement");
    }
}
