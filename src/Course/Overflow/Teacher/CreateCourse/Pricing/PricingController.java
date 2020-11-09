/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Teacher.CreateCourse.Pricing;

import Course.Overflow.Global.Customize.ToolTip;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Teacher.CreateCourse.CourseLandingPage.DetailsController;
import Course.Overflow.Teacher.CreateCourse.CreateCourse;
import Course.Overflow.Teacher.CreateCourse.Curriculum.CurriculumController;
import Course.Overflow.Teacher.CreateCourse.TargetStudentPage.TargetStudentPageController;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PricingController implements Initializable {

    @FXML
    private TextField price;
    @FXML
    private CheckBox offerCkBox;
    @FXML
    private HBox offerContainer;
    private CheckBox promoCkBox;
    @FXML
    private HBox promoContainer;

    private AnchorPane promoPane;
    private AnchorPane offerPane;
    @FXML
    private AnchorPane container;
    @FXML
    private Label pricingDetailLabel;
    @FXML
    private FontAwesomeIconView infoPrice;
    @FXML
    private JFXButton uploadBtn;
    private DetailsController detailsCtrl;
    private CurriculumController curriculumCtrl;
    private TargetStudentPageController targetStudentCtrl;
    private OfferController offerCtrl;
    private CreateCourse parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new ToolTip(MouseEvent.MOUSE_ENTERED, infoPrice, "If you want to give this course as free, please set the 'Course price' as 0.");
        addListener();
    }

    @FXML
    private void mouseExited(MouseEvent event) {
    }

    @FXML
    private void mouseEntered(MouseEvent event) {
    }

    @FXML
    private void mouseClicked(MouseEvent event) throws IOException {
        Object src = event.getSource();
        if (src == offerCkBox) {
            if (offerCkBox.isSelected()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_PRICING_LOCATION + "/Offer.fxml"));
                offerPane = loader.load();
                offerContainer.getChildren().add(offerPane);
                offerCtrl =  loader.<OfferController>getController();
                offerCtrl.setParent(this, container);
            } else {
                offerContainer.getChildren().removeAll(offerPane);
            }
        } else if (src == promoCkBox) {
            if (promoCkBox.isSelected()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(GLOBAL.COURSE_PRICING_LOCATION + "/Promocode.fxml"));
                promoPane = loader.load();
                promoContainer.getChildren().addAll(promoPane);
                loader.<PromocodeController>getController().setParent(this, container);

            } else {
                promoContainer.getChildren().removeAll(promoPane);
            }
        }
    }

    public void setControllers(TargetStudentPageController targetStudentCtrl, CurriculumController curriculumCtrl, DetailsController detailsController) {
        this.targetStudentCtrl = targetStudentCtrl;
        this.curriculumCtrl = curriculumCtrl;
        this.detailsCtrl = detailsController;
    }

    public Double getPrice() {
        return Double.parseDouble(price.getText());
    }

    public Double getOffer() {
        if(offerCtrl == null) return 0.0;
        return offerCtrl.getOffer();
    }

    private void addListener() {
        uploadBtn.setOnMouseClicked((event) -> {
            parent.uploadToDB();
        });
    }

    public void setParent(CreateCourse parent) {
        this.parent = parent;
    }

    public Boolean isPassedCondition() {
        if(price.getText().equals("")){
            ToolKit.showWarning("Please set a price of your course. Or set '0' if you want to give this course as free.");
            return false;
        }
        return true;
    }
}
