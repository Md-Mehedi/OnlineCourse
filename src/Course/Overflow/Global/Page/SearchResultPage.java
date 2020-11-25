

package Course.Overflow.Global.Page;

import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Layout.PageByPageLayoutController;
import Course.Overflow.Global.ToolKit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Md Mehedi Hasan
 */
public class SearchResultPage extends Page{

    private final HBox container;
    private VBox courseBoxContainer;
    private VBox filterContainer;
    private FXMLLoader loader;
    private VBox checkedFilterContainer;
    private ArrayList<CheckBox> chceckedCBList; 
    private ArrayList<HBox> chceckedNameBoxList; 
    private ArrayList<VBox> subFilterVBoxList; 
    private PageByPageLayoutController courseBoxesCtrl;
    
    
    public SearchResultPage(){
        super(PageName.SearchResult);
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        chceckedCBList = new ArrayList<>();
        chceckedNameBoxList = new ArrayList<>();
        subFilterVBoxList = new ArrayList<>();
        
        courseBoxContainer = new VBox();
        filterContainer = new VBox();
        container = new HBox(filterContainer, courseBoxContainer);
        root.getChildren().add(container);
        ToolKit.setAnchor(container, 20, 0, 20, 0);
        
        root.getStyleClass().add("searchResultPageContainer");
        courseBoxContainer.getStyleClass().add("courseBoxContainer");
        filterContainer.getStyleClass().add("filterContainer");
        
        readyTheCheckedFilterContainer();
        readyCourseBoxes();
        readyFilterList();        
    }
    
    private void readyTheCheckedFilterContainer() {
        Label label = new Label("Courses are showing for these filter");
        label.getStyleClass().add("title7");
        
        Region region = new Region();
        region.setPrefHeight(15);
        
        checkedFilterContainer = new VBox(label,region);
        checkedFilterContainer.getStyleClass().add("checkedListVBox");
    }
    
    private void readyCourseBoxes() {
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.LAYOUT_LOCATION + "/PageByPageLayout.fxml"));
            courseBoxContainer.getChildren().add(loader.load());
            courseBoxesCtrl = loader.<PageByPageLayoutController>getController();
            courseBoxesCtrl.setUpPage(new ArrayList<>(), PageByPageLayoutController.BoxViewType.ListView);
        } catch (IOException ex) {
            Logger.getLogger(SearchResultPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void readyFilterList() {
        for(int i=0; i<10; i++){
            Label label = new Label("Filter no "+i);
            
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_DOUBLE_DOWN);
            icon.setSize("30");
            
            HBox box = new HBox(label,icon);
            box.getStyleClass().add("filterLabel");
            
            VBox subFilterContainer = new VBox(box);
            filterContainer.getChildren().add(subFilterContainer);
            
            createSubFilterList(i);
            
            addListenerToShowSubFilter(i, box, icon, subFilterContainer);
        }
    }
    
    private void createSubFilterList(int idx){
        VBox box = new VBox();
        box.getStyleClass().add("subFilterContainer");

        for(int i=0; i<5; i++){
            CheckBox cb = new CheckBox("Sub label "+(i+1));
            box.getChildren().add(cb);
            setActionForCheckbox(cb);
        }
        
        subFilterVBoxList.add(idx, box);
    }

    private void addListenerToShowSubFilter(int idx, HBox nameContainer, FontAwesomeIconView icon, VBox container) {
        nameContainer.setOnMouseClicked((event) -> {
            if(icon.getGlyphName()=="ANGLE_DOUBLE_DOWN"){
                icon.setGlyphName("ANGLE_DOUBLE_UP");
                container.getChildren().add(subFilterVBoxList.get(idx));
            }
            else {
                icon.setGlyphName("ANGLE_DOUBLE_DOWN");
                container.getChildren().remove(subFilterVBoxList.get(idx));
            }
        });
    }

    private void setActionForCheckbox(CheckBox cb) {
        cb.setOnAction((event) -> {
            HBox box = new HBox();
            if(cb.isSelected()) {
                Text text = new Text(cb.getText());
                
                Region region = new Region();
                HBox.setHgrow(region, Priority.ALWAYS);
                
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("20");
                
                box.getChildren().addAll(text, region, icon);
                box.getStyleClass().add("checkedListHbox");
                
                checkedFilterContainer.getChildren().add(box);
                chceckedCBList.add(cb);
                chceckedNameBoxList.add(box);
                
                if(!filterContainer.getChildren().contains(checkedFilterContainer)){
                    filterContainer.getChildren().add(0,checkedFilterContainer);
                }
                
                box.setOnMouseClicked((event2) -> {
                    cb.setSelected(false);
                    removeFilter(cb);
                });
            }
            else {
                removeFilter(cb);
            }
        });
    }

    private void removeFilter(CheckBox cb) {
        int idx = chceckedCBList.indexOf(cb);
        checkedFilterContainer.getChildren().remove(
              chceckedNameBoxList.get(idx)
        );
        chceckedCBList.remove(idx);
        chceckedNameBoxList.remove(idx);
        if(chceckedNameBoxList.size()==0){
            filterContainer.getChildren().remove(checkedFilterContainer);
        }
    }

}
