package pl.project.mp3player.controller;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import pl.project.mp3player.mp3.Mp3Parser;
import pl.project.mp3player.mp3.Mp3Player;
import pl.project.mp3player.mp3.Mp3Song;

import java.io.File;
import java.io.IOException;

public class MainController {

    @FXML
    private ContentPaneController contentPaneController;

    @FXML
    private ControlPaneController controlPaneController;

    @FXML
    private MenuPaneController menuPaneController;

    private Mp3Player player;

    public void initialize(){
        createPlayer();
        configureTableClick();
        configureButtons();
        configureMenu();
    }

    private void createPlayer(){
        ObservableList<Mp3Song> items = contentPaneController.getContentTable().getItems();
        player = new Mp3Player(items);
    }

    private void configureTableClick(){
        TableView<Mp3Song> contentTable = contentPaneController.getContentTable();
        contentTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
            if(event.getClickCount()==2){
                int selectedIndex = contentTable.getSelectionModel().getSelectedIndex();
                playSelectedSong(selectedIndex);
            }
        });
    }

    private void playSelectedSong(int selectedIndex){
        player.loadSong(selectedIndex);
        configureProgressBar();
        configureVolume();
        controlPaneController.getPlayButton().setSelected(true);
    }

    private void configureProgressBar(){
        Slider progressSlider = controlPaneController.getProgressSlider();
        player.getMediaPlayer().setOnReady(()->progressSlider.setMax(player.getLoadedSongLength()));
        player.getMediaPlayer().currentTimeProperty().addListener(((observableValue, duration, t1) -> {
            progressSlider.setValue(t1.toSeconds());
        }));
        progressSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            if(progressSlider.isValueChanging()){
                player.getMediaPlayer().seek(Duration.seconds(t1.doubleValue()));
            }
        }));
    }

    private void configureVolume(){
        Slider volumeSlider = controlPaneController.getVolumeSlider();
        volumeSlider.valueProperty().unbind();
        volumeSlider.setMax(1.0);
        volumeSlider.valueProperty().bindBidirectional(player.getMediaPlayer().volumeProperty());
    }

    private void configureButtons(){
        TableView<Mp3Song> contentTable = contentPaneController.getContentTable();
        ToggleButton playButton = controlPaneController.getPlayButton();
        Button prevButton = controlPaneController.getPreviousButton();
        Button nextButton = controlPaneController.getNextButton();

        playButton.setOnAction(event->{
            if (playButton.isSelected()) {
                player.play();
            } else {
                player.stop();
            }
        });

        nextButton.setOnAction(event->{
            contentTable.getSelectionModel().select(contentTable.getSelectionModel().getSelectedIndex()+1);
            playSelectedSong(contentTable.getSelectionModel().getSelectedIndex());
        });

        prevButton.setOnAction(event->{
            contentTable.getSelectionModel().select(contentTable.getSelectionModel().getSelectedIndex()-1);
            playSelectedSong(contentTable.getSelectionModel().getSelectedIndex());
        });
    }

    private void configureMenu(){
        MenuItem openFile = menuPaneController.getFileMenuItem();
        MenuItem openDir = menuPaneController.getDirMenuItem();

        openFile.setOnAction(event->{
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mp3", "*.mp3"));
            File file = fc.showOpenDialog(new Stage());
            try{
                contentPaneController.getContentTable().getItems().add(Mp3Parser.createMp3Song(file));
                showMessage("File loaded " + file.getName());
            } catch (Exception e){
                showMessage("File cannot be loaded " + file.getName());
            }
        });

        openDir.setOnAction(event->{
            DirectoryChooser dc = new DirectoryChooser();
            File dir = dc.showDialog(new Stage());
            try{
                contentPaneController.getContentTable().getItems().addAll(Mp3Parser.createMp3List(dir));
                showMessage("Directory data loaded: " + dir.getName());
            } catch (Exception e){
                showMessage("Directory data cannot be loaded");
            }
        });
    }

    private void showMessage(String message){
        controlPaneController.getMessageTextField().setText(message);
    }



}
