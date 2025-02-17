/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Global.Communication.MessagePage;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.PersonPreviewController;
import Course.Overflow.Global.ToolKit;
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
            GLOBAL.PAGE_CTRL.loadPage(PageName.PersonDetails);
            PersonPreviewController ctrl = (PersonPreviewController) GLOBAL.PAGE_CTRL.getController();
            ctrl.loadData(ToolKit.getCurrentPerson());
        });
        if(GLOBAL.ACCOUNT_TYPE == Person.AccountType.Admin){
            addLabel("Dashboard", PageName.AdminPanel);
            addLabel("Message", PageName.Messenger);
        }
        else if (GLOBAL.ACCOUNT_TYPE == Person.AccountType.Student) {
            addLabel("My course", PageName.MyCourse);
            addLabel("Purchase history", PageName.PurchaseHistory);
//            addLabel("Wishlist", PageName.Wishlist);
            addLabel("Message", PageName.Messenger);
            addLabel("My Review", PageName.Review);
            addLabel("FAQ", PageName.FAQ);
        } else if (GLOBAL.ACCOUNT_TYPE == Person.AccountType.Teacher) {
            addLabel("My course", PageName.MyCourse);
            addLabel("Overview", PageName.Overview);
            addLabel("My students", PageName.EnrolledStudents);
            addLabel("Purchase details", PageName.PurchaseHistory);
            addLabel("Create a course", PageName.CreateCourse);
            addLabel("Message", PageName.Messenger);
            addLabel("Student's Review", PageName.Review);
            addLabel("FAQ", PageName.FAQ);
        }
        addLabel("Account setting", PageName.ProfileSetting);
        addLabel("Sign out", PageName.Login, ()-> {
            GLOBAL.PAGE_CTRL.clearHistory();
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
                if(pageName == PageName.Messenger){
                    MessagePage ctrl = (MessagePage) GLOBAL.PAGE_CTRL.getPage();
                    if(ctrl.getSelectedBox() == null){
                        ctrl.showNoDataFound();
                    }
                }
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
            Image image = ToolKit.makeImage(person.getPhoto());
            imageCircle.setFill(new ImagePattern(image));
            imageLabel.setText("");
        } else {
//            imageLabel.setText(ToolKit.getCurrentPerson().getShortName());
        }
    }
}
