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
import java.util.HashMap;
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
import javax.swing.JOptionPane;

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
    private Student student;
    private Teacher teacher;

    @FXML
    private VBox securityBox1;
    @FXML
    private TextField cardNo;
    @FXML
    private TextField nameOnCard;
    @FXML
    private DatePicker expireDate;
    private HashMap<Integer, CheckBox> checkBoxes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accountType = GLOBAL.ACCOUNT_TYPE;
        selectedLanguage = new ArrayList<>();
        checkBoxes = new HashMap<>();
        readyEducationalStatusOrDesignation();
        addLanguage();
        addCountries();
        addListener();
        loadData();

    }

    private void loadData() {
        Person ps = ToolKit.getCurrentPerson();
//        System.out.println("loaddata" + ps.getAccountType());
        if (ps != null) {
            firstName.setText(ps.getFirstName());
            lastName.setText(ps.getLastName());
            institution.setText(ps.getInstitution());
            website.setText(ps.getWebsite());
            facebook.setText(ps.getFbURL());
            linkedin.setText(ps.getLinkedInURL());
            youtube.setText(ps.getYoutubeURL());
            about.setText(ps.getAbout());
            email.setText(ps.getEmail());
            email.setDisable(true);
            if (ps.getDob() != null) {
                dob.setValue(ToolKit.DateToLocalDate(ps.getDob()));
            }
            if (ps.getImage() != null) {
                photo.setImage(ps.getImage());
            }
            if (ps.getCard() != null) {
                cardNo.setText(ps.getCard().getCardNo());
            System.out.println("card");
                nameOnCard.setText(ps.getCard().getNameOnCard());
                expireDate.setValue(ToolKit.DateToLocalDate(ps.getCard().getExpireDate()));
            }
            if (ps.getCountry() != null) {
                countryCB.setValue(ps.getCountry().getName());
            }
            if (ps.getAccountType() == AccountType.Student && GLOBAL.STUDENT.getEduStatus() != null) {
                System.out.println("inside" + GLOBAL.STUDENT.getEduStatus().getType());
                eduStatusCB.setValue(GLOBAL.STUDENT.getEduStatus().getType());
            }
            if (ps.getAccountType() == AccountType.Teacher && GLOBAL.TEACHER.getDesignation() != null) {
                System.out.println("inside" + GLOBAL.TEACHER.getDesignation().getType());
                eduStatusCB.setValue(GLOBAL.TEACHER.getDesignation().getType());
            }
            for (Language l : ps.getLanguages()) {
                CheckBox cb = checkBoxes.get(l.getId());
                cb.setSelected(true);
                selectedLanguage.add(l);
                cb.setOnMouseClicked((event) -> {
                    if (cb.isSelected() == false) {
                        if (selectedLanguage.contains(l)) {
                            selectedLanguage.remove(l);
                        }
                    } else {
                        if (!selectedLanguage.contains(l)) {
                            selectedLanguage.add(l);
                        }
                    }
                    refreshLanguageString();
                });
            }
            refreshLanguageString();
        }
    }

    private void addLanguage() {
        ArrayList<Language> list = Language.getList();
        VBox container = new VBox();
        AnchorPane root = new AnchorPane(container);
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");

        for (Language l : list) {
            CheckBox cb = new CheckBox(l.getName());
            checkBoxes.put(l.getId(), cb);
            //System.out.println(checkBoxes.get(l.getId()));
            //checkBoxes.get(l.getId()).setSelected(true);

            container.getChildren().add(cb);
            cb.setStyle(cb.getStyle() + "-fx-font-size: 18;");
            cb.setOnMouseClicked((event) -> {
                if (cb.isSelected()) {
                    selectedLanguage.add(l);
                } else {
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

        Platform.runLater(() -> {
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

        for (int i = 0; i < selectedLanguage.size(); i++) {
            languages[i] = selectedLanguage.get(i).getName();
        }
        Arrays.sort(languages);
        for (String name : languages) {
            if (text == "") {
                text = name;
            } else {
                text += ", " + name;
            }
        }
        language.setText(text);
    }

    public void readyEducationalStatusOrDesignation() {
        //System.out.println(accountType);
        eduStatusList = FXCollections.observableArrayList();
        eduStatusList.clear();
        eduStatusList.add("-- Select --");
        eduStatusLabel.setText(accountType == AccountType.Student ? "Educational Status" : "Designation");

        if (accountType == AccountType.Student) {
            ArrayList<EducationalStatus> list = EducationalStatus.getList();
            for (EducationalStatus es : list) {
                eduStatusList.add(es.getType());
            }
            eduStatusCB.setItems(eduStatusList);
            eduStatusCB.setValue("-- Select --");
        } else {
            ArrayList<Designation> list = Designation.getList();
            for (Designation ds : list) {
                eduStatusList.add(ds.getType());
            }
            eduStatusCB.setItems(eduStatusList);
            eduStatusCB.setValue("-- Select --");
        }
    }

    public void createEnvironmentForSignup(String email, String username, String password) {
        this.accountType = GLOBAL.ACCOUNT_TYPE;
        this.username = username;
        this.password = password;
        this.email.setText(email);

        VBox parent = (VBox) securityBox.getParent();
        parent.getChildren().remove(securityBox);
        readyEducationalStatusOrDesignation();

        save.setOnMouseClicked((event) -> {
            if (!checkConditions()) {
                return;
            }
            switch (accountType) {
                case Student:
                    student = new Student(accountType, username, email, password, firstName.getText(), lastName.getText(), about.getText(), ToolKit.localDateToDate(dob.getValue()));
                    if (eduStatusCB.getValue() != "-- Select --") {
                        student.setEduStatus(new EducationalStatus(eduStatusCB.getValue()));
                    }
                    GLOBAL.STUDENT = student;
                    break;
                case Teacher:
                    teacher = new Teacher(accountType, username, email, password, firstName.getText(), lastName.getText(), about.getText(), ToolKit.localDateToDate(dob.getValue()));
                    if (eduStatusCB.getValue() != "-- Select --") {
                        teacher.setDesignation(new Designation(eduStatusCB.getValue()));
                    }
                    GLOBAL.TEACHER = teacher;
                    break;
//                case Admin:     person = (Admin) person; break;
            }
            setPersonInformations();

//            Files fileDB = new Files(photoFile, FileType.toType("Picture"));
//            CreditCard card = CreditCard.insertCreditCard(cardNo.getText(), nameOnCard.getText(), ToolKit.localDateToDate(expireDate.getValue()));
//            Country country = new Country(this.countryCB.getValue());
//            DB.execute("INSERT INTO PERSON(ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, SIGNUP_DATE, ABOUT, PHOTO_ID, CARD_ID, COUNTRY_ID) VALUES('#', '#', '#', '#', '#', #, '#', #, #, #)",
//                  username, this.email.getText(), password, firstName.getText(), lastName.getText(), ToolKit.getCurTimeDB(), about.getText(), fileDB.getId().toString(), card.getId().toString(), country.getId().toString()
//            );
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
            if (photoFile != null) {
                imageNameLabel.setText(photoFile.getName());
                photo.setImage(new Image(photoFile.toURI().toString()));
                GLOBAL.FILE_CHOOSER_DIRECTORY = photoFile.getParent();
            } else {
                imageNameLabel.setText("");
            }
        });
        save.setOnMouseClicked((event) -> {
            if (!checkConditions()) {
                return;
            }

        });

    }

    private void addCountries() {
        countryList = FXCollections.observableArrayList();
        countryList.add("-- Select --");
        ArrayList<Country> list = Country.getList();
        for (Country cc : list) {
            countryList.add(cc.getName());
        }
        countryCB.setItems(countryList);
        countryCB.setValue("-- Select --");
    }

    private boolean checkConditions() {
        boolean flag = (firstName.getText().trim().isEmpty()) | (lastName.getText().trim().isEmpty());
        if (flag) {
            JOptionPane.showConfirmDialog(null, "The first name or last name cannot be empty ", "select", JOptionPane.CANCEL_OPTION);
            return false;
        }
        flag = (!ToolKit.isValidString(firstName.getText().trim())) | (!ToolKit.isValidString(lastName.getText().trim()));
        if (flag) {
            JOptionPane.showConfirmDialog(null, "The first name or last name cannot contain \\ ,\",\' characters ", "select", JOptionPane.CANCEL_OPTION);
            return false;
        }
        flag = (about.getText().trim().isEmpty()) | (!ToolKit.isValidString(about.getText().trim()));
        if (flag) {
            JOptionPane.showConfirmDialog(null, "Please write something about you ! \n avoid \' , \\ and \" characters  ", "select", JOptionPane.CANCEL_OPTION);
            return false;
        }
        if (dob.getValue() == null) {
            JOptionPane.showConfirmDialog(null, "Pleas Write or Select your date of birth !", "select", JOptionPane.CANCEL_OPTION);
            return false;
        }
        boolean a = cardNo.getText()!="";
        boolean b = nameOnCard.getText() != "";
        boolean c = expireDate.getValue() != null;
        if ((a & b & c) || (!a & !b & !c)){
            JOptionPane.showConfirmDialog(null, "Please provide all informations of credit card  !", "select", JOptionPane.OK_OPTION);
            return false;
        }
        return true;
    }

    private void setPersonInformations() {
        Person person = ToolKit.getCurrentPerson();
        if (countryCB.getValue() != "-- Select --") {
            person.setCountry(new Country(countryCB.getValue()));
        } else {
            person.setCountry(null);
        }
        person.setFirstName(firstName.getText());
        person.setLastName(lastName.getText());
        //person.setE
        person.setDob(ToolKit.localDateToDate(dob.getValue()));
        person.setInstitution(institution.getText());
        person.setWebsite(website.getText());
        person.setFbURL(facebook.getText());
        person.setYoutubeURL(youtube.getText());
        person.setLinkedInURL(linkedin.getText());
        person.setLanguages(selectedLanguage);

        if (cardNo.getText() != "" && nameOnCard.getText() != "" && expireDate.getValue() != null) {
            person.setCard(CreditCard.insertCreditCard(cardNo.getText(), nameOnCard.getText(), ToolKit.localDateToDate(expireDate.getValue())));
        }

        if (photoFile != null) {
            person.setPhoto(new Files(photoFile, FileType.toType("Picture")));
        }
    }
}
