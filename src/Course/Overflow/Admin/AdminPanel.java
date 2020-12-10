/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Admin;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.LeftSlidingPane;
import Course.Overflow.Global.Page.CommunicationPage;
import Course.Overflow.Global.Page.Page;
import Course.Overflow.Global.Page.PageName;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Asus
 */
public class AdminPanel extends Page{
    private final LeftSlidingPane slidingPaneCtrl;
    private FXMLLoader loader;
    private AnchorPane showPersonPane;
    private AnchorPane courseListPane;
    private AnchorPane countryList;
    private AnchorPane categoryList;
    private AnchorPane designationPane;
    private AnchorPane eduStatusePane;
    private AnchorPane languagePane;
    
    public AdminPanel(){
        super(PageName.Home);
        slidingPaneCtrl = new LeftSlidingPane(LeftSlidingPane.Type.NO_HOVER);
        slidingPaneCtrl.removeHeader();
        slidingPaneCtrl.removeFooter();
        slidingPaneCtrl.removeTitleBar();
        root.getChildren().add(slidingPaneCtrl.getRoot());
        slidingPaneCtrl.getRoot().getStyleClass().add("adminpanel");
        
        addShowPerson();
        addCourseList();
        addMaintainCategory();
        addMaintainCountry();
        addMaintainLanguage();
        addMaintainDesignation();
        addMaintainEduStatus();
        
        slidingPaneCtrl.setPage(showPersonPane);
    }
    
    private void addShowPerson() {
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.ADMIN_LOCATION + "/ShowPerson.fxml"));
            showPersonPane = (AnchorPane)loader.load();
            slidingPaneCtrl.addContent(showPersonPane, new Image(GLOBAL.ICON_LOCATION + "/person.png"), PageName.AdminShowPerson);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addCourseList() {
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.ADMIN_LOCATION + "/CourseList.fxml"));
            courseListPane = (AnchorPane)loader.load();
            slidingPaneCtrl.addContent(courseListPane, new Image(GLOBAL.ICON_LOCATION + "/list.png"), PageName.AdminCourseList);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addMaintainCountry() {
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.ADMIN_LOCATION + "/MaintainCountry.fxml"));
            countryList = (AnchorPane)loader.load();
            slidingPaneCtrl.addContent(countryList, new Image(GLOBAL.ICON_LOCATION + "/country.png"), PageName.AdminMaintainCountry);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addMaintainCategory() {
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.ADMIN_LOCATION + "/MaintainCategory.fxml"));
            categoryList = (AnchorPane)loader.load();
            slidingPaneCtrl.addContent(categoryList, new Image(GLOBAL.ICON_LOCATION + "/root.png"), PageName.AdminMaintainCategory);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addMaintainDesignation() {
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.ADMIN_LOCATION + "/MaintainDesignation.fxml"));
            designationPane = (AnchorPane)loader.load();
            slidingPaneCtrl.addContent(designationPane,new Image(GLOBAL.ICON_LOCATION + "/designation.png"), PageName.AdminMaintainDesignation);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addMaintainEduStatus() {
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.ADMIN_LOCATION + "/MaintainEduStatus.fxml"));
            eduStatusePane = (AnchorPane)loader.load();
            slidingPaneCtrl.addContent(eduStatusePane, new Image(GLOBAL.ICON_LOCATION + "/edustatus.png"), PageName.AdminMaintainEduStatus);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addMaintainLanguage() {
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.ADMIN_LOCATION + "/MaintainLanguage.fxml"));
            languagePane = (AnchorPane)loader.load();
            slidingPaneCtrl.addContent(languagePane, new Image(GLOBAL.ICON_LOCATION + "/language.png"), PageName.AdminMaintainLanguage);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
