/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Components.Notification;

import Course.Overflow.Global.GLOBAL;
import java.util.ArrayList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class NotificationView {
    AnchorPane root;
    VBox container;
    ArrayList<AnchorPane> notificationList;
    

    public NotificationView() {
        container = new VBox();
        container.getChildren().add(new Notification(Notification.NotificationType.COURSE_BOUGHT).getPane());
        container.getChildren().add(new Notification(Notification.NotificationType.COURSE_BOUGHT).getPane());
        container.getChildren().add(new Notification(Notification.NotificationType.MESSAGE).getPane());
        container.getChildren().add(new Notification(Notification.NotificationType.COURSE_BOUGHT).getPane());
        container.getChildren().add(new Notification(Notification.NotificationType.COMMENT).getPane());
        
        
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        scrollPane.getStylesheets().add(GLOBAL.COMPONENTS_LOCATION + "/Components.css");
        scrollPane.setId("notificationPane");
//        scrollPane.setPrefSize(350,500);
        root = new AnchorPane(scrollPane);

//        root.setOnMouseEntered((MouseEvent)->{
//            root.setVisible(true);
//            FadeTransition t = new FadeTransition(Duration.millis(500), root);
//            t.setFromValue(0);
//            t.setToValue(1);
//            t.play();
//        });
//        root.setOnMouseExited((MouseEvent)->{
//            FadeTransition t = new FadeTransition(Duration.millis(500), root);
//            t.setFromValue(1);
//            t.setToValue(0);
//            t.play();
//            t.setOnFinished((event) -> {
//                root.setVisible(false);
//            });
//        });
    }
    
    public AnchorPane getContainer() {
        return root;
    }
}
