/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global;
      ;

import Course.Overflow.DB;
import Course.Overflow.Files.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    AccountType accountType;
    String username;
    String email;
    String password;
    String firstName;
    String lastName;
    Date dob;
    String institution;
    String fbURL;
    String linkedInURL;
    Date signupDate;
    String about;
    Country country;
    Files photo;
    CreditCard card;
    String youtubeURL;
    String website;
    ArrayList<Language> languages;

    public Person(String username){
        this.username = username;
        ResultSet rs = DB.executeQuery("SELECT * FROM PERSON WHERE ID = '#'", username);
        try {
            rs.next();
            this.email = rs.getString("EMAIL");
            this.password = rs.getString("PASSWORD");
            this.firstName = rs.getString("FIRST_NAME");
            this.lastName = rs.getString("LAST_NAME");
            this.dob = rs.getDate("DOB");
            this.institution = rs.getString("INSTITUTION");
            this.fbURL = rs.getString("FB_URL");
            this.linkedInURL = rs.getString("LINKEDIN_URL");
            this.signupDate = rs.getDate("SIGNUP_DATE");
            this.about = rs.getString("ABOUT");
            this.country = new Country(rs.getInt("COUNTRY_ID"));
            this.photo = new Files(rs.getInt("PHOTO_ID"));
            this.card = new CreditCard(rs.getInt("CARD_ID"));
            this.youtubeURL = rs.getString("YOUTUBE_URL");
            this.website = rs.getString("WEBSITE");
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Person insertNew(String username, String email, String password, String firstName, String lastName, String about){
        DB.execute("INSERT INTO PERSON(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, SIGNUP_DATE, ABOUT)"
              + " VALUES('#', '#', '#', '#', '#', #, '#')"
              , username, email, password, firstName, lastName, ToolKit.getCurTimeDB(), about);
        return new Person(username);
    }    
}
