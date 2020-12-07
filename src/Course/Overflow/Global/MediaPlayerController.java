package Course.Overflow.Global;

import Course.Overflow.Global.Layout.FloatingPane;
import com.jfoenix.controls.JFXSlider;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaPlayerController extends FloatingPane implements Initializable {

    @FXML
    private AnchorPane videoplayerpane;
    @FXML
    private MediaView mediaview;
    @FXML
    private JFXSlider volumeslider;
    @FXML
    private JFXSlider mediaslider;
    @FXML
    private Button volume;
    @FXML
    private Button left;
    @FXML
    private Button right;
    @FXML
    private Button play;
    private MediaPlayer mediaPlayer;
    private Media media;
    @FXML
    private Button select;
    private boolean playing;
    @FXML
    private ImageView playimage;
    @FXML
    private ImageView volumeimage;
    @FXML
    private Label playTime;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    @FXML
    private ChoiceBox speed;
    private File file;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.backPane = videoplayerpane;
        setSpeed();
        setTooltip();
//        addListener();
    }

    private void setTooltip() {
        right.setTooltip(new Tooltip("forward 6s"));
        left.setTooltip(new Tooltip("backward 6s"));
        speed.setTooltip(new Tooltip("Select speed"));
    }

    private void setSpeed() {

        speed.setItems(FXCollections.observableArrayList("1.0x", "0.75x", "1.25x"));
        speed.setValue("1.0x");
        speed.valueProperty().addListener((Observable observable) -> {
            if (media != null) {
                String rate = speed.getSelectionModel().getSelectedItem().toString();
                if (rate != null && !rate.isEmpty()) {
                    if (rate.equals("0.75x")) {
                        mediaPlayer.setRate(0.75);
                    } else if (rate.equals("1.25x")) {
                        mediaPlayer.setRate(1.25);
                    } else {
                        mediaPlayer.setRate(1.0);
                    }
                }
            }
        });
    }

    private void addListener() {
        speed.setOnMouseClicked(event -> {
        });
//        select.setOnMouseClicked(event -> {
//            FileChooser filechooser = new FileChooser();
//            file = filechooser.showOpenDialog(null);
//            media = new Media(file.toURI().toString());
//            mediaPlayer = new MediaPlayer(media);
//            mediaview.setMediaPlayer(mediaPlayer);

            volumeslider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    if (volumeslider.getValue() > 0) {
                        volumeimage.setImage(new Image(getClass().getResourceAsStream("/Course/Overflow/Files/Icon/soundon.png")));
                    } else {
                        volumeimage.setImage(new Image(getClass().getResourceAsStream("/Course/Overflow/Files/Icon/soundoff.png")));
                    }
                    mediaPlayer.setVolume(volumeslider.getValue() / 100);
                }
            });

            mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    updateValues();
                }
            });

            mediaPlayer.setOnPlaying(new Runnable() {
                public void run() {
                    if (stopRequested) {
                        mediaPlayer.pause();
                        stopRequested = false;
                    } else {
                        playimage.setImage(new Image(getClass().getResourceAsStream("/Course/Overflow/Files/Icon/pause.png")));
                    }
                }
            });

            mediaPlayer.setOnPaused(new Runnable() {
                public void run() {
                    playimage.setImage(new Image(getClass().getResourceAsStream("/Course/Overflow/Files/Icon/play.png")));
                }
            });

            mediaPlayer.setOnReady(new Runnable() {
                public void run() {
                    duration = mediaPlayer.getMedia().getDuration();
                    updateValues();
                }
            });

            mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    if (!repeat) {
                        playimage.setImage(new Image(getClass().getResourceAsStream("/Course/Overflow/Files/Icon/play.png")));
                        stopRequested = true;
                        atEndOfMedia = true;
                    }
                }
            });

            mediaslider.valueProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    if (mediaslider.isValueChanging()) {
                        mediaPlayer.seek(duration.multiply(mediaslider.getValue() / 100.0));
                    }
                }
            });

        right.setOnMouseClicked(event -> {
            if (media != null) {
                mediaPlayer.seek(duration.multiply(((mediaslider.getValue() + 1.0) / 100.0)));
            }
        });
        left.setOnMouseClicked(event -> {
            if (media != null) {
                mediaPlayer.seek(duration.multiply(((mediaslider.getValue() - 1.0) / 100.0)));
            }
        });

        play.setOnMouseClicked(event -> {
            if (media != null) {
                if (playing) {
                    playimage.setImage(new Image(getClass().getResourceAsStream("/Course/Overflow/Files/Icon/pause.png")));
                    mediaPlayer.pause();
                    playing = false;
                } else {
                    playimage.setImage(new Image(getClass().getResourceAsStream("/Course/Overflow/Files/Icon/play.png")));
                    mediaPlayer.play();
                    playing = true;
                }
            }
        });
        volume.setOnMouseClicked(event -> {
            if (media != null) {
                if (mediaPlayer.isMute()) {
                    volumeimage.setImage(new Image(getClass().getResourceAsStream("/Course/Overflow/Files/Icon/soundon.png")));
                    mediaPlayer.setMute(false);
                    volumeslider.setValue(50);
                } else {
                    volumeimage.setImage(new Image(getClass().getResourceAsStream("/Course/Overflow/Files/Icon/soundoff.png")));
                    mediaPlayer.setMute(true);
                    volumeslider.setValue(0);
                }

            }
        });
        closeTransition.setOnFinished((event) -> {
            mediaPlayer.pause();
        });
    }

    private void updateValues() {
        if (playTime != null && mediaslider != null && volumeslider != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    duration = mediaPlayer.getMedia().getDuration();
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    playTime.setText(formatTime(currentTime, duration));
                    mediaslider.setDisable(duration.isUnknown());
                    if (!mediaslider.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !mediaslider.isValueChanging()) {
                        mediaslider.setValue(currentTime.divide(duration).toMillis()
                                * 100.0);
                    }
                    if (!volumeslider.isValueChanging()) {
                        volumeslider.setValue((int) Math.round(mediaPlayer.getVolume()
                                * 100));
                    }
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }

    public void setFile(File file) {
        this.file = file;
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaview.setMediaPlayer(mediaPlayer);
        volumeslider.setValue(mediaPlayer.getVolume() * 100);
//        updateValues();
        addListener();
        playing = true;
        mediaPlayer.play();
    }

}
/*
String sql = "SELECT * FROM NOTIFICATION WHERE ID = #";
        ResultSet rs = DB.executeQuery(sql, id.toString());
        try {
            if(rs.next())
            {
                this.id = id;
                this.userId = rs.getString("USER_ID");
                this.text = rs.getString("TEXT");
                this.time = rs.getDate("TIME");
                this.seen = rs.getString("SEEN").equals("T") ? true:false;
                this.course = new Course(new Integer(rs.getString("COURSE_ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }   
*/