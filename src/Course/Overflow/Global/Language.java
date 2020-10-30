/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global;

import java.util.ArrayList;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Language {
    int id;
    String name;

    public Language(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public static ArrayList<Language> getList(){
        //This will be updated with SQL to get list from DATABASE
        ArrayList<Language> list = new ArrayList<>();
        list.add(new Language(0, "Bangla"));
        list.add(new Language(1, "English"));
        list.add(new Language(2, "Hindi"));
        list.add(new Language(3, "Urdu"));
        list.add(new Language(4, "Arabic"));
        return list;
    } 
    
}
