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
class Requirement {
    ArrayList<String> requiremnts;

    public ArrayList<String> getRequiremnts() {
        return requiremnts;
    }

    public void setRequiremnts(ArrayList<String> requiremnts) {
        this.requiremnts = requiremnts;
    }

    public Requirement(ArrayList<String> requiremnts) {
        this.requiremnts = requiremnts;
    }
}
