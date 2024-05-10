package com.example.player;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {

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

    private File file;
    private MediaPlayer mediaPlayer;
    private Media media;
    private Boolean isPlaying = false;
    private Timer timer;
    private TimerTask task;

    public void playPaue(){
        if (isPlaying){
            pause();
        }else {
            play();
        }
    }
    public void play(){
        if (mediaPlayer != null){
            beginTimer();
            mediaPlayer.play();
            isPlaying = true;
            playPauseButton.setText("⏸");
        }
    }
    public void pause(){
        if (mediaPlayer != null){
            mediaPlayer.pause();
            endTimer();
            isPlaying = false;
            playPauseButton.setText("▶");
        }
    }
    public void open() {

        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) openButton.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        player.setMediaPlayer(mediaPlayer);
        isPlaying = true;
        beginTimer();
        mediaPlayer.play();



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
                mediaPlayer.play();
            }
        });

    }

    public void beginTimer() {

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    double total = media.getDuration().toSeconds();
                    double current = mediaPlayer.getCurrentTime().toSeconds();
                    if (!progressSlider.isPressed()) {
                        progressSlider.setValue((current / total) * 100);
                    }
                }

            }

        };
        timer.scheduleAtFixedRate(task, 1000, 1000);

    }

    public void endTimer() {
        timer.cancel();
    }



}