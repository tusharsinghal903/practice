package org.example.multiThreading;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class KeyedExecutor {
    private final Executor[] executors;
    private final int numberOfThreads;

    public KeyedExecutor(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        executors = new Executor[numberOfThreads];
        for(int i=0;i<numberOfThreads;i++) {
            executors[i] = Executors.newSingleThreadExecutor();
        }
    }

    private Executor getThread(String key) {
        return executors[key.hashCode()%numberOfThreads];
    }

    public CompletionStage<Void> submit(String key, final Runnable task) {
        return CompletableFuture.runAsync(task, getThread(key));
    }

    public <R> CompletionStage<R> submit(String key, final Supplier<R> task) {
        return CompletableFuture.supplyAsync(task, getThread(key));
    }

}
