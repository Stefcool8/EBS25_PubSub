package org.pub_sub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EvaluationB {

    public static void main(String[] args) {
        // The notification files per subscriber port
        String[] ports = {"8082", "8083", "8084"};

        // Collect all latencies across all subscribers
        List<Long> latencies = new ArrayList<>();

        for (String port : ports) {
            try {
                InputStream inputStream = EvaluationA.class.getResourceAsStream("notifications-" + port + ".txt");
                if (inputStream == null) {
                    throw new RuntimeException("Could not find notifications file in the same directory as " + EvaluationA.class.getName());
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Expected format: "<emittedTs>, <receivedTs>"
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        long emitted  = Long.parseLong(parts[0].trim());
                        long received = Long.parseLong(parts[1].trim());
                        latencies.add(received - emitted);
                    }
                }
            } catch (IOException e) {
                System.err.println("Failed to read notifications file: " + e.getMessage());
            }
        }

        if (latencies.isEmpty()) {
            System.out.println("No deliveries found; cannot compute latency.");
            return;
        }

        // Compute average
        long sum = 0L;
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (long l : latencies) {
            sum += l;
            if (l < min) min = l;
            if (l > max) max = l;
        }
        double average = (double) sum / latencies.size();

        System.out.printf("Evaluated %d deliveries across %d subscribers.%n",
                latencies.size(), ports.length);
        System.out.printf("Average delivery latency: %.2f ms%n", average);
        System.out.printf("Min latency: %d ms, Max latency: %d ms%n", min, max);
    }
}
