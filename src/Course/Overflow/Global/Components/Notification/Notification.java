/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components.Notification;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Notification {

    public enum NotificationType {
        MESSAGE,
        COMMENT,
        FAQ,
        COURSE_BOUGHT
    }

    private NotificationType type;
    private AnchorPane pane;

    public Notification(NotificationType type) {
        this.type = type;
        if(type == NotificationType.MESSAGE){
            messageText("Md. Mehedi Hasan");
        } else if(type == NotificationType.COMMENT){
            makeNotificationWithPicture("Hello");
        } else if(type == NotificationType.COURSE_BOUGHT){
            courseBought("Md. Aman", "SQL : Database Design");
        }
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
        container.getChildren().addAll(t1,t2,t3,t4);
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
    
    public AnchorPane getPane(){
        
        return pane;
    }
}
