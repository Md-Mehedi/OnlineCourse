/*
 * To change this license header, choose License Headers in Project Properties.
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
public class ProfileSettingPage extends Page{
    private FXMLLoader loader;
    public ProfileSettingPage(){
        try {
            loader = new FXMLLoader(getClass().getResource(GLOBAL.COMPONENTS_LOCATION + "/ProfileSetting.fxml"));
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(TeacherDetailsPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
