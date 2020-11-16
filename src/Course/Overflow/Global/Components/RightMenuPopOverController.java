/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.ToolKit;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class RightMenuPopOverController implements Initializable {

    @FXML
    private HBox setting;
    @FXML
    private Circle imageCircle;
    @FXML
    private Label imageLabel;
    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private VBox labelContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        readyList();
        addDetails();
    }

    public void readyList() {
        labelContainer.getChildren().clear();
        labelContainer.getChildren().add(setting);
        setting.setOnMouseClicked((event) -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.ProfileSetting);
        });

        addLabel("My course", PageName.MyCourse);
        if (GLOBAL.ACCOUNT_TYPE == Person.AccountType.Student) {
            addLabel("Purchase history", PageName.PurchaseHistory);
            addLabel("Wishlist", PageName.Wishlist);
        } else if (GLOBAL.ACCOUNT_TYPE == Person.AccountType.Teacher) {
            addLabel("My students", PageName.EnrolledStudents);
            addLabel("Overview", PageName.Overview);
            addLabel("Create a course", PageName.CreateCourse);
        }
        addLabel("Message", PageName.Messenger);
        addLabel("FAQ", PageName.FAQ);
        addLabel("Account setting", PageName.ProfileSetting);
        addLabel("Sign out", PageName.Login, ()-> {
            GLOBAL.ACCOUNT_TYPE = null;
            GLOBAL.STUDENT = null;
            GLOBAL.TEACHER = null;
            return true;
        });
    }

    private void addLabel(String labelName, PageName pageName) {
        addLabel(labelName, pageName, () -> true);
    }

    private void addLabel(String labelName, PageName pageName, Callable<Boolean> callBeforeLoad) {
        Label label = new Label(labelName);
        label.setOnMouseClicked((event) -> {
            try {
                callBeforeLoad.call();
                GLOBAL.PAGE_CTRL.loadPage(pageName);
            } catch (Exception ex) {
                Logger.getLogger(RightMenuPopOverController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        labelContainer.getChildren().add(label);
    }

    @FXML
    private void mouseExited(MouseEvent event) {
    }

    @FXML
    private void mouseEntered(MouseEvent event) {
    }

    @FXML
    private void mouseClicked(MouseEvent event) {
    }

    private void addDetails() {
        Person person = ToolKit.getCurrentPerson();
        username.setText(person.getUsername());
        email.setText(person.getEmail());
        if (person.getPhoto() != null) {
            Image image = new Image(new File(ToolKit.makeAbsoluteLocation(person.getPhoto().getContent())).toURI().toString());
            imageCircle.setFill(new ImagePattern(image));
            imageLabel.setText("");
        } else {
            imageLabel.setText(ToolKit.getCurrentPerson().getShortName());
        }
    }
}
