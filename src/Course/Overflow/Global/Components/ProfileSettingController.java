/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Components;

import Course.Overflow.Files.FileType;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.Country;
import Course.Overflow.Global.CreditCard;
import Course.Overflow.Global.Customize.HoverEffect;
import Course.Overflow.Global.Designation;
import Course.Overflow.Global.EducationalStatus;
import Course.Overflow.Global.GLOBAL;
import Course.Overflow.Global.Language;
import Course.Overflow.Global.Page.PageName;
import Course.Overflow.Global.Person;
import Course.Overflow.Global.Person.AccountType;
import Course.Overflow.Global.ToolKit;
import Course.Overflow.Student.Student;
import Course.Overflow.Teacher.Teacher;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Mehedi
 */
public class ProfileSettingController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField language;
    private TextArea biography;
    @FXML
    private TextField website;
    @FXML
    private TextField facebook;
    @FXML
    private TextField youtube;
    @FXML
    private ImageView photo;
    @FXML
    private Label imageNameLabel;
    @FXML
    private Button upload;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton cancel;
    @FXML
    private TextField email;
    @FXML
    private TextField oldPass;
    @FXML
    private TextField newPass;
    @FXML
    private TextField newPassAgain;
    @FXML
    private VBox securityBox;
    @FXML
    private ChoiceBox<String> countryCB;
    @FXML
    private DatePicker dob;
    @FXML
    private TextArea about;
    @FXML
    private TextField institution;
    @FXML
    private TextField linkedin;
    
    
    private String username;
    private String password;
    private AccountType accountType;
    private ArrayList<Language> selectedLanguage;
    private File photoFile;
    @FXML
    private Label eduStatusLabel;
    @FXML
    private ChoiceBox<String> eduStatusCB;
    
    private ObservableList<String> eduStatusList;
    private ObservableList<String> countryList;
    private Person person;
    
    @FXML
    private VBox securityBox1;
    @FXML
    private TextField cardNo;
    @FXML
    private TextField nameOnCard;
    @FXML
    private DatePicker expireDate;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedLanguage = new ArrayList<>();
        addLanguage();
        addCountries();
        addListener();
    }    

    private void addLanguage() {
        ArrayList<Language> list = Language.getList();
        VBox container = new VBox();
        AnchorPane root = new AnchorPane(container);
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        
        for(Language l : list){
            CheckBox cb = new CheckBox(l.getName());
            container.getChildren().add(cb);
            cb.setStyle(cb.getStyle() + "-fx-font-size: 18;");
            cb.setOnMouseClicked((event) -> {
                if(cb.isSelected()){
                    selectedLanguage.add(l);
                }
                else{
                    selectedLanguage.remove(l);
                }
                refreshLanguageString();
            });
        }
        
        new HoverEffect(language, root) {
            @Override
            public void setLocation() {
                this.defaultLocation();
            }
        };
        
        Platform.runLater(()->{
            root.setStyle(
                  root.getStyle()
                  + "-fx-background-color: white;"
                  + "-fx-pref-width: 350;"
            );
            System.out.println(language.getPrefWidth() + 100);
            container.setStyle(
                  container.getStyle()
                  + "-fx-alignment: center-left;"
                  + "-fx-spacing: 5; "
            );
        });
    }

    private void refreshLanguageString() {
        String text = "";
        String[] languages = new String[selectedLanguage.size()];
        
        for(int i=0; i<selectedLanguage.size(); i++){
            languages[i] = selectedLanguage.get(i).getName();
        }
        Arrays.sort(languages);
        for(String name : languages){
            if(text == ""){
                text = name;
            }
            else{
                text += ", " + name;
            }
        }
        language.setText(text);
    }
    
    public void readyEducationalStatusOrDesignation(){
        System.out.println(accountType);
        eduStatusList = FXCollections.observableArrayList();
        eduStatusList.add("-- Select --");
        eduStatusLabel.setText(accountType == AccountType.Student ? "Educational Status" : "Designation");
        
        if(accountType == AccountType.Student){
            ArrayList<EducationalStatus> list = EducationalStatus.getList();
            for(EducationalStatus es : list){
                eduStatusList.add(es.getType());
            }
            eduStatusCB.setItems(eduStatusList);
            eduStatusCB.setValue("-- Select --");
        }
        else {
            ArrayList<Designation> list = Designation.getList();
            for(Designation ds : list){
                eduStatusList.add(ds.getType());
            }
            eduStatusCB.setItems(eduStatusList);
            eduStatusCB.setValue("-- Select --");
        }
    }

    public void createEnvironmentForSignup(AccountType accountType, String email, String username, String password) {
        this.accountType = accountType;
        this.username = username;
        this.password = password;
        this.email.setText(email);
        
        VBox parent = (VBox) securityBox.getParent();
        parent.getChildren().remove(securityBox);
        readyEducationalStatusOrDesignation();
        
        save.setOnMouseClicked((event) -> {
            switch(accountType){
                case Student:
                    person = new Student(accountType, username, email, password, firstName.getText(), lastName.getText(), about.getText());
                    if(eduStatusCB.getValue() != "-- Select --") ((Student)person).setEduStatus(new EducationalStatus(eduStatusCB.getValue()));
                    break;
                case Teacher:
                    person = new Teacher(accountType, username, email, password, firstName.getText(), lastName.getText(), about.getText());
                    if(eduStatusCB.getValue() != "-- Select --") ((Teacher)person).setDesignation(new Designation(eduStatusCB.getValue()));
                    break;
//                case Admin:     person = (Admin) person; break;
            }
            if(countryCB.getValue() != "-- Select --"){
                person.setCountry(new Country(countryCB.getValue()));
            }
            person.setDob(ToolKit.localDateToDate(dob.getValue()));
            person.setInstitution(institution.getText());
            person.setWebsite(website.getText());
            person.setFbURL(facebook.getText());
            person.setYoutubeURL(youtube.getText());
            person.setLinkedInURL(linkedin.getText());
            person.setLanguages(selectedLanguage);
            
            
            if(cardNo.getText() != "" && nameOnCard.getText() != "" && expireDate.getValue() != null){
                person.setCard(CreditCard.insertCreditCard(cardNo.getText(), nameOnCard.getText(), ToolKit.localDateToDate(expireDate.getValue())));
            } else {
                // Show jFrame for error of credit card...
            }
            
            if(photoFile != null){
                person.setPhoto(new Files(photoFile, FileType.toType("Picture")));
            }
            
//            Files fileDB = new Files(photoFile, FileType.toType("Picture"));
//            CreditCard card = CreditCard.insertCreditCard(cardNo.getText(), nameOnCard.getText(), ToolKit.localDateToDate(expireDate.getValue()));
//            Country country = new Country(this.countryCB.getValue());
            
            
//            DB.execute("INSERT INTO PERSON(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, SIGNUP_DATE, ABOUT, PHOTO_ID, CARD_ID, COUNTRY_ID) VALUES('#', '#', '#', '#', '#', #, '#', #, #, #)",
//                  username, this.email.getText(), password, firstName.getText(), lastName.getText(), ToolKit.getCurTimeDB(), about.getText(), fileDB.getId().toString(), card.getId().toString(), country.getId().toString()
//            );
            
            GLOBAL.USER = person;
            GLOBAL.PAGE_CTRL.loadPage(PageName.Home);
        });
        cancel.setOnMouseClicked(event -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.Login);
        });
    }

    private void addListener() {
        upload.setOnMouseClicked((event) -> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(GLOBAL.FILE_CHOOSER_DIRECTORY));
            photoFile = fc.showOpenDialog(null);
            if(photoFile != null){
                imageNameLabel.setText(photoFile.getName());
                photo.setImage(new Image(photoFile.toURI().toString()));
                GLOBAL.FILE_CHOOSER_DIRECTORY = photoFile.getParent();
            }
            else{
                imageNameLabel.setText("");
            }
        });
    }

    private void addCountries() {
        countryList = FXCollections.observableArrayList();
        countryList.add("-- Select --");
        ArrayList<Country> list = Country.getList();
        for(Country cc : list){
            countryList.add(cc.getType());
        }
        countryCB.setItems(countryList);
        countryCB.setValue("-- Select --");
    }
}
