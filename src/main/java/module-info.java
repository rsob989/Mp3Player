module mp3player {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports pl.project.mp3player.main to javafx.graphics;
    opens pl.project.mp3player.controller to javafx.fxml;
}