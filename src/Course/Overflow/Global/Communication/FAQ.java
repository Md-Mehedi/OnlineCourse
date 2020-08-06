/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Communication;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author Md Mehedi Hasan
 */
public class FAQ extends Communication{
    VBox qaContainer;
    
    public FAQ(){
        qaContainer = bottomContainer;
        
        addAQA();
        addAQA();
    }
    
    private void addAQA(){
        Label q = new Label("Question sldkfjlksdj flksdjf lasjf s;lkfj ;lajs fljlskfj ljf lsjflksjdlfjlashfjkshfkljoiweroi??");
        q.getStyleClass().add("title5");
        Label a = new Label("Answer ajsfljsl fowo ojosa fweuo lsifu oiwer jzjoaiuf worijoif poiweir wir oiugoisugoa owroi...");
        
        qaContainer.getChildren().add(new VBox(q,a));
    }
}
