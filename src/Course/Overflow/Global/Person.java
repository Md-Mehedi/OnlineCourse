/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global;
      ;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Person {
    int id;
    String firstName;
    String lastName;
    String shortDescription;
    String description;
    Language language;

    public Person(int id, String firstName, String lastName, String shortDescription, String description, Language language) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public Language getLanguage() {
        return language;
    }
    

    
}
