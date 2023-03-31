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
@Group("database")
@Summary("Simulates a simple application using JsonDB")
@Licenses(License.MIT)
@Repetitions(10)
@Parameter(name = "operation_count_per_thread", defaultValue = "1000000")
@Parameter(name = "op_insert_weight", defaultValue = "1")
@Parameter(name = "op_remove_weight", defaultValue = "1")
@Parameter(name = "op_update_weight", defaultValue = "5")
@Parameter(name = "op_find_by_id_weight", defaultValue = "10")
@Parameter(name = "op_find_one_weight", defaultValue = "7")
@Parameter(name = "op_find_weight", defaultValue = "1")
@Parameter(name = "rng_seed", defaultValue = "42")
public final class JsonDBBench implements Benchmark {

  private JsonDBTemplate jsonDBTemplate;
  private int operationCountPerThread;
  private OperationGenerator operationGenerator;
  private Random rng;

  final static int CPU = Runtime.getRuntime().availableProcessors();
  final static String dbImageLocation = "benchmarks/jsondb/src/main/java/org/renaissance/jsondb/resources/";
  private static volatile Object blackhole = null;

  @Override
  public void setUpBeforeAll(BenchmarkContext c){

    operationCountPerThread = c.parameter("operation_count_per_thread").toInteger();

    String dbFilesLocation = c.scratchDirectory().toString();
    
    String baseScanPackage = "org.renaissance.jsondb.model";

    // Initialize an empty DB
    this.jsonDBTemplate = new JsonDBTemplate(dbFilesLocation, baseScanPackage);
    this.jsonDBTemplate.createCollection(Artist.class);

    // Setup OperationGenerator parameters
    operationGenerator = new OperationGenerator();
    operationGenerator.setInsertWeight(c.parameter("op_insert_weight").toInteger());
    operationGenerator.setRemoveWeight(c.parameter("op_remove_weight").toInteger());
    operationGenerator.setUpdateWeight(c.parameter("op_update_weight").toInteger());
    operationGenerator.setFindByIdWeight(c.parameter("op_find_by_id_weight").toInteger());
    operationGenerator.setFindOneWeight(c.parameter("op_find_one_weight").toInteger());
    operationGenerator.setFindWeight(c.parameter("op_find_weight").toInteger());

    rng = new Random(c.parameter("rng_seed").toInteger());
  }

  // @Override
  // public void tearDownAfterAll(BenchmarkContext c){
  //   // TODO? remove db data
  // }

  DatabaseOperation[][] operations;

  @Override
  public void setUpBeforeEach(BenchmarkContext c){
    // Restore the database to a known state
    this.jsonDBTemplate.restore(dbImageLocation, false);
    
    // Generate operations for each thread
    operations = new DatabaseOperation[CPU][operationCountPerThread];
    
    for(int i = 0; i < CPU; ++i){
      operations[i] = operationGenerator.operations(operationCountPerThread, rng, this.jsonDBTemplate);
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
