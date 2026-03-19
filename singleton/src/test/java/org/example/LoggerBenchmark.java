package org.example;

import org.example.zad1.Logger;
import org.example.zad1.LoggerDoubleCheckedLocking;
import org.example.zad1.LoggerEnum;
import org.example.zad1.LoggerSynchronized;
import org.example.zad2.LoggerPerThread;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 2, time = 1)
@Fork(1)
public class LoggerBenchmark {

    @Benchmark
    @Threads(10)
    public void benchmarkGetInstance(Blackhole bh) {
        bh.consume(Logger.getInstance());
    }

    @Benchmark
    @Threads(10)
    public void benchmarkGetInstanceSync(Blackhole bh) {
        bh.consume(LoggerSynchronized.getInstance());
    }

    @Benchmark
    @Threads(10)
    public void benchmarkGetInstanceDCL(Blackhole bh) {
        bh.consume(LoggerDoubleCheckedLocking.getInstance());
    }

    @Benchmark
    @Threads(10)
    public void benchmarkGetInstanceEnum(Blackhole bh) {
        bh.consume(LoggerEnum.getInstance());
    }

    @Benchmark
    @Threads(10)
    public void benchmarkGetInstancePerThread(Blackhole bh) {
        bh.consume(LoggerPerThread.getInstance());
    }
}