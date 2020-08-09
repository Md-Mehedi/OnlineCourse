/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Customize;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author Md Mehedi Hasan
 */
public abstract class MyFadeTransition {
    public FadeTransition transition;

    public abstract void setOnfinish();
    public MyFadeTransition(Duration duration, Node node, double toValue) {
        transition = new FadeTransition(duration, node);
        transition.setToValue(toValue);
        transition.setOnFinished((event) -> {
            setOnfinish();
        });
        transition.play();
    }    
}
