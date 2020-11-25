/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Global.Person;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PersonSmallViewController implements Initializable {

    @FXML
    private ImageView photo;
    @FXML
    private Label name;
    @FXML
    private Label desc;
    private Person person;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void loadData(Person person) {
        this.person = person;
        photo.setImage(new Image(person.getPhoto().getContent()));
        name.setText(person.getFullName());
        desc.setText(person.getAbout());
    }
    
}
