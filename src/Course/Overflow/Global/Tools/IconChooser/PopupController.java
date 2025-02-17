/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Course.Overflow.Global.Tools.IconChooser;

import Course.Overflow.Global.Customize.Icon.IconType;
import Course.Overflow.Global.Customize.SVG;
import Course.Overflow.Global.Customize.SVGView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PopupController implements Initializable {

    private ArrayList<Label> buttonList;
    @FXML
    private AnchorPane runningPane;
    private Stage stage;
    private HashMap<String, Object> result;

    @FXML
    private AnchorPane container;
    @FXML
    private HBox typeContainer;
    @FXML
    private AnchorPane centerPane;
    private int maxCol = 13;
    String s[] = {"a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","b","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","c","a","b","c","a","b","c","a","b","c","d","e","a","b","c","d","e","a","b","c","d","e"};
    ArrayList<AnchorPane> paneList;
    private Label selectedButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        result = new HashMap<String,Object>();
        paneList = new ArrayList<AnchorPane>();
        buttonList = new ArrayList<Label>();
        makeIconType(IconType.FONT_AWESOME_ICON,fontIconName);
        makeIconType(IconType.SVG,SVG.getNames());
        select(buttonList.get(0));
    }    

    private void makeIconType(IconType type, String iconPack[]) {
        AnchorPane pane = new AnchorPane();
        paneList.add(pane);
        Label label = new Label(type.name());
        buttonList.add(label);
        typeContainer.getChildren().add(label);
        createPane(type, pane, iconPack);
        makeEventHandler(label,pane);
    }

    private void makeEventHandler(Label label, AnchorPane pane) {
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                select(label);
            }
        });
    }

    private void createPane(IconType type, AnchorPane pane, String s[]) {
        GridPane grid = new GridPane();
        Node node = null;
        for(int i=0;i<maxCol;i++){
            ColumnConstraints column = new ColumnConstraints(60,60,60);
            column.setHalignment(HPos.CENTER);
            grid.getColumnConstraints().add(column);
        }
        int rowNum = -1;
        for(int i=0; i<s.length; i++){
            if(i%maxCol==0){
                rowNum++;
                RowConstraints row = new RowConstraints(60,60,60);
                row.setValignment(VPos.CENTER);
                grid.getRowConstraints().add(row);
            }
            if(type == IconType.FONT_AWESOME_ICON){
                node = getFontAwesomeIconView(s[i]);
            }
            if(type == IconType.SVG){
                node = getSVGView(s[i]);
            }
            grid.add(node, (i)%maxCol, rowNum);
            addEvent(type, node);
        }
        pane.getChildren().add(grid);
        centerPane.getChildren().add(pane);
    }
    
    private FontAwesomeIconView getFontAwesomeIconView(String name){
        FontAwesomeIconView fiv = new FontAwesomeIconView();
        fiv.setIcon(FontAwesomeIcon.valueOf(name));
        fiv.setSize("35");
        return fiv;
    }
    private StackPane getSVGView(String name) {
        return new SVGView(name, 40.0, 40.0);
    }
    
    private void addEvent(IconType type, Node node){
        node.setOnMouseEntered((MouseEvent)->{
            node.setScaleX(1.2);
            node.setScaleY(1.2);
        });
        node.setOnMouseExited((MouseEvent)->{
            node.setScaleX(1);
            node.setScaleY(1);
        });
        node.setOnMouseClicked((MouseEvent)->{
            result.clear();
            if(type == IconType.FONT_AWESOME_ICON){
                FontAwesomeIconView icon = (FontAwesomeIconView) node;
                result.put("type", IconType.FONT_AWESOME_ICON);
                result.put("name", icon.getGlyphName());
            }
            if(type == IconType.SVG){
                SVGView icon = (SVGView) node;
                result.put("type", IconType.SVG);
                result.put("name", icon.getSVGName());
            }
            closeStage();
        });
    }
    private void select(Label label) {
        selectedButton = label;
        int idx = buttonList.indexOf(label);
        centerPane.getChildren().clear();
        centerPane.getChildren().add(paneList.get(idx));
        Platform.runLater(()->{
            new Timeline(new KeyFrame(Duration.millis(300), new KeyValue(runningPane.layoutXProperty(), selectedButton.getLayoutX()+selectedButton.getWidth()/2-runningPane.getWidth()/2))).play();
            new Timeline(new KeyFrame(Duration.millis(300), new KeyValue(runningPane.scaleXProperty(), selectedButton.getWidth()/runningPane.getWidth()))).play();
        });
        
    }   
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void closeStage(){
        if(stage!=null) {
            stage.close();
        }
    }
    
    public HashMap<String, Object> getResult(){
        return result;
    }
    
    String fontIconName[] = {
        "ADJUST",
        "ADN",
        "ALIGN_CENTER",
        "ALIGN_JUSTIFY",
        "ALIGN_LEFT",
        "ALIGN_RIGHT",
        "AMAZON",
        "AMBULANCE",
        "ANCHOR",
        "ANDROID",
        "ANGELLIST",
        "ANGLE_DOUBLE_DOWN",
        "ANGLE_DOUBLE_LEFT",
        "ANGLE_DOUBLE_RIGHT",
        "ANGLE_DOUBLE_UP",
        "ANGLE_DOWN",
        "ANGLE_LEFT",
        "ANGLE_RIGHT",
        "ANGLE_UP",
        "APPLE",
        "ARCHIVE",
        "AREA_CHART",
        "ARROW_CIRCLE_DOWN",
        "ARROW_CIRCLE_LEFT",
        "ARROW_CIRCLE_ALT_DOWN",
        "ARROW_CIRCLE_ALT_LEFT",
        "ARROW_CIRCLE_ALT_RIGHT",
        "ARROW_CIRCLE_ALT_UP",
        "ARROW_CIRCLE_RIGHT",
        "ARROW_CIRCLE_UP",
        "ARROW_DOWN",
        "ARROW_LEFT",
        "ARROW_RIGHT",
        "ARROW_UP",
        "ARROWS",
        "ARROWS_ALT",
        "ARROWS_H",
        "ARROWS_V",
        "ASTERISK",
        "AT",
        "AUTOMOBILE",
        "BACKWARD",
        "BALANCE_SCALE",
        "BAN",
        "BANK",
        "BAR_CHART",
        "BAR_CHART_ALT",
        "BARCODE",
        "BARS",
        "BATTERY_0",
        "BATTERY_1",
        "BATTERY_2",
        "BATTERY_3",
        "BATTERY_4",
        "BATTERY_EMPTY",
        "BATTERY_FULL",
        "BATTERY_HALF",
        "BATTERY_QUARTER",
        "BATTERY_THREE_QUARTERS",
        "BED",
        "BEER",
        "BEHANCE",
        "BEHANCE_SQUARE",
        "BELL",
        "BELL_ALT",
        "BELL_SLASH",
        "BELL_SLASH_ALT",
        "BICYCLE",
        "BINOCULARS",
        "BIRTHDAY_CAKE",
        "BITBUCKET",
        "BITBUCKET_SQUARE",
        "BITCOIN",
        "BLACK_TIE",
        "BLUETOOTH",
        "BLUETOOTH_B",
        "BOLD",
        "BOLT",
        "BOMB",
        "BOOK",
        "BOOKMARK",
        "BOOKMARK_ALT",
        "BRIEFCASE",
        "BTC",
        "BUG",
        "BUILDING",
        "BUILDING_ALT",
        "BULLHORN",
        "BULLSEYE",
        "BUS",
        "BUYSELLADS",
        "CAB",
        "CALCULATOR",
        "CALENDAR",
        "CALENDAR_CHECK_ALT",
        "CALENDAR_MINUS_ALT",
        "CALENDAR_ALT",
        "CALENDAR_PLUS_ALT",
        "CALENDAR_TIMES_ALT",
        "CAMERA",
        "CAMERA_RETRO",
        "CAR",
        "CARET_DOWN",
        "CARET_LEFT",
        "CARET_RIGHT",
        "CARET_SQUARE_ALT_DOWN",
        "CARET_SQUARE_ALT_LEFT",
        "CARET_SQUARE_ALT_RIGHT",
        "CARET_SQUARE_ALT_UP",
        "CARET_UP",
        "CART_ARROW_DOWN",
        "CART_PLUS",
        "CC",
        "CC_AMEX",
        "CC_DINERS_CLUB",
        "CC_DISCOVER",
        "CC_JCB",
        "CC_MASTERCARD",
        "CC_PAYPAL",
        "CC_STRIPE",
        "CC_VISA",
        "CERTIFICATE",
        "CHAIN",
        "CHAIN_BROKEN",
        "CHECK",
        "CHECK_CIRCLE",
        "CHECK_CIRCLE_ALT",
        "CHECK_SQUARE",
        "CHECK_SQUARE_ALT",
        "CHEVRON_CIRCLE_DOWN",
        "CHEVRON_CIRCLE_LEFT",
        "CHEVRON_CIRCLE_RIGHT",
        "CHEVRON_CIRCLE_UP",
        "CHEVRON_DOWN",
        "CHEVRON_LEFT",
        "CHEVRON_RIGHT",
        "CHEVRON_UP",
        "CHILD",
        "CHROME",
        "CIRCLE",
        "CIRCLE_ALT",
        "CIRCLE_ALT_NOTCH",
        "CIRCLE_THIN",
        "CLIPBOARD",
        "CLOCK_ALT",
        "CLONE",
        "CLOSE",
        "CLOUD",
        "CLOUD_DOWNLOAD",
        "CLOUD_UPLOAD",
        "CNY",
        "CODE",
        "CODE_FORK",
        "CODEPEN",
        "CODIEPIE",
        "COFFEE",
        "COG",
        "COGS",
        "COLUMNS",
        "COMMENT",
        "COMMENT_ALT",
        "COMMENTING",
        "COMMENTING_ALT",
        "COMMENTS",
        "COMMENTS_ALT",
        "COMPASS",
        "COMPRESS",
        "CONNECTDEVELOP",
        "CONTAO",
        "COPY",
        "COPYRIGHT",
        "CREATIVE_COMMONS",
        "CREDIT_CARD",
        "CREDIT_CARD_ALT",
        "CROP",
        "CROSSHAIRS",
        "CSS3",
        "CUBE",
        "CUBES",
        "CUT",
        "CUTLERY",
        "DASHBOARD",
        "DASHCUBE",
        "DATABASE",
        "DEDENT",
        "DELICIOUS",
        "DESKTOP",
        "DEVIANTART",
        "DIAMOND",
        "DIGG",
        "DOLLAR",
        "DOT_CIRCLE_ALT",
        "DOWNLOAD",
        "DRIBBBLE",
        "DROPBOX",
        "DRUPAL",
        "EDGE",
        "EDIT",
        "EJECT",
        "ELLIPSIS_H",
        "ELLIPSIS_V",
        "EMPIRE",
        "ENVELOPE",
        "ENVELOPE_ALT",
        "ENVELOPE_SQUARE",
        "ERASER",
        "EUR",
        "EURO",
        "EXCHANGE",
        "EXCLAMATION",
        "EXCLAMATION_CIRCLE",
        "EXCLAMATION_TRIANGLE",
        "EXPAND",
        "EXPEDITEDSSL",
        "EXTERNAL_LINK",
        "EXTERNAL_LINK_SQUARE",
        "EYE",
        "EYE_SLASH",
        "EYEDROPPER",
        "FACEBOOK",
        "FACEBOOK_F",
        "FACEBOOK_OFFICIAL",
        "FACEBOOK_SQUARE",
        "FAST_BACKWARD",
        "FAST_FORWARD",
        "FAX",
        "FEED",
        "FEMALE",
        "FIGHTER_JET",
        "FILE",
        "FILE_ARCHIVE_ALT",
        "FILE_AUDIO_ALT",
        "FILE_CODE_ALT",
        "FILE_EXCEL_ALT",
        "FILE_IMAGE_ALT",
        "FILE_MOVIE_ALT",
        "FILE_ALT",
        "FILE_PDF_ALT",
        "FILE_PHOTO_ALT",
        "FILE_PICTURE_ALT",
        "FILE_POWERPOINT_ALT",
        "FILE_SOUND_ALT",
        "FILE_TEXT",
        "FILE_TEXT_ALT",
        "FILE_VIDEO_ALT",
        "FILE_WORD_ALT",
        "FILE_ZIP_ALT",
        "FILES_ALT",
        "FILM",
        "FILTER",
        "FIRE",
        "FIRE_EXTINGUISHER",
        "FIREFOX",
        "FLAG",
        "FLAG_CHECKERED",
        "FLAG_ALT",
        "FLASH",
        "FLASK",
        "FLICKR",
        "FLOPPY_ALT",
        "FOLDER",
        "FOLDER_ALT",
        "FOLDER_OPEN",
        "FOLDER_OPEN_ALT",
        "FONT",
        "FONTICONS",
        "FORT_AWESOME",
        "FORUMBEE",
        "FORWARD",
        "FOURSQUARE",
        "FROWN_ALT",
        "FUTBOL_ALT",
        "GAMEPAD",
        "GAVEL",
        "GBP",
        "GE",
        "GEAR",
        "GEARS",
        "GENDERLESS",
        "GET_POCKET",
        "GG",
        "GG_CIRCLE",
        "GIFT",
        "GIT",
        "GIT_SQUARE",
        "GITHUB",
        "GITHUB_ALT",
        "GITHUB_SQUARE",
        "GITTIP",
        "GLASS",
        "GLOBE",
        "GOOGLE",
        "GOOGLE_PLUS",
        "GOOGLE_PLUS_SQUARE",
        "GOOGLE_WALLET",
        "GRADUATION_CAP",
        "GRATIPAY",
        "GROUP",
        "H_SQUARE",
        "HACKER_NEWS",
        "HAND_GRAB_ALT",
        "HAND_LIZARD_ALT",
        "HAND_ALT_DOWN",
        "HAND_ALT_LEFT",
        "HAND_ALT_RIGHT",
        "HAND_ALT_UP",
        "HAND_PAPER_ALT",
        "HAND_PEACE_ALT",
        "HAND_POINTER_ALT",
        "HAND_ROCK_ALT",
        "HAND_SCISSORS_ALT",
        "HAND_SPOCK_ALT",
        "HAND_STOP_ALT",
        "HASHTAG",
        "HDD_ALT",
        "HEADER",
        "HEADPHONES",
        "HEART",
        "HEART_ALT",
        "HEARTBEAT",
        "HISTORY",
        "HOME",
        "HOSPITAL_ALT",
        "HOTEL",
        "HOURGLASS",
        "HOURGLASS_1",
        "HOURGLASS_2",
        "HOURGLASS_3",
        "HOURGLASS_END",
        "HOURGLASS_HALF",
        "HOURGLASS_ALT",
        "HOURGLASS_START",
        "HOUZZ",
        "HTML5",
        "I_CURSOR",
        "ILS",
        "IMAGE",
        "INBOX",
        "INDENT",
        "INDUSTRY",
        "INFO",
        "INFO_CIRCLE",
        "INR",
        "INSTAGRAM",
        "INSTITUTION",
        "INTERNET_EXPLORER",
        "INTERSEX",
        "IOXHOST",
        "ITALIC",
        "JOOMLA",
        "JPY",
        "JSFIDDLE",
        "KEY",
        "KEYBOARD_ALT",
        "KRW",
        "LANGUAGE",
        "LAPTOP",
        "LASTFM",
        "LASTFM_SQUARE",
        "LEAF",
        "LEANPUB",
        "LEGAL",
        "LEMON_ALT",
        "LEVEL_DOWN",
        "LEVEL_UP",
        "LIFE_BOUY",
        "LIFE_BUOY",
        "LIFE_RING",
        "LIFE_SAVER",
        "LIGHTBULB_ALT",
        "LINE_CHART",
        "LINK",
        "LINKEDIN",
        "LINKEDIN_SQUARE",
        "LINUX",
        "LIST",
        "LIST_ALT",
        "LIST_OL",
        "LIST_UL",
        "LOCATION_ARROW",
        "LOCK",
        "LONG_ARROW_DOWN",
        "LONG_ARROW_LEFT",
        "LONG_ARROW_RIGHT",
        "LONG_ARROW_UP",
        "MAGIC",
        "MAGNET",
        "MAIL_FORWARD",
        "MAIL_REPLY",
        "MAIL_REPLY_ALL",
        "MALE",
        "MAP",
        "MAP_MARKER",
        "MAP_ALT",
        "MAP_PIN",
        "MAP_SIGNS",
        "MARS",
        "MARS_DOUBLE",
        "MARS_STROKE",
        "MARS_STROKE_H",
        "MARS_STROKE_V",
        "MAXCDN",
        "MEANPATH",
        "MEDIUM",
        "MEDKIT",
        "MEH_ALT",
        "MERCURY",
        "MICROPHONE",
        "MICROPHONE_SLASH",
        "MINUS",
        "MINUS_CIRCLE",
        "MINUS_SQUARE",
        "MINUS_SQUARE_ALT",
        "MIXCLOUD",
        "MOBILE",
        "MOBILE_PHONE",
        "MODX",
        "MONEY",
        "MOON_ALT",
        "MORTAR_BOARD",
        "MOTORCYCLE",
        "MOUSE_POINTER",
        "MUSIC",
        "NAVICON",
        "NEUTER",
        "NEWSPAPER_ALT",
        "OBJECT_GROUP",
        "OBJECT_UNGROUP",
        "ODNOKLASSNIKI",
        "ODNOKLASSNIKI_SQUARE",
        "OPENCART",
        "OPENID",
        "OPERA",
        "OPTIN_MONSTER",
        "OUTDENT",
        "PAGELINES",
        "PAINT_BRUSH",
        "PAPER_PLANE",
        "PAPER_PLANE_ALT",
        "PAPERCLIP",
        "PARAGRAPH",
        "PASTE",
        "PAUSE",
        "PAUSE_CIRCLE",
        "PAUSE_CIRCLE_ALT",
        "PAW",
        "PAYPAL",
        "PENCIL",
        "PENCIL_SQUARE",
        "PENCIL_SQUARE_ALT",
        "PERCENT",
        "PHONE",
        "PHONE_SQUARE",
        "PHOTO",
        "PICTURE_ALT",
        "PIE_CHART",
        "PIED_PIPER",
        "PIED_PIPER_ALT",
        "PINTEREST",
        "PINTEREST_P",
        "PINTEREST_SQUARE",
        "PLANE",
        "PLAY",
        "PLAY_CIRCLE",
        "PLAY_CIRCLE_ALT",
        "PLUG",
        "PLUS",
        "PLUS_CIRCLE",
        "PLUS_SQUARE",
        "PLUS_SQUARE_ALT",
        "POWER_OFF",
        "PRINT",
        "PRODUCT_HUNT",
        "PUZZLE_PIECE",
        "QQ",
        "QRCODE",
        "QUESTION",
        "QUESTION_CIRCLE",
        "QUOTE_LEFT",
        "QUOTE_RIGHT",
        "RA",
        "RANDOM",
        "REBEL",
        "RECYCLE",
        "REDDIT",
        "REDDIT_ALIEN",
        "REDDIT_SQUARE",
        "REFRESH",
        "REGISTERED",
        "REMOVE",
        "RENREN",
        "REORDER",
        "REPEAT",
        "REPLY",
        "REPLY_ALL",
        "RETWEET",
        "RMB",
        "ROAD",
        "ROCKET",
        "ROTATE_LEFT",
        "ROTATE_RIGHT",
        "ROUBLE",
        "RSS",
        "RSS_SQUARE",
        "RUB",
        "RUBLE",
        "RUPEE",
        "SAFARI",
        "SAVE",
        "SCISSORS",
        "SCRIBD",
        "SEARCH",
        "SEARCH_MINUS",
        "SEARCH_PLUS",
        "SELLSY",
        "SEND",
        "SEND_ALT",
        "SERVER",
        "SHARE",
        "SHARE_ALT",
        "SHARE_ALT_SQUARE",
        "SHARE_SQUARE",
        "SHARE_SQUARE_ALT",
        "SHEKEL",
        "SHEQEL",
        "SHIELD",
        "SHIP",
        "SHIRTSINBULK",
        "SHOPPING_BAG",
        "SHOPPING_BASKET",
        "SHOPPING_CART",
        "SIGN_IN",
        "SIGN_OUT",
        "SIGNAL",
        "SIMPLYBUILT",
        "SITEMAP",
        "SKYATLAS",
        "SKYPE",
        "SLACK",
        "SLIDERS",
        "SLIDESHARE",
        "SMILE_ALT",
        "SOCCER_BALL_ALT",
        "SORT",
        "SORT_ALPHA_ASC",
        "SORT_ALPHA_DESC",
        "SORT_AMOUNT_ASC",
        "SORT_AMOUNT_DESC",
        "SORT_ASC",
        "SORT_DESC",
        "SORT_DOWN",
        "SORT_NUMERIC_ASC",
        "SORT_NUMERIC_DESC",
        "SORT_UP",
        "SOUNDCLOUD",
        "SPACE_SHUTTLE",
        "SPINNER",
        "SPOON",
        "SPOTIFY",
        "SQUARE",
        "SQUARE_ALT",
        "STACK_EXCHANGE",
        "STACK_OVERFLOW",
        "STAR",
        "STAR_HALF",
        "STAR_HALF_EMPTY",
        "STAR_HALF_FULL",
        "STAR_HALF_ALT",
        "STAR_ALT",
        "STEAM",
        "STEAM_SQUARE",
        "STEP_BACKWARD",
        "STEP_FORWARD",
        "STETHOSCOPE",
        "STICKY_NOTE",
        "STICKY_NOTE_ALT",
        "STOP",
        "STOP_CIRCLE",
        "STOP_CIRCLE_ALT",
        "STREET_VIEW",
        "STRIKETHROUGH",
        "STUMBLEUPON",
        "STUMBLEUPON_CIRCLE",
        "SUBSCRIPT",
        "SUBWAY",
        "SUITCASE",
        "SUN_ALT",
        "SUPERSCRIPT",
        "SUPPORT",
        "TABLE",
        "TABLET",
        "TACHOMETER",
        "TAG",
        "TAGS",
        "TASKS",
        "TAXI",
        "TELEVISION",
        "TENCENT_WEIBO",
        "TERMINAL",
        "TEXT_HEIGHT",
        "TEXT_WIDTH",
        "TH",
        "TH_LARGE",
        "TH_LIST",
        "THUMB_TACK",
        "THUMBS_DOWN",
        "THUMBS_ALT_DOWN",
        "THUMBS_ALT_UP",
        "THUMBS_UP",
        "TICKET",
        "TIMES",
        "TIMES_CIRCLE",
        "TIMES_CIRCLE_ALT",
        "TINT",
        "TOGGLE_DOWN",
        "TOGGLE_LEFT",
        "TOGGLE_OFF",
        "TOGGLE_ON",
        "TOGGLE_RIGHT",
        "TOGGLE_UP",
        "TRADEMARK",
        "TRAIN",
        "TRANSGENDER",
        "TRANSGENDER_ALT",
        "TRASH",
        "TRASH_ALT",
        "TREE",
        "TRELLO",
        "TRIPADVISOR",
        "TROPHY",
        "TRUCK",
        "TRY",
        "TTY",
        "TUMBLR",
        "TUMBLR_SQUARE",
        "TURKISH_LIRA",
        "TV",
        "TWITCH",
        "TWITTER",
        "TWITTER_SQUARE",
        "UMBRELLA",
        "UNDERLINE",
        "UNDO",
        "UNIVERSITY",
        "UNLINK",
        "UNLOCK",
        "UNLOCK_ALT",
        "UNSORTED",
        "UPLOAD",
        "USB",
        "USD",
        "USER",
        "USER_MD",
        "USER_PLUS",
        "USER_SECRET",
        "USER_TIMES",
        "USERS",
        "VENUS",
        "VENUS_DOUBLE",
        "VENUS_MARS",
        "VIACOIN",
        "VIDEO_CAMERA",
        "VIMEO",
        "VIMEO_SQUARE",
        "VINE",
        "VK",
        "VOLUME_DOWN",
        "VOLUME_OFF",
        "VOLUME_UP",
        "WARNING",
        "WECHAT",
        "WEIBO",
        "WEIXIN",
        "WHATSAPP",
        "WHEELCHAIR",
        "WIFI",
        "WIKIPEDIA_W",
        "WINDOWS",
        "WON",
        "WORDPRESS",
        "WRENCH",
        "XING",
        "XING_SQUARE",
        "Y_COMBINATOR",
        "Y_COMBINATOR_SQUARE",
        "YAHOO",
        "YC",
        "YC_SQUARE",
        "YELP",
        "YEN",
        "YOUTUBE",
        "YOUTUBE_PLAY"
    };
}
