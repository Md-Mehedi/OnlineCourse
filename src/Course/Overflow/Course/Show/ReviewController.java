/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Course.Show;

import Course.Overflow.Course.Review;
import Course.Overflow.Global.ToolKit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ReviewController implements Initializable {

    @FXML
    private Circle imageCircle;
    @FXML
    private Label imageLabel;
    @FXML
    private Label name;
    @FXML
    private Rating ratingBox;
    @FXML
    private Label date;
    @FXML
    private Label review;
    @FXML
    private AnchorPane root;
    @FXML
    private VBox container;
    private Review reviewValue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ratingBox.setOnMouseClicked((event) -> {
            ratingBox.setRating(reviewValue.getRating().getValue());
        });
    }    
    
    public void setPrefWidth(double value){
        container.setMaxWidth(value);
    }

    public void loadData(Review review) {
        this.reviewValue = review;
        name.setText(review.getStudent().getFullName());
        this.review.setText(review.getText());
        this.ratingBox.setRating(review.getRating().getValue());
        date.setText(ToolKit.makeDateStructured(review.getDate(), "hh:mm aa - dd MMMMM, yyyy"));
        setPic(review.getStudent().getImage());
        setPicName(review.getStudent().getShortName());
    }

    private void setPic(Image image) {
        imageLabel.setVisible(true);
        imageCircle.setFill(new ImagePattern(image));
    }

    private void setPicName(String shortName) {
        imageLabel.setText(shortName);
        imageLabel.setVisible(false);
    }
}
