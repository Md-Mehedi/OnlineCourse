package Course.Overflow.Global.Components;

import Course.Overflow.Files.FileType;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.Components.Notification.Notification;
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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Mehedi
 */
public class ProfileSettingController implements Initializable {


    private String username;
    private String password;
    private AccountType accountType;
    private ArrayList<Language> selectedLanguage;
    private File photoFile;

    private ObservableList<String> eduStatusList;
    private ObservableList<String> countryList;
    private Student student;
    private Teacher teacher;

    private HashMap<Integer, CheckBox> checkBoxes;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField language;
    @FXML
    private JFXComboBox<String> countryCB;
    @FXML
    private JFXDatePicker dob;
    @FXML
    private JFXComboBox<String> eduStatusCB;
    @FXML
    private JFXTextField institution;
    @FXML
    private JFXTextField website;
    @FXML
    private JFXTextField facebook;
    @FXML
    private JFXTextField youtube;
    @FXML
    private JFXTextField linkedin;
    @FXML
    private JFXTextArea about;
    @FXML
    private ImageView photo;
    @FXML
    private Label imageNameLabel;
    @FXML
    private Button upload;
    @FXML
    private VBox securityBox1;
    @FXML
    private JFXTextField cardNo;
    @FXML
    private JFXTextField nameOnCard;
    @FXML
    private JFXDatePicker expireDate;
    @FXML
    private VBox securityBox;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField oldPass;
    @FXML
    private JFXTextField newPass;
    @FXML
    private JFXTextField newPassAgain;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton cancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clearPassword();
        accountType = GLOBAL.ACCOUNT_TYPE;
        student = GLOBAL.STUDENT;
        teacher = GLOBAL.TEACHER;
        selectedLanguage = new ArrayList<>();
        checkBoxes = new HashMap<>();
        readyEducationalStatusOrDesignation();
        addLanguage();
        addCountries();
        addListener();
        loadData();
    }

    private void clearPassword() {
        oldPass.setText("");
        newPass.setText("");
        newPassAgain.setText("");
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
            if (ps.getDob() != null) {
                dob.setValue(ToolKit.DateToLocalDate(ps.getDob()));
            }
            if (ps.getImage() != null) {
                photo.setImage(ps.getImage());
                photoFile = new File(ToolKit.makeAbsoluteLocation(ps.getPhoto().getContent()));
                System.out.println(photoFile.getAbsolutePath());
                System.out.println(ToolKit.makeAbsoluteLocation(ps.getPhoto().getContent()));
            }
            if (ps.getCard() != null) {
                cardNo.setText(ps.getCard().getCardNo());
                nameOnCard.setText(ps.getCard().getNameOnCard());
                expireDate.setValue(ToolKit.DateToLocalDate(ps.getCard().getExpireDate()));
            }
            if (ps.getCountry() != null) {
                countryCB.setValue(ps.getCountry().getName());
            }
            if (ps.getAccountType() == AccountType.Student && GLOBAL.STUDENT.getEduStatus() != null) {
                eduStatusCB.setValue(GLOBAL.STUDENT.getEduStatus().getType());
            }
            if (ps.getAccountType() == AccountType.Teacher && GLOBAL.TEACHER.getDesignation() != null) {
                eduStatusCB.setValue(GLOBAL.TEACHER.getDesignation().getType());
            }
            selectedLanguage.clear();
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
        ToolKit.setAnchor(container, 0, 0, 0, 0);
        root.getStylesheets().add(GLOBAL.GLOBAL_LOCATION + "/Global.css");
        root.getStyleClass().add("backContainer");
        root.getStyleClass().add("shadow");
        for (Language l : list) {
            CheckBox cb = new CheckBox(l.getName());
            checkBoxes.put(l.getId(), cb);
            //System.out.println(checkBoxes.get(l.getId()));
            //checkBoxes.get(l.getId()).setSelected(true);

            container.getChildren().add(cb);
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
                    + "-fx-pref-width: 350;"
            );
            container.setStyle(
                    container.getStyle()
                    + "-fx-alignment: center-left;"
                    + "-fx-spacing: 5; "
                    + "-fx-padding: 5; "
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
        eduStatusCB.setPromptText(accountType == AccountType.Student ? "Educational Status" : "Designation");

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
                    GLOBAL.STUDENT = student;
                    break;
                case Teacher:
                    teacher = new Teacher(accountType, username, email, password, firstName.getText(), lastName.getText(), about.getText(), ToolKit.localDateToDate(dob.getValue()));
                    GLOBAL.TEACHER = teacher;
                    break;
//                case Admin:     person = (Admin) person; break;
            }
            setPersonInformations();
            Notification.setRegistration(username);
            GLOBAL.PAGE_CTRL.loadPage(PageName.Home);
        });
        cancel.setOnMouseClicked(event -> {
            GLOBAL.PAGE_CTRL.loadPage(PageName.Login);
        });
    }

    private void addListener() {
        upload.setOnMouseClicked((event) -> {
            File chosenFile = ToolKit.chooseFile(FileType.PICTURE);
            if (chosenFile != null) {
                photoFile = chosenFile;
            }
            if (chosenFile != null) {
                imageNameLabel.setText(chosenFile.getName());
                photo.setImage(new Image(chosenFile.toURI().toString()));
                GLOBAL.FILE_CHOOSER_DIRECTORY = chosenFile.getParent();
            } else {
                imageNameLabel.setText("");
            }
        });
        save.setOnMouseClicked((event) -> {
            if (!checkConditions()) {
                return;
            }
            setPersonInformations();
            GLOBAL.PAGE_CTRL.loadPreviousPage();
            clearPassword();
        });
        cancel.setOnMouseClicked(event -> {
            GLOBAL.PAGE_CTRL.loadPreviousPage();
            clearPassword();
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
            JOptionPane.showConfirmDialog(null, "Please Write or Select your date of birth !", "select", JOptionPane.CANCEL_OPTION);
            return false;
        }
        if (!ToolKit.isOnlyNumber(cardNo.getText())) {
            JOptionPane.showConfirmDialog(null, "Card number contains only digits!", "select", JOptionPane.OK_OPTION);
            return false;
        }
        boolean a = !cardNo.getText().equals("");
        boolean b = !nameOnCard.getText().equals("");
        boolean c = expireDate.getValue() != null;
        if (!((a & b & c) || (!a & !b & !c))) {
            JOptionPane.showConfirmDialog(null, "Please provide all informations of credit card  !", "select", JOptionPane.OK_OPTION);
            return false;
        }
        a = !oldPass.getText().equals("");
        b = !newPass.getText().equals("");
        c = !newPassAgain.getText().equals("");
        if (!((a & b & c) || (!a & !b & !c))) {
            JOptionPane.showConfirmDialog(null, "Please fill up all password field!", "select", JOptionPane.OK_OPTION);
            return false;
        }
        if (!newPass.getText().equals(newPassAgain.getText())) {
            JOptionPane.showConfirmDialog(null, "New password is not matching!", "select", JOptionPane.OK_OPTION);
            return false;
        }
        if (!oldPass.getText().equals("")) {
            Person per = Person.validUser(ToolKit.getCurrentPerson().getUsername(), oldPass.getText());
            if (per == null) {
                JOptionPane.showConfirmDialog(null, "Password is not matching.", "select", JOptionPane.OK_OPTION);
                return false;
            }
        }
        return true;
    }


    private void setPersonInformations() {
        Person person = ToolKit.getCurrentPerson();
        person.setFirstName(firstName.getText());
        person.setLastName(lastName.getText());
        person.setAbout(about.getText());
        person.setDob(ToolKit.localDateToDate(dob.getValue()));
        person.setInstitution(institution.getText());
        person.setWebsite(website.getText());
        person.setFbURL(facebook.getText());
        person.setYoutubeURL(youtube.getText());
        person.setLinkedInURL(linkedin.getText());
        person.setLanguages(selectedLanguage);

        if (!countryCB.getValue().equals("-- Select --")) {
            person.setCountry(new Country(countryCB.getValue()));
        } else {
            person.setCountry(null);
        }

        if (!cardNo.getText().equals("")) {
            if (person.getCard() == null) {
                person.setCard(CreditCard.insertCreditCard(cardNo.getText(), nameOnCard.getText(), ToolKit.localDateToDate(expireDate.getValue())));
            } else {
                CreditCard card = person.getCard();
                card.setCardNo(cardNo.getText());
                card.setNameOnCard(nameOnCard.getText());
                card.setExpireDate(ToolKit.localDateToDate(expireDate.getValue()));
            }
        } else if (person.getCard() != null) {
            person.setCard(null);
        }

        if (person.getPhoto() == null) {
            person.setPhoto(new Files(photoFile, FileType.PICTURE));
        } else {
            person.getPhoto().setFile(photoFile);
        }

        switch (accountType) {
            case Student:
                if (!eduStatusCB.getValue().equals("-- Select --")) {
                    student.setEduStatus(new EducationalStatus(eduStatusCB.getValue()));
                }
                break;
            case Teacher:
                if (!eduStatusCB.getValue().equals("-- Select --")) {
                    teacher.setDesignation(new Designation(eduStatusCB.getValue()));
                }
        }
        if (!oldPass.getText().equals("")) {
            ToolKit.getCurrentPerson().setPassword(newPass.getText());
        }
    }
}
