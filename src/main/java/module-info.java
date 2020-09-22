module mp3player {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires jid3lib;

    exports pl.project.mp3player.main to javafx.graphics;
    opens pl.project.mp3player.controller to javafx.fxml;
    opens pl.project.mp3player.mp3 to javafx.base;
}