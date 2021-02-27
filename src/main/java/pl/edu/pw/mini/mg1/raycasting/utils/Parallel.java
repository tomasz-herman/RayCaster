package pl.edu.pw.mini.mg1.raycasting.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

public class Parallel {
    private static final Executor threadPool = Executors.newFixedThreadPool(8, Parallel::newThread);

    public static void parallelFor(int elements, BiConsumer<Integer, Integer> function) {
        int batchSize = elements / 8;
        int batchRemainder = elements % 8;

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < 8; ++i) {
            int start = i * batchSize;
            futures.add(CompletableFuture.runAsync(() -> function.accept(start, start + batchSize), threadPool));
        }

        function.accept(batchSize * 8, batchSize * 8 + batchRemainder);

        futures.forEach(CompletableFuture::join);
    }

    private static int threadNum = 0;
    private static Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.setName("ParallelFor thread " + threadNum++);
        return thread;
    }
}
