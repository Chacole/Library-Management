package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button testButton;

    @FXML
    protected void initialize() {
        testButton.setOnMouseEntered(e -> {
            testButton.setScaleX(1.2);
            testButton.setScaleY(1.2);
        });

        testButton.setOnMouseExited(e -> {
            testButton.setScaleX(1.0);
            testButton.setScaleY(1.0);
        });
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("The next");
        String sound = getClass().getResource("/error_CDOxCYm.mp3").toExternalForm();
        Media media = new Media(sound);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}