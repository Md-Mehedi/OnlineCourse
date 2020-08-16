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
    public enum AccountType{
        Admin("Admin"), Student("Student"), Teacher("Teacher");
        
        String name;
        private AccountType(String name) {
            this.name = name;
        }
        
    }
    
    int id;
    String firstName;
    String lastName;
    String shortDescription;
    String description;
    Language language;
    AccountType accountType;

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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    

    
}
