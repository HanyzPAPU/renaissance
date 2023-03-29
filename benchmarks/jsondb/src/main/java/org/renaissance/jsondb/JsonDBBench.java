package org.renaissance.jsondb;

import org.renaissance.Benchmark;
import org.renaissance.BenchmarkContext;
import org.renaissance.BenchmarkResult;
import org.renaissance.BenchmarkResult.Validators;
import org.renaissance.License;

import static org.renaissance.Benchmark.*;

@Name("jsondb-bench")
@Group("jsondb")
@Summary("Simulates a simple application usng JsonDB")
@Licenses(License.MIT)
// TODO: add supports jvm
@Repetitions(1) //TODO: change
// TODO: think about parameters
// TODO: configuration?
public final class JsonDBBench implements Benchmark {
    @Override
  public BenchmarkResult run(BenchmarkContext c) {
    return Validators.simple("nothing", 0, 0);
  }
}
