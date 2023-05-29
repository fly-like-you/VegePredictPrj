package com.vegetable.vegetable;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class PythonScriptExecutor {

    private final ScheduledExecutorService scheduler;
    private String pythonPath = "C:/Users/Parkjunho/anaconda3/envs/MachineLearning/python.exe";

    public PythonScriptExecutor(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    public Optional<Integer> executePythonScript(String pythonScriptPath) {

        try {
            ProcessBuilder pb = new ProcessBuilder(pythonPath, pythonScriptPath);
            pb.inheritIO();
            Process process = pb.start();
            int exitCode = process.waitFor();

            return Optional.of(exitCode);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void scheduleRetry() {
        scheduler.schedule(() -> executePythonScript(pythonPath), 1, TimeUnit.HOURS);
    }
}
