package com.example.player;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private File file;
    private MediaPlayer medaPlayer;
    private Media media;


    public void open() {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) openButton.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        player.setMediaPlayer(mediaPlayer);

    }



}