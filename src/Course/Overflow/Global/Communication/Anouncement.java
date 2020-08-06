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
public class Anouncement extends Communication{
    VBox anounContainer;
    
    public Anouncement(){
        anounContainer = bottomContainer;
        
        addAAnouncement();
    }
    
    public void addAAnouncement(){
        Label title = new Label("Title... lksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr l");
        title.getStyleClass().add("title5");
        Label anounce = new Label("Title... lksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr llksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr l lksd sjf l;sjf kljf lkjr klje weklrj ewrjlewjr lkwejrk lkwejr krj ewjr jweljr l");
        anounContainer.getChildren().add(new VBox(title, anounce));
    }
}
