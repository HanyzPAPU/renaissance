package org.renaissance.jsondb;

import org.renaissance.Benchmark;
import org.renaissance.BenchmarkContext;
import org.renaissance.BenchmarkResult;
import org.renaissance.BenchmarkResult.Validators;
import org.renaissance.License;

import static org.renaissance.Benchmark.*;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Random;

import io.jsondb.JsonDBTemplate;

import org.renaissance.jsondb.model.*;;

@Name("jsondb-bench")
@Group("jsondb")
@Summary("Simulates a simple application usng JsonDB")
@Licenses(License.MIT)
// TODO: add supports jvm
@Repetitions(1) //TODO: change
public final class JsonDBBench implements Benchmark {

  private JsonDBTemplate jsonDBTemplate;

  @Override
  public void setUpBeforeAll(BenchmarkContext c){
    String dbFilesLocation = c.scratchDirectory().toString();
    String dbImageLocation = "benchmarks/jsondb/src/main/java/org/renaissance/jsondb/resources/artists.json";
    String baseScanPackage = "org.renaissance.jsondb.model";

    // Initialize an empty DB
    this.jsonDBTemplate = new JsonDBTemplate(dbFilesLocation, baseScanPackage);
    this.jsonDBTemplate.createCollection(Artist.class);


    // Copy the prepared data to the db document
    Path dbImageLocationPath = Paths.get(System.getProperty("user.dir"), dbImageLocation);
    Path dbFilesLocationPath = Paths.get(dbFilesLocation, "artists.json");

    try {
      Files.copy(dbImageLocationPath, dbFilesLocationPath, StandardCopyOption.REPLACE_EXISTING);
    }
    catch (IOException e){
      throw new AssertionError("Could not copy the database image to scratch directory!\n" + e.getMessage());
    }

    // Update the DD
    this.jsonDBTemplate.reLoadDB();
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
