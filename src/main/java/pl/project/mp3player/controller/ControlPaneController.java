package pl.project.mp3player.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

public class ControlPaneController {

    @FXML
    private Button previousButton;

    @FXML
    private ToggleButton playButton;

    @FXML
    private Button nextButton;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider progressSlider;

    public Button getPreviousButton() {
        return previousButton;
    }

    public ToggleButton getPlayButton() {
        return playButton;
    }

    public Button getNextButton() {
        return nextButton;
    }

    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    public Slider getProgressSlider() {
        return progressSlider;
    }

    public void initialize() {
        configureButtons();
        configureSlider();
    }

    public void configureButtons() {
        previousButton.setOnAction(x -> System.out.println("Previous Button"));
        nextButton.setOnAction(x -> System.out.println("Next Button"));
        playButton.setOnAction(x -> {
            if (playButton.isSelected()) {
                System.out.println("Play");
            } else {
                System.out.println("Stop");
            }
        });
    }

    public void configureSlider() {
        volumeSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> System.out.println("Zmiana głośności " + newValue.doubleValue()));
        progressSlider.valueProperty().addListener(
                ((observableValue, oldValue, newValue) -> System.out.println("Przesunięcie piosenki")));

    }

}
