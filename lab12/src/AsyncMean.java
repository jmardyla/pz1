import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class AsyncMean {
    static double[] array;

    static void initArray(int size) {
        array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = Math.random() * size / (i + 1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int size = 128_000_000;
        initArray(size);
        int[] numberOfThreads = {1, 2, 4, 8, 16, 32, 64, 128};
        for (int n : numberOfThreads) {
            asyncMeanv1(n);
        }

        for (int n : numberOfThreads) {
            asyncMeanv2(n);
        }
    }

    public static void asyncMeanv1(int n) {
        ExecutorService executor = Executors.newFixedThreadPool(n);
        int blockLength = array.length / n;
        // Utwórz listę future
        List<CompletableFuture<Double>> partialResults = new ArrayList<>();
        double t1 = System.nanoTime() / 1e6;
        for (int i = 0; i < n; i++) {
            CompletableFuture<Double> partialMean = CompletableFuture.supplyAsync(
                    new MeanCalcSupplier(i * blockLength, i * blockLength + blockLength), executor);
            partialResults.add(partialMean);
        }
        // zagreguj wyniki
        double mean = 0;
        double t2 = System.nanoTime() / 1e6;
        for (var pr : partialResults) {
            // wywołaj pr.join() aby odczytać wartość future;
            mean += pr.join();
            // join() zawiesza wątek wołający
        }
        double t3 = System.nanoTime() / 1e6;
        mean /= n;
        System.out.printf(Locale.US, "size = %d n=%d >  t2-t1=%f t3-t1=%f mean=%f\n\n",
                array.length,
                n,
                t2 - t1,
                t3 - t1,
                mean);

        executor.shutdown();
    }

    static void asyncMeanv2(int n) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(16);

        BlockingQueue<Double> queue = new ArrayBlockingQueue<>(n);

        int blockLength = array.length / n;
        double t1 = System.nanoTime() / 1e6;
        for (int i = 0; i < n; i++) {
            CompletableFuture.supplyAsync(
                    new MeanCalcSupplier(i * blockLength, i * blockLength + blockLength), executor)
            .thenApply(queue::offer);
        }

        double mean=0;
        double t2 = System.nanoTime() / 1e6;
        // w pętli obejmującej n iteracji wywołaj queue.take() i oblicz średnią
        for(int i=0; i<n; i++) {
            mean += queue.take();
        }
        double t3 = System.nanoTime() / 1e6;
        mean /= n;
        System.out.printf(Locale.US, "size = %d n=%d >  t2-t1=%f t3-t1=%f mean=%f\n\n",
                array.length,
                n,
                t2 - t1,
                t3 - t1,
                mean);

        executor.shutdown();
    }

    static class MeanCalcSupplier implements Supplier<Double> {
        int start, end;

        MeanCalcSupplier(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Double get() {
            double mean = 0;
            for (int i = start; i < end; i++) {
                mean += array[i];
            }
            mean = mean / (end - start);
            System.out.printf(Locale.US, "%d-%d mean=%f\n", start, end, mean);
            return mean;
        }
    }
}