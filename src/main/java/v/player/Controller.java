package v.player;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class Controller {
    @FXML
    private ChoiceBox speedBox;
    @FXML
    private Button openButton;
    @FXML
    private MediaView player;
    @FXML
    private Button playPauseButton;
    @FXML
    private Slider progressSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private HBox Controls;
    @FXML
    private VBox scene;
    @FXML
    private ScrollPane videos;

    private File file;
    private MediaPlayer mediaPlayer;
    private Media media;
    private double[] speeds = {0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75, 2};
    private ArrayList<File> files = new ArrayList<File>();
    GridPane pane = new GridPane();
    private int index = -1;
    private double volume = 100;
    Controls controls = new Controls();
    private double speed = 1;


    public void initialize() {

        for (int i = 0; i < speeds.length; i++) {
            speedBox.getItems().add(speeds[i]);
        }
        speedBox.setOnAction(this::setSpeed);

        // handle the ESC key press to escape the full screen
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String key = String.valueOf(keyEvent.getCode());
                System.out.println(key);
                if (key.equals("ESCAPE")) {
                    setScene();
                }
            }
        });

        resizeWhenMoving();

        volumeSlider.setValue(volume);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> mediaPlayer.setVolume(newValue.doubleValue() / 100));

        progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (progressSlider.isPressed()) {
                Duration total = media.getDuration();
                System.out.println();
                double value = newValue.doubleValue();
                Duration position = total.multiply(value / 100);
                System.out.println(position);
                mediaPlayer.stop();
                mediaPlayer.setStartTime(position);
                controls.play(mediaPlayer, playPauseButton, volume, media, progressSlider, getSpeed());
            }
        });
    }

    /**
     * Starts playing the video or pause if the player is already playing
     */
    public void playPaue() {
        if (mediaPlayer != null) {
            if (MediaPlayer.Status.PLAYING.equals(mediaPlayer.getStatus())) {
                controls.pause(mediaPlayer, playPauseButton);
            } else {
                controls.play(mediaPlayer, playPauseButton, volume, media, progressSlider, getSpeed());
            }
        }
    }

    /**
     * Opens a chooser to add the video file to the list
     */
    public void open() {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) openButton.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            files.add(file);
            index++;
            final int currentIndex = index;
            Button button = new Button(files.get(currentIndex).getName());
            pane.add(button, 1, currentIndex);
            if (currentIndex == 0) {
                changeMedia(currentIndex);
            }

            button.setOnAction(actionEvent -> {
                changeMedia(currentIndex);
            });

        }

        videos.setContent(pane);
    }

    /**
     * This method is called when fullScreenButton is pressed and it makes fullscreen.
     */
    public void fullScreen() {
        if (!((Stage) player.getScene().getWindow()).isFullScreen()) {
            ((Stage) player.getScene().getWindow()).setFullScreen(true);
            player.setFitWidth(scene.getWidth());
            player.setFitHeight(scene.getHeight() - 70);
        } else if (((Stage) player.getScene().getWindow()).isFullScreen()) {
            ((Stage) player.getScene().getWindow()).setFullScreen(false);
            setScene();
        }
    }

    /**
     * Calculates size of the window.
     */
    public void setScene() {
        Controls.setPrefWidth(scene.getWidth() - 40);
        player.setFitWidth(scene.getWidth() - 80);
        player.setFitHeight(scene.getHeight() - 150);
    }

    /**
     * Sets speed of video
     *
     * @param event
     */
    public void setSpeed(Event event) {
        if (mediaPlayer != null) {
            mediaPlayer.setRate(Double.parseDouble(String.valueOf(speedBox.getValue())));
            speed = Double.parseDouble(String.valueOf(speedBox.getValue()));
        }
    }

    /**
     * when mouse is moving inside the panel it will set its calculated size
     */
    public void resizeWhenMoving() {

        scene.setOnMouseMoved(m -> {
            setScene();
        });

    }

    /**
     * Switches actually playing video
     *
     * @param index - index of video to switch to
     */
    public void changeMedia(int index) {
        try {
            controls.stop(mediaPlayer, playPauseButton);
            if (index != 0) {
                volume = mediaPlayer.getVolume();
            }

            String uri = files.get(index).toURI().toString();
            media = new Media(uri);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(() ->
                    controls.stop(mediaPlayer, playPauseButton));
            player.setMediaPlayer(mediaPlayer);
            controls.play(mediaPlayer, playPauseButton, volume, media, progressSlider, getSpeed());
        } catch (MediaException e) {
            System.err.println("Error playing media: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public double getSpeed() {
        return speed;
    }


}
