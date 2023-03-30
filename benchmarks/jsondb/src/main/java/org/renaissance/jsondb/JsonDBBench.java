package org.renaissance.jsondb;

import org.renaissance.Benchmark;
import org.renaissance.BenchmarkContext;
import org.renaissance.BenchmarkResult;
import org.renaissance.BenchmarkResult.Validators;
import org.renaissance.License;

import static org.renaissance.Benchmark.*;

import java.util.Random;

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

    for(int i = 0; i < 5000; ++i){
      this.jsonDBTemplate.insert(DataGenerator.artist(new Random()));
    }

    
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
