/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Global.Components.HeaderController;
import Course.Overflow.Global.Components.Notification.NotificationView;
import Course.Overflow.Global.Components.RightMenuPopOverController;
import Course.Overflow.Global.Components.TopMenuBar.MenuBar;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class ContainerPage {
    public static ArrayList<Page> pages;
    public static int pageIdx;

    
    private Page curPage;
    private AnchorPane container;
    private ScrollPane scroll;
    private static VBox verticalBox;
    private FXMLLoader loader;
    private static HeaderController headerCtrl;
    private AnchorPane header;
    private AnchorPane footer;
    private NotificationView notiCtrl;
    private AnchorPane noti;
    private AnchorPane menuBar;
    private MenuBar menuBarCtrl;
    private AnchorPane wrapper;
    private RightMenuPopOverController profileCtrl;
    private AnchorPane profilePane;
    private Homepage page;
    private static int idx;

    public ContainerPage() {
        pages = new ArrayList<>();
        pageIdx = -1;
        
        verticalBox = new VBox();
        wrapper = new AnchorPane(verticalBox);
        scroll = new ScrollPane(wrapper);
        container = new AnchorPane(scroll);
        ToolKit.setAnchor(verticalBox, 0, 0, 0, 0);
        ToolKit.setAnchor(scroll, 0, 0, 0, 0);        
        
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/Header.fxml"));
            header = loader.load();
            headerCtrl = loader.<HeaderController>getController();
            verticalBox.getChildren().add(header);
            
            menuBarCtrl = new MenuBar();
            menuBar = menuBarCtrl.getMenuContainer();
            verticalBox.getChildren().add(menuBar);
            
            idx = verticalBox.getChildren().size();
            verticalBox.getChildren().add(new Page().getRoot());
            
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/RightMenuPopOver.fxml"));
            profilePane = loader.load();
            profileCtrl = loader.<RightMenuPopOverController>getController();
            wrapper.getChildren().add(profilePane);
            headerCtrl.setProfilePane(profilePane);
            
            notiCtrl = new NotificationView();
            noti = notiCtrl.getContainer();
            wrapper.getChildren().add(noti);
            headerCtrl.setNotiPane(noti);
        } catch (IOException ex) { Logger.getLogger(ContainerPage.class.getName()).log(Level.SEVERE, null, ex);}
        
        setSearchResultPage();
    }
    
    public AnchorPane getContainer(){
        return container;
    }
    
    private void setNewPage(Page page){
        verticalBox.getChildren().remove(idx);
        verticalBox.getChildren().add(idx, page.getRoot());
        
        while(pages.size()-1>pageIdx && pageIdx!=-1){
            pages.remove(pageIdx+1);
        }
        pages.add(page);
        pageIdx++;
        headerCtrl.getRightArrow().setOpacity(0.1);
        if(pageIdx!=0){
            headerCtrl.getLeftArrow().setOpacity(1);
        }
    }

    public static void loadPage() {
        verticalBox.getChildren().remove(idx);
        verticalBox.getChildren().add(idx,pages.get(pageIdx).getRoot());
        if(pageIdx==0) headerCtrl.getLeftArrow().setOpacity(0.1);
        else headerCtrl.getLeftArrow().setOpacity(1);
        if(pageIdx==pages.size()-1) headerCtrl.getRightArrow().setOpacity(0.1);
        else headerCtrl.getRightArrow().setOpacity(1);
    }
    
    public void setHomePage(){
        if(page == null){
            page = new Homepage();
        }
        setNewPage(page);
    }
    
    public void setCoursePage(){
        CoursePage page = new CoursePage();
        setNewPage(page);
    }
    
    public void setTeacherDetailsPage(){
        TeacherDetailsPage page = new TeacherDetailsPage();
        setNewPage(page);
    }
    
    public void setMyCoursesPage() {
        CourseListShowPage page = new CourseListShowPage("My courses");
        setNewPage(page);
    }
    
    public void setWishlistPage() {
        CourseListShowPage page = new CourseListShowPage("Wishlist");
        setNewPage(page);
    }
    
    public void setSearchResultPage() {
        SearchResultPage page = new SearchResultPage();
        setNewPage(page);
    }
}
