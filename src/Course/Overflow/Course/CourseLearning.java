/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course;

import java.util.ArrayList;

/**
 *
 * @author Md Mehedi Hasan
 */
public class CourseLearning {
    ArrayList<String> learnings;
    
    public void add(String learning){
        learnings.add(learning);
    }
    
    public void setAt(int i, String learning){
        learnings.set(i, learning);
    }
    
    public String getAt(int i){
        return learnings.get(i);
    }

    public ArrayList<String> getLearnings() {
        return learnings;
    }

    public void setLearnings(ArrayList<String> learnings) {
        this.learnings = learnings;
    }
    
}
