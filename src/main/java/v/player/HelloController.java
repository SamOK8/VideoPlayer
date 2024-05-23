package v.player;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
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

    public void initialize(){

        for (int i = 0; i < speeds.length; i++){
            speedBox.getItems().add(speeds[i]);
        }
        speedBox.setOnAction(this::setSpeed);
    }

    public void playPaue(){
        if(mediaPlayer != null){
            if (MediaPlayer.Status.PLAYING.equals(mediaPlayer.getStatus())){
                controls.pause(mediaPlayer, playPauseButton);

            }else {
                controls.play(mediaPlayer,playPauseButton,volume,media,progressSlider);
            }
        }
    }
    /**
     *
     */
    public void open() {

        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) openButton.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
//            media = new Media(file.toURI().toString());
//            mediaPlayer = new MediaPlayer(media);
//            player.setMediaPlayer(mediaPlayer);
//            play();

            files.add(file);
            index++;
            final int currentIndex = index;
            Button button = new Button(files.get(currentIndex).getName());
            pane.add(button, 1, currentIndex);
            if (currentIndex == 0){
                changeMedia(currentIndex);
            }
            button.setOnAction(actionEvent -> {
                controls.stop(mediaPlayer, playPauseButton);
                changeMedia(currentIndex);
            });
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> mediaPlayer.setVolume(newValue.doubleValue() / 100));
            volumeSlider.setValue(volume);


            progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (progressSlider.isPressed()) {
                    Duration total = media.getDuration();
                    System.out.println();
                    double value = newValue.doubleValue();
                    Duration position = total.multiply(value / 100);
                    System.out.println(position);
                    mediaPlayer.stop();
                    mediaPlayer.setStartTime(position);
                    controls.play(mediaPlayer,playPauseButton,volume,media,progressSlider);

                }
            });
        }
        //.....................................................
        Scene scene = playPauseButton.getScene();
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

    //......................................................
        videos.setContent(pane);
    }
    /**
     * This method is called when fullScreenButton is pressed and it makes fullscreen.
     */
    public void fullScreen(){
        if(!((Stage)player.getScene().getWindow()).isFullScreen()){
            ((Stage)player.getScene().getWindow()).setFullScreen(true);

            //setScene();
            player.setFitWidth(scene.getWidth());
            player.setFitHeight(scene.getHeight()-70);
        } else if (((Stage) player.getScene().getWindow()).isFullScreen()) {
            ((Stage)player.getScene().getWindow()).setFullScreen(false);
            setScene();
        }


    }
    /**
     * Calculates size of the window.
     */
    private void setScene(){
        Controls.setPrefWidth(scene.getWidth()-40);
        player.setFitWidth(scene.getWidth()-80);
        player.setFitHeight(scene.getHeight()-150);
    }
    /**
     * Sets speed of video
     * @param event
     */
    private void setSpeed(Event event){
        if (mediaPlayer != null) {
            mediaPlayer.setRate(Double.parseDouble(String.valueOf(speedBox.getValue())));

        }
    }
    public void resizeWhenMoving(){

        scene.setOnMouseMoved(m -> {
            setScene();
        });

    }
    public void changeMedia(int index){
        if (index == 0){
            media = new Media(files.get(index).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            player.setMediaPlayer(mediaPlayer);
            controls.play(mediaPlayer,playPauseButton,volume,media,progressSlider);
        }else {
            volume = mediaPlayer.getVolume();
            media = new Media(files.get(index).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            player.setMediaPlayer(mediaPlayer);
            controls.play(mediaPlayer,playPauseButton,volume,media,progressSlider);
        }
    }


}