package pl.project.mp3player.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import pl.project.mp3player.mp3.Mp3Song;

import java.io.File;
import java.io.IOException;

public class ContentPaneController {

    private static final String TITLE = "Title";
    private static final String AUTHOR = "Author";
    private static final String ALBUM = "Album";

    @FXML
    private TableView<Mp3Song> contentTable;

    public void initialize(){
        createTable();
        createMp3List();
    }

    private void createTable(){
        TableColumn<Mp3Song, String> titleColumn = new TableColumn<>(TITLE);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Mp3Song, String> authorColumn = new TableColumn<>(AUTHOR);
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Mp3Song, String> albumColumn = new TableColumn<>(ALBUM);
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));

        contentTable.getColumns().add(titleColumn);
        contentTable.getColumns().add(authorColumn);
        contentTable.getColumns().add(albumColumn);
    }

    private void createMp3List(){
        ObservableList<Mp3Song> items = contentTable.getItems();
        Mp3Song mp3Song = createMp3Song("BardzoProsze.mp3");
        items.add(mp3Song);
    }

    private Mp3Song createMp3Song(String path){
        File file = new File(path);
        try {
            MP3File mp3File = new MP3File(file);
            String filePath = file.getAbsolutePath();
            String title = mp3File.getID3v2Tag().getSongTitle();
            String album = mp3File.getID3v2Tag().getAlbumTitle();
            String author = mp3File.getID3v2Tag().getLeadArtist();
            return new Mp3Song(title, author, album, filePath);
        } catch(TagException | IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
