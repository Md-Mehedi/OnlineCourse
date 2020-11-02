

package Course.Overflow.Global;
      

import Course.Overflow.DB;
import Course.Overflow.Files.Files;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

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
            if(rs.getInt("COUNTRY_ID") != 0) this.country = new Country(rs.getInt("COUNTRY_ID"));
            if(rs.getInt("PHOTO_ID") != 0) this.photo = new Files(rs.getInt("PHOTO_ID"));
            if(rs.getInt("CARD_ID") != 0) this.card = new CreditCard(rs.getInt("CARD_ID"));
            this.youtubeURL = rs.getString("YOUTUBE_URL");
            this.website = rs.getString("WEBSITE");
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Person(AccountType accountType, String username, String email, String password, String firstName, String lastName, String about, Date dob){
        this.accountType = accountType;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.about = about;
        this.dob = dob;
        
        DB.execute("INSERT INTO PERSON(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, SIGNUP_DATE, ABOUT, DOB)"
              + " VALUES('#', '#', '#', '#', '#', #, '#', #)"
              , username, email, password, firstName, lastName, ToolKit.getCurTimeDB(), about, ToolKit.JDateToDDate(dob));
        // return new Person(username);
    }    
    
    
    public static Person validUser(String username, String password) {
        ResultSet rs = DB.executeQuery("SELECT ID FROM PERSON WHERE ID = '#' AND PASSWORD = '#'", username, password);
        try {
            if(rs.next()){
                return new Person(username);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    
    
    
    
    /*
     * Getter and Setter...
     */

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        DB.execute("UPDATE PERSON SET PASSWORD = '#' WHERE ID = '#'", password, username);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        DB.execute("UPDATE PERSON SET FIRST_NAME = '#' WHERE ID = '#'", firstName, username);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        DB.execute("UPDATE PERSON SET LAST_NAME = '#' WHERE ID = '#'", lastName, username);
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
        DB.execute("UPDATE PERSON SET DOB = # WHERE ID = '#'", ToolKit.JDateToDDate(dob), username);
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
        DB.execute("UPDATE PERSON SET INSTITUTION = '#' WHERE ID = '#'", institution, username);
    }

    public String getFbURL() {
        return fbURL;
    }

    public void setFbURL(String fbURL) {
        this.fbURL = fbURL;
        DB.execute("UPDATE PERSON SET FB_URL = '#' WHERE ID = '#'", fbURL, username);
    }

    public String getLinkedInURL() {
        return linkedInURL;
    }

    public void setLinkedInURL(String linkedInURL) {
        this.linkedInURL = linkedInURL;
        DB.execute("UPDATE PERSON SET LINKEDIN_URL = '#' WHERE ID = '#'", linkedInURL, username);
    }

    public Date getSignupDate() {
        return signupDate;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
        DB.execute("UPDATE PERSON SET ABOUT = '#' WHERE ID = '#'", about, username);
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
        DB.execute("UPDATE PERSON SET COUNTRY_ID = '#' WHERE ID = '#'", country.getId().toString(), username);
    }

    public Files getPhoto() {
        return photo;
    }

    public void setPhoto(Files photo) {
        this.photo = photo;
        DB.execute("UPDATE PERSON SET PHOTO_ID = '#' WHERE ID = '#'", photo.getId().toString(), username);
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
        DB.execute("UPDATE PERSON SET CARD_ID = '#' WHERE ID = '#'", card.getId().toString(), username);
    }

    public String getYoutubeURL() {
        return youtubeURL;
    }

    public void setYoutubeURL(String youtubeURL) {
        this.youtubeURL = youtubeURL;
        DB.execute("UPDATE PERSON SET YOUTUBE_URL = '#' WHERE ID = '#'", youtubeURL, username);
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
        DB.execute("UPDATE PERSON SET WEBSITE = '#' WHERE ID = '#'", website, username);
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Language> languages) {
        this.languages = languages;
        ResultSet rs = DB.executeQuery("SELECT * FROM PERSON_LANGUAGE WHERE PERSON_ID = '#'", username);
        try {
            while(rs.next()){
                DB.execute("DELETE FROM PERSON_LANGUAGE WHERE ID = #", rs.getString("ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(Language lang : languages){
            Integer id = DB.generateId("PERSON_LANGUAGE");
            DB.execute("INSERT INTO PERSON_LANGUAGE VALUES(#, '#', #)", id.toString(), username, lang.getId().toString());
        }
    }
    
    public Image getImage(){
        return new Image(new File(ToolKit.makeAbsoluteLocation(photo.getContent())).toURI().toString());
    }
    
}
