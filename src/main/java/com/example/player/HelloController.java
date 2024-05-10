package com.example.player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
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

    private File file;
    private MediaPlayer mediaPlayer;
    private Media media;
    private Timer timer;
    private TimerTask task;
    private double[] speeds = {0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75, 2};

    public void initialize(){

        for (int i = 0; i < speeds.length; i++){
            speedBox.getItems().add(speeds[i]);
        }
        speedBox.setOnAction(this::setSpeed);

    }
    public void playPaue(){
        if(mediaPlayer != null){
            if (MediaPlayer.Status.PLAYING.equals(mediaPlayer.getStatus())){
                pause();
            }else {
                play();
            }
        }
    }
    public void play(){
        if (mediaPlayer != null){
            beginTimer();
            mediaPlayer.play();
            playPauseButton.setText("⏸");
        }
    }
    public void pause(){
        if (mediaPlayer != null){
            mediaPlayer.pause();
            endTimer();
            playPauseButton.setText("▶");
        }
    }
    public void stop(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            endTimer();
            playPauseButton.setText("▶");
        }
    }
    public void open() {

        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) openButton.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        //if (file != null) {
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            player.setMediaPlayer(mediaPlayer);
            play();
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> mediaPlayer.setVolume(newValue.doubleValue() / 100));
            volumeSlider.setValue(100);



        progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (progressSlider.isPressed()) {
                Duration total = media.getDuration();
                System.out.println();
                double value = newValue.doubleValue();
                Duration position = total.multiply(value / 100);
                System.out.println(position);
                mediaPlayer.stop();
                mediaPlayer.setStartTime(position);
                play();

            }
        });
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
     //}
    }
    public void fullScreen(){
        if(!((Stage)player.getScene().getWindow()).isFullScreen()){
            ((Stage)player.getScene().getWindow()).setFullScreen(true);
            setScene();
        } else if (((Stage) player.getScene().getWindow()).isFullScreen()) {
            ((Stage)player.getScene().getWindow()).setFullScreen(false);
            setScene();
        }


    }
    private void setScene(){
        Controls.setPrefWidth(scene.getWidth()-40);
        player.setFitWidth(scene.getWidth()-89);
        player.setFitHeight(scene.getHeight()-150);
    }
    private void setSpeed(Event event){
        if (mediaPlayer != null) {
            mediaPlayer.setRate(Double.parseDouble(String.valueOf(speedBox.getValue())));
        }
    }

    public void beginTimer() {

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (mediaPlayer != null && media != null) {
                    double total = media.getDuration().toSeconds();
                    double current = mediaPlayer. getCurrentTime().toSeconds();
                    if (!progressSlider.isPressed()) {
                        progressSlider.setValue((current / total) * 100);
                    }
                    mediaPlayer.setOnEndOfMedia(() ->
                            stop());
                }

            }

        };
        timer.scheduleAtFixedRate(task, 1000, 1000);

    }

    public void endTimer() {
        timer.cancel();
    }



}