package com.example.player;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class HelloController {

    @FXML
    private Button openButton;
    @FXML
    private MediaView player;
    @FXML
    private Button playPauseButton;

    private File file;
    private MediaPlayer mediaPlayer;
    private Media media;
    private Boolean isPlaying = false;

    public void playPaue(){
        if (isPlaying){
            pause();
        }else {
            play();
        }
    }
    public void play(){
        if (mediaPlayer != null){
            mediaPlayer.play();
            isPlaying = true;
            playPauseButton.setText("⏸");
        }
    }
    public void pause(){
        if (mediaPlayer != null){
            mediaPlayer.pause();
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
        mediaPlayer.play();

    }



}