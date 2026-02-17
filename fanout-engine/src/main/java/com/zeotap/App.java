package com.zeotap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) {

        String filePath = "sample.csv";

        // Transformer strategy
        Transformer transformer = new UpperCaseTransformer();

        // Multiple sinks with rate limiting
        List<Sink> sinks = List.of(
                new RateLimitedSink(new ConsoleSink(), 5),
                new RateLimitedSink(new SlowSink(), 2)
        );

        // Thread pool for high throughput
        ExecutorService pool = Executors.newFixedThreadPool(4);

        long startTime = System.currentTimeMillis();
        int[] count = {0};

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String transformed = transformer.transform(line);
                count[0]++;

                for (Sink sink : sinks) {
                    pool.submit(() -> {
                        try {
                            sink.send(transformed);
                        } catch (Exception e) {
                            System.out.println("Sink error: " + e.getMessage());
                        }
                    });
                }
            }

        } catch (Exception e) {
            System.out.println("File error: " + e.getMessage());
        }

        // shutdown pool
        pool.shutdown();
        try {
            pool.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException ignored) {}

        long endTime = System.currentTimeMillis();

        System.out.println("\n===============================");
        System.out.println("Total records processed: " + count[0]);
        System.out.println("Time taken (ms): " + (endTime - startTime));
        System.out.println("Throughput (records/sec): "
                + (count[0] * 1000.0 / (endTime - startTime)));
        System.out.println("===============================");
    }
}
