package org.renaissance.jsondb;

import org.renaissance.Benchmark;
import org.renaissance.BenchmarkContext;
import org.renaissance.BenchmarkResult;
import org.renaissance.BenchmarkResult.Validators;
import org.renaissance.License;

import static org.renaissance.Benchmark.*;

import io.jsondb.JsonDBTemplate;

import org.renaissance.jsondb.model.*;;

@Name("jsondb-bench")
@Group("jsondb")
@Summary("Simulates a simple application usng JsonDB")
@Licenses(License.MIT)
// TODO: add supports jvm
@Repetitions(1) //TODO: change
// TODO: think about parameters
// TODO: configuration?
public final class JsonDBBench implements Benchmark {

  private JsonDBTemplate jsonDBTemplate;

  @Override
  public void setUpBeforeAll(BenchmarkContext c){
    //String dbFilesLocation = c.scratchDirectory().toString();
    String dbFilesLocation = "/home/honza/renaissance/target/";
    String baseScanPackage = "org.renaissance.jsondb.model";

    this.jsonDBTemplate = new JsonDBTemplate(dbFilesLocation, baseScanPackage);
    this.jsonDBTemplate.createCollection(Artist.class);

    Artist artist = new Artist();
    artist.setRealName("artist");
    Album album = new Album();
    album.setTitle("album");

    Song song1 = new Song();
    song1.setTitle("song1");

    Song song2 = new Song();
    song2.setTitle("song2");

    Song[] songs = new Song[] {
      song1, song2
    };

    album.setSongs(songs);
    artist.setAlbums(new Album[] {album});

    this.jsonDBTemplate.insert(artist);
  }

  @Override
  public void tearDownAfterAll(BenchmarkContext c){
    // TODO? remove db data
  }

  @Override
  public void setUpBeforeEach(BenchmarkContext c){
    // TODO: initialize DB data
  }

  @Override
  public void tearDownAfterEach(BenchmarkContext context){
    // TODO: empty DB state
  }

  

  @Override
  public BenchmarkResult run(BenchmarkContext context) {
    return Validators.simple("nothing", 0, 0);
  }
}
