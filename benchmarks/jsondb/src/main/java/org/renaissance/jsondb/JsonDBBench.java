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
import java.util.List;
import java.util.Random;

import io.jsondb.JsonDBTemplate;

import org.renaissance.jsondb.model.*;
import org.renaissance.jsondb.operations.*;

@Name("jsondb-bench")
@Group("jsondb")
@Summary("Simulates a simple application usng JsonDB")
@Licenses(License.MIT)
@Repetitions(10) //TODO: change
@Parameter(name = "operation_count_per_thread", defaultValue = "1000000")
public final class JsonDBBench implements Benchmark {

  private JsonDBTemplate jsonDBTemplate;
  private int operationCountPerThread;

  final static int CPU = Runtime.getRuntime().availableProcessors();
  private static volatile Object blackhole = null;

  @Override
  public void setUpBeforeAll(BenchmarkContext c){

    operationCountPerThread = c.parameter("operation_count_per_thread").toInteger();

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

  // @Override
  // public void tearDownAfterAll(BenchmarkContext c){
  //   // TODO? remove db data
  // }

  DatabaseOperation[][] operations;

  @Override
  public void setUpBeforeEach(BenchmarkContext c){
    // Generate operations for each thread
    operations = new DatabaseOperation[CPU][operationCountPerThread];
    Random rng = new Random();

    for(int i = 0; i < CPU; ++i){
      operations[i] = OperationGenerator.operations(operationCountPerThread, rng);
    }
  }

  @Override
  public BenchmarkResult run(BenchmarkContext context) {
    Thread[] threads = new Thread[CPU];
    for(int t = 0; t < CPU; ++t){
      final int p = t;
      final DatabaseOperation[] t_operations = operations[p];
      final JsonDBTemplate jsondb = this.jsonDBTemplate;
      threads[p] = new Thread() {
        public void run() {
          for(int i = 0; i < operationCountPerThread; ++i){
            blackhole = t_operations[i].Apply(jsondb);
          }
        }
      };
      threads[p].start();
    }

    for(int t = 0; t < CPU; ++t){
      try {
        threads[t].join();
      }
      catch (Exception e) {
        throw new AssertionError(e);
      }
    }

    return Validators.simple("nothing", 0, 0);
  }
}
