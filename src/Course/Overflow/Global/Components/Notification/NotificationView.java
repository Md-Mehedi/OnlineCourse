
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
    private Integer unseenCount;
    

    public NotificationView() {
        container = new VBox();
        unseenCount = 0;
        ArrayList<Notification> list = Notification.getList();
        for(Notification notification : list){
            container.getChildren().add(notification.getPane());
            if(!notification.isSeen()) unseenCount++;
        }
        
        
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
    
    public Integer getUnseenCount(){
        return unseenCount;
    }
}
