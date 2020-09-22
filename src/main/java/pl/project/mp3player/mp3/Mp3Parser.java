package pl.project.mp3player.mp3;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mp3Parser {

    public static Mp3Song createMp3Song(File file) throws IOException, TagException {
        MP3File mp3File = new MP3File(file);
        String filePath = file.getAbsolutePath();
        String title = mp3File.getID3v2Tag().getSongTitle();
        String album = mp3File.getID3v2Tag().getAlbumTitle();
        String author = mp3File.getID3v2Tag().getLeadArtist();
        return new Mp3Song(title, author, album, filePath);
    }

    public static List<Mp3Song> createMp3List(File dir) throws IOException, TagException{
        if(!dir.isDirectory()){
            throw new IllegalArgumentException("Not a directory");
        }
        List<Mp3Song> songList = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if(fileExtension.equals("mp3"))
                songList.add(createMp3Song(file));
        }

        return songList;
    }
}
