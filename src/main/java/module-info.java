module com.example.player {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires java.datatransfer;

    opens com.example.player to javafx.fxml;
    exports com.example.player;
}
