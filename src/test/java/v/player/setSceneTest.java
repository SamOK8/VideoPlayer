package v.player;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ControllerTest {
        private VBox scene;
        private MediaView player;
        private HBox controls;

        @BeforeEach
        public void setUp () {
            scene = mock(VBox.class);
            player = mock(MediaView.class);
            controls = mock(HBox.class);
        }

        @Test
        public void testSetScene () {
            MediaPlayerWrapper mediaPlayerWrapper = new MediaPlayerWrapper();
            mediaPlayerWrapper.scene = scene;
            mediaPlayerWrapper.player = player;
            mediaPlayerWrapper.controls = controls;


            when(scene.getWidth()).thenReturn(800.0);
            when(scene.getHeight()).thenReturn(600.0);

            mediaPlayerWrapper.setScene();

            verify(controls).setPrefWidth(760.0);
            verify(player).setFitWidth(720.0);
            verify(player).setFitHeight(450.0);
        }
    }

    class MediaPlayerWrapper {

        VBox scene;
        MediaView player;
        HBox controls;

        public void setScene() {
            controls.setPrefWidth(scene.getWidth() - 40);
            player.setFitWidth(scene.getWidth() - 80);
            player.setFitHeight(scene.getHeight() - 150);
        }
}