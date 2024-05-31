package v.player;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Timer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ControlsTest {

    @Mock
    private MediaPlayer mediaPlayer;

    @Mock
    private Button playPauseButton;

    @Mock
    private Media media;

    @Mock
    private Slider slider;

    @Mock
    private Timer timer;

    @InjectMocks
    Controls controls;

    @BeforeAll
    static void beforeAll() {
        try {
            Platform.startup(() -> {
            });
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void play() {
        controls.play(mediaPlayer, playPauseButton, 50.0, media, slider, 1.0);
        verify(mediaPlayer).play();
    }

    @Test
    void pause() {
        controls.pause(mediaPlayer, playPauseButton);

        verify(mediaPlayer).pause();
        verify(timer).cancel();
    }

    @Test
    void stop() {
        controls.stop(mediaPlayer, playPauseButton);

        verify(mediaPlayer).stop();
        verify(timer).cancel();
    }

    @Test
    void endTimer() {
        controls.endTimer();

        verify(timer).cancel();
    }

    @Test
    void beginTimer() {
        controls.beginTimer(mediaPlayer, media, slider, playPauseButton);

        assertNotNull(controls.getTask());
    }
}