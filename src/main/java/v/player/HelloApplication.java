package v.player;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("player_GUI.fxml"));
        scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Video Player");
        stage.setScene(scene);
        stage.show();
        stage.setWidth(800);
        stage.setHeight(550);
        //stage.setResizable(false);
        stage.setOnCloseRequest(e -> Platform.exit());
    }


    public static void main(String[] args) {
        launch();
    }
}