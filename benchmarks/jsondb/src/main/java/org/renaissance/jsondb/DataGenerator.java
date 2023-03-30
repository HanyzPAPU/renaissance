package org.renaissance.jsondb;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.renaissance.jsondb.model.*;

public class DataGenerator {

    final private static int randomStringSize = 10;
    final private static int pseudonymMaxSize = 4; // Exclusive upper bound
    final private static int albumsMaxSize = 6; // Exclusive upper bound
    final private static int recordLabelsMaxSize = 3; // Exclusive upper bound
    final private static int genresMaxSize = 5; // Exclusive upper bound
    final private static int songsMaxSize = 20; // Exclusive upper bound
    final private static long randomDateEndMiliseconds = 120 * 365 * (24 * 60 * 60 * 1000); // Somewhere around 2020


    private static Set<String> usedNames = new HashSet<String>();

    public static void addUsedName(String name){
        usedNames.add(name);
    }

    private static String randomString(Random rng){
        return rng.ints('0', 'z' + 1)
        .filter(i -> (i <= '9') || (i >= 'A' && i <= 'Z') || (i >= 'a'))
        .limit(randomStringSize)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
    }

    private static String unusedName(Random rng){
        String ret = randomString(rng);
        // Simply loop until we generate a unique string
        while(usedNames.contains(ret)){
            ret = randomString(rng);
        }
        usedNames.add(ret);
        return ret;
    }

    private static String[] pseudonyms(Random rng){
        final int pseudonymSize = rng.nextInt(pseudonymMaxSize);

        if (pseudonymSize == 0) return null;

        String[] pseudonyms = new String[pseudonymSize];
        for(int i = 0; i < pseudonymSize; ++i){
            pseudonyms[i] = randomString(rng);
        }
        return pseudonyms;
    }

    private static Date date(Random rng){
        return new Date(rng.nextLong() % randomDateEndMiliseconds);
    }

    public static RecordLabel recordLabel(Random rng){
        RecordLabel recordLabel = new RecordLabel();
        recordLabel.setName(randomString(rng));
        recordLabel.setNetWorth(rng.nextInt()); // Unbound networth
        recordLabel.setCreationDate(date(rng));
        recordLabel.setHeadquarters(randomString(rng));
        return recordLabel;
    }

    private static RecordLabel[] recordLabels(Random rng){
        final int recordLabelsSize = rng.nextInt(recordLabelsMaxSize);

        if(recordLabelsSize == 0) return null;

        RecordLabel[] recordLables = new RecordLabel[recordLabelsSize];
        for(int i = 0; i < recordLabelsSize; ++i){
            recordLables[i] = recordLabel(rng);
        }
        return recordLables;
    }

    public static Genre genre(Random rng){
        Genre genre = new Genre();
        genre.setName(randomString(rng));
        genre.setDescription(randomString(rng));
        genre.setDecadeOfCreation(date(rng));
        return genre;
    }

    private static Genre[] genres(Random rng){
        final int genresSize = rng.nextInt(genresMaxSize);
        
        if (genresSize == 0) return null;

        Genre[] genres = new Genre[genresSize];
        for(int i = 0; i < genresSize; ++i){
            genres[i] = genre(rng);
        }
        return genres;
    }

    public static Song song(Random rng){
        Song song = new Song();
        song.setTitle(randomString(rng));
        song.setLength(rng.nextFloat()); // unbound song length
        song.setGenres(genres(rng));
        return song;
    }

    public static Song[] songs(Random rng){
        final int songsSize = rng.nextInt(songsMaxSize);

        if (songsSize == 0) return null;
        Song[] songs = new Song[songsSize];
        for (int i = 0; i < songsSize; ++i) {
            songs[i] = song(rng);
        }
        return songs;
    }

    public static Album album(Random rng){
        Album album = new Album();
        album.setTitle(randomString(rng));
        album.setDescription(randomString(rng));
        album.setCreationDate(date(rng));
        album.setSongs(songs(rng));
        return album;
    }

    private static Album[] albums(Random rng){
        final int albumSize = rng.nextInt(albumsMaxSize);

        if (albumSize == 0) return null;

        Album[] albums = new Album[albumSize];
        for(int i = 0; i < albumSize; ++i){
            albums[i] = album(rng);
        }
        return albums;
    }

    public static Artist artist(Random rng) {
        Artist artist = new Artist();
        artist.setRealName(unusedName(rng));
        artist.setPseudonym(pseudonyms(rng));
        artist.setDateOfBirth(date(rng));
        artist.setCareerStartDate(date(rng));
        artist.setRecordLabels(recordLabels(rng));
        artist.setAlbums(albums(rng));
        return artist;
    }
}
