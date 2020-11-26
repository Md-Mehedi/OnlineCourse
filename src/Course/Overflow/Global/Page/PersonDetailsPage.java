/*
 * To change this license HEADER, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Page;

import Course.Overflow.Global.GLOBAL;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Md Mehedi Hasan
 */
public class PersonDetailsPage extends Page{

    private FXMLLoader loader;
    public PersonDetailsPage(){
        super(PageName.PersonDetails);
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.GLOBAL_LOCATION + "/PersonPreview.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(PersonDetailsPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
