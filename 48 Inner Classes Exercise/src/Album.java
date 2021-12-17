import java.util.ArrayList;
import java.util.LinkedList;

public class Album {

    private String name;
    private String artist;
    private SongList songs;

    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.songs = new SongList();
    }

    public boolean addSong(String title, double duration){
        Song song = new Song(title,duration);
        return songs.add(song);
    }



//    public boolean addSong(String title, double duration) {
//        if(findSong(title) == null) {
//            this.songs.add(new Song(title, duration));
//            return true;
//        }
//        return false;
//    }

//    private Song findSong(String title) {
//        for(Song checkedSong: this.songList.songs) {
//            if(checkedSong.getTitle().equals(title)) {
//                return checkedSong;
//            }
//        }
//        return null;
//    }

    public boolean addToPlayList(int trackNumber, LinkedList<Song> playList) {

        if(songs.findSong(trackNumber) != null) {
            playList.add(songs.findSong(trackNumber));
            return true;
        }
        System.out.println("This album does not have a track " + trackNumber);
        return false;
    }

    public boolean addToPlayList(String title, LinkedList<Song> playList) {

        if(songs.findSong(title) != null) {
            playList.add(songs.findSong(title));
            return true;
        }
        System.out.println("The song " + title + " is not in this album");
        return false;
    }

    public static class SongList{

        private ArrayList<Song> songs;

        private SongList() {
            this.songs = new ArrayList<Song>();
        }

        private boolean add(Song song){
            if(!songs.contains(song)) {
                this.songs.add(song);
                return true;
            }
            return false;
        }

        private Song findSong(String title) {
            for(Song checkedSong: this.songs) {
                if(checkedSong.getTitle().equals(title)) {
                    return checkedSong;
                }
            }
            return null;
        }

        private Song findSong(int trackNumber) {
            int index = trackNumber - 1;
            if ((index >= 0) && (index <= this.songs.size())) {
                return this.songs.get(index);
            }
            return null;
        }
    }
}
