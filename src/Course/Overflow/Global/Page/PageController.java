
package Course.Overflow.Global.Page;

import Course.Overflow.Admin.AdminPanel;
import Course.Overflow.Global.Components.HeaderController;
import Course.Overflow.Global.Components.Notification.NotificationView;
import Course.Overflow.Global.Components.RightMenuPopOverController;
import Course.Overflow.Global.Components.TopMenuBar.MenuBar;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.CreateCourse.CreateCourse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class PageController {

    public static ArrayList<Page> pages;
    public static int pageIdx;

    public static PageName curPage;
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

//    public PageController() {
//        this(PageName.Home);
//    }
    public PageController(){}
    public PageController(PageName pageName) {
        pages = new ArrayList<>();
        pageIdx = -1;

        container = new AnchorPane();
        scroll = new ScrollPane();

        if (!isLoadingWithoutLayout(pageName)) {
            createLayout();
        }
        loadPage(pageName);
    }

    public AnchorPane getContainer() {
        return container;
    }

    public void makePageHistory() {
        while (pages.size() - 1 > pageIdx && pageIdx != -1) {
            pages.remove(pageIdx + 1);
        }
        pages.add(page);
        pageIdx++;
        headerCtrl.getRightArrow().setOpacity(0.1);
        if (pageIdx != 0) {
            headerCtrl.getLeftArrow().setOpacity(1);
        }
    }

    public PageName getPreviousPageName() {
        return pages.get(pages.size() - 2).getPageName();
    }

    public void loadPage(Page page) {
        verticalBox.getChildren().remove(idx);
        verticalBox.getChildren().add(idx, page.getRoot());
        makePageHistory();
    }
    private boolean isLoadingWithoutLayout(PageName pagename)
    {
        switch(pagename)
        {
            case Login : 
            case Signup: 
            case FrogetPassword: return true;
        }
        return false;
    }

    public void createLayout() {
        try {
            verticalBox = new VBox();
            wrapper = new AnchorPane(verticalBox);
            Platform.runLater(()->{
                wrapper.setMaxWidth(GLOBAL.WIDTH);
            });
            scroll = new ScrollPane(wrapper);
            container.getChildren().clear();
            container.getChildren().add(scroll);
            ToolKit.setAnchor(verticalBox, 0, 0, 0, 0);
            ToolKit.setAnchor(scroll, 0, 0, 0, 0);

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
            verticalBox.getChildren().add(new Page(PageName.Blank).getRoot());

            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/RightMenuPopOver.fxml"));
            profilePane = loader.load();
            profileCtrl = loader.<RightMenuPopOverController>getController();
            wrapper.getChildren().add(profilePane);
            headerCtrl.setProfilePane(profilePane);

            notiCtrl = new NotificationView();
            noti = notiCtrl.getContainer();
            wrapper.getChildren().add(noti);
            headerCtrl.setNotiPane(noti);
        } catch (IOException ex) {
            Logger.getLogger(PageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadFromHistory() {
        verticalBox.getChildren().remove(idx);
        verticalBox.getChildren().add(idx, pages.get(pageIdx).getRoot());
        if (pageIdx == 0) {
            headerCtrl.getLeftArrow().setOpacity(0.1);
        } else {
            headerCtrl.getLeftArrow().setOpacity(1);
        }
        if (pageIdx == pages.size() - 1) {
            headerCtrl.getRightArrow().setOpacity(0.1);
        } else {
            headerCtrl.getRightArrow().setOpacity(1);
        }
        curPage = pages.get(pageIdx).pageName;
    }

    public void loadPage(PageName pageName) {
        if (curPage == pageName) {
            return;
        }
        if (!container.getChildren().contains(scroll) && !isLoadingWithoutLayout(pageName)) {
            System.out.println(pageName);
            createLayout();
        }

        switch (pageName) {
            case Home:
                page = new Homepage();
                break;
            case Course:
                page = new CoursePage();
                break;
            case PersonDetails:
                page = new PersonDetailsPage();
                break;
            case MyCourse:
            case Wishlist:
                page = new CourseListShowPage(pageName);
                break;
            case PurchaseHistory:
                if (GLOBAL.ACCOUNT_TYPE == Person.AccountType.Student) {
                    page = new CourseListShowPage(pageName);
                } else if (GLOBAL.ACCOUNT_TYPE == Person.AccountType.Teacher) {
                    page = new PurchaseHistoryPage();
                }
                break;
            case SearchResult:
                page = new SearchResultPage();
                break;
            case Overview:
            case EnrolledStudents:
            case Messenger:
            case FAQ:
            case Review:
            case Anouncement:
                page = new CommunicationPage(pageName);
                break;
            case ProfileSetting:
                page = new ProfileSettingPage();
                break;
            case CreateCourse:
                page = new CreateCourse();
                break;
            case Login:
                loadFXML(GLOBAL.LOGIN_SIGNUP_LOCATION + "/Login.fxml");
                break;
            case Signup:
                loadFXML(GLOBAL.LOGIN_SIGNUP_LOCATION + "/Signup.fxml");
                break;
            case AdminPanel:
                page = new AdminPanel();
                break;
        }
        if (!isLoadingWithoutLayout(pageName)) {
            loadPage(page);
        }
        curPage = pageName;
    }
    
    public void loadPreviousPage(){
        if(idx <= 0) loadPage(PageName.Home);
        else{
            page = new Page(pages.get(idx-1));
            loadPage(page);
        }
    }

    public Object loadFXML(String fxmlName) {
        try {
            container.getChildren().clear();
            loader = new FXMLLoader(getClass().getResource(fxmlName));
            AnchorPane pane = loader.load();
            container.getChildren().add(pane);
            return loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(PageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Page getPage(){
        return page;
    }
    
    public Object getController(){
        return page.getController();
    }
}
