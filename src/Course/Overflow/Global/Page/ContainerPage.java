/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Global.Components.HeaderController;
import Course.Overflow.Global.Components.Notification.NotificationView;
import Course.Overflow.Global.Components.RightMenuPopOverController;
import Course.Overflow.Global.Components.TopMenuBar.MenuBar;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.PageByPageLayoutController;
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

    
    private PageName curPage;
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
    private Page page;
    private static int idx;
    
    public enum PageName{
        Home,
        Course,
        TeacherDetails,
        MyCourse,
        Wishlist,
        PurchaseHistory,
        SearchResult,
        Messenger,
        FAQ,
        Review,
        Anouncement
        ;
    }

    public ContainerPage() {
        pages = new ArrayList<>();
        pageIdx = -1;
        
        verticalBox = new VBox();
        wrapper = new AnchorPane(verticalBox);
        scroll = new ScrollPane(wrapper);
        container = new AnchorPane(scroll);
        ToolKit.setAnchor(verticalBox, 0, 0, 0, 0);
        ToolKit.setAnchor(scroll, 0, 0, 0, 0);        
        
        createLayout();
        loadPage(PageName.Messenger);
    }
    
    public AnchorPane getContainer(){
        return container;
    }
    
    public void loadPage(Page page){
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
    
    
    public void createLayout(){
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/Header.fxml"));
            header = loader.load();
            headerCtrl = loader.<HeaderController>getController();
            GLOBAL.HEADER = headerCtrl;
            verticalBox.getChildren().add(header);
            
            menuBarCtrl = new MenuBar();
            menuBar = menuBarCtrl.getMenuContainer();
            GLOBAL.TOP_MENU_BAR = menuBarCtrl;
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
    }

    public static void loadFromHistory() {
        verticalBox.getChildren().remove(idx);
        verticalBox.getChildren().add(idx,pages.get(pageIdx).getRoot());
        if(pageIdx==0) headerCtrl.getLeftArrow().setOpacity(0.1);
        else headerCtrl.getLeftArrow().setOpacity(1);
        if(pageIdx==pages.size()-1) headerCtrl.getRightArrow().setOpacity(0.1);
        else headerCtrl.getRightArrow().setOpacity(1);
    }
    
    public void loadPage(PageName pageName){
        if(curPage == pageName) return;
        
        switch(pageName){
            case Home:  
                if(curPage != PageName.Home){
                    page = new Homepage();
                }
                break;
            case Course: page = new CoursePage(); break;
            case TeacherDetails: page = new TeacherDetailsPage(); break;
            case MyCourse: page = new CourseListShowPage("My courses"); break;
            case Wishlist: page = new CourseListShowPage("Wishlist"); break;
            case PurchaseHistory: page = new CourseListShowPage("Your purchase history", PageByPageLayoutController.CourseBoxShowType.Vertical); break;
            case SearchResult: page = new SearchResultPage(); break;
            case Messenger:
            case FAQ:
            case Review: 
            case Anouncement: page = new CommunicationPage(pageName);
            break;
        }
        loadPage(page);
        curPage = pageName;
 
    }
}
