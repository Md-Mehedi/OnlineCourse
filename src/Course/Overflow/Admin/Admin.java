/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Admin;

import Course.Overflow.DB;
import Course.Overflow.Global.Person;

/**
 *
 * @author Asus
 */
public class Admin extends Person{

    
    public Admin(String username) {
        super(username);
        
    }
    
    public static boolean exist(String username) {
        return DB.valueExist("ADMIN", "ID", username);
    }
}
