package v.player;

import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControllerTest {
    @Mock
    private VBox scene;

    @Mock
    private MediaView player;

    @Mock
    private HBox controls;

    @Mock
    private ChoiceBox speedBox;

    @Mock
    private MediaPlayer mediaPlayer;

    @InjectMocks
    private Controller controller;

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
    public void testSetScene() {
        when(scene.getWidth()).thenReturn(800.0);
        when(scene.getHeight()).thenReturn(600.0);

        controller.setScene();

        verify(controls).setPrefWidth(760.0);
        verify(player).setFitWidth(720.0);
        verify(player).setFitHeight(450.0);
    }

    @Test
    public void testSetSpeed() {
        when(speedBox.getValue()).thenReturn(1.0);

        controller.setSpeed(null);

        verify(mediaPlayer).setRate(1.0);
        assertEquals(1.0, controller.getSpeed());
    }
}
