package v.player;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class Controls {
    private Timer timer;
    private TimerTask task;

    /**
     * play the media on the specified media player with the specified volume, speed and progress
     *
     * @param mediaPlayer
     * @param playPauseButton
     * @param volume
     * @param media
     * @param progressSlider
     * @param speed
     */

    public void play(MediaPlayer mediaPlayer, Button playPauseButton, double volume, Media media, Slider progressSlider, double speed) {
        if (mediaPlayer != null) {
            beginTimer(mediaPlayer, media, progressSlider);
            mediaPlayer.setVolume(volume);
            mediaPlayer.setRate(speed);
            mediaPlayer.play();
            playPauseButton.setText("⏸");

        }
    }

    /**
     * Pause media player and change the button graphics to play symbol for the next action
     *
     * @param mediaPlayer
     * @param playPauseButton
     */
    public void pause(MediaPlayer mediaPlayer, Button playPauseButton) {
        if (mediaPlayer != null) {
            endTimer();
            mediaPlayer.pause();
            playPauseButton.setText("▶");
        }
    }

    /**
     * Stop the media player and change the button graphics to play symbol for the next action
     *
     * @param mediaPlayer
     * @param playPauseButton
     */
    public void stop(MediaPlayer mediaPlayer, Button playPauseButton) {
        if (mediaPlayer != null) {
            endTimer();
            mediaPlayer.stop();
            playPauseButton.setText("▶");
        }
    }

    /**
     * End the progress bar update timer
     */
    public void endTimer() {
        timer.cancel();
        task.cancel();
    }

    /**
     * Start the progress bar update timer
     *
     * @param mediaPlayer
     * @param media
     * @param progressSlider
     */
    public void beginTimer(MediaPlayer mediaPlayer, Media media, Slider progressSlider) {

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (mediaPlayer != null && media != null) {
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

    public Timer getTimer() {
        return timer;
    }

    public TimerTask getTask() {
        return task;
    }
}
