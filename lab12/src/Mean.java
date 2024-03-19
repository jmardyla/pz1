import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mean {
    static double[] array;
    static BlockingQueue<Double> results = new ArrayBlockingQueue<Double>(100);

    static void initArray(int size) {
        array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = Math.random() * size / (i + 1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        initArray(128000000);
        for (int cnt : new int[]{1, 2, 4, 8, 16, 32, 64, 128}) {
            parallelMean2(cnt);
        }
    }

    /**
     * Oblicza średnią wartości elementów tablicy array uruchamiając równolegle działające wątki.
     * Wypisuje czasy operacji
     *
     * @param cnt - liczba wątków
     */
    static void parallelMean1(int cnt) throws InterruptedException {
        // utwórz tablicę wątków
        MeanCalc[] threads = new MeanCalc[cnt];
        // utwórz wątki, podziel tablice na równe bloki i przekaż indeksy do wątków
        int blockLength = array.length / cnt;
        for (int i = 0; i < cnt; i++) {
            threads[i] = new MeanCalc(i * blockLength, i * blockLength + blockLength);
        }
        // załóż, że array.length dzieli się przez cnt)
        double t1 = System.nanoTime() / 1e6;
        //uruchom wątki
        for (var thread : threads) {
            thread.start();
        }
        double t2 = System.nanoTime() / 1e6;
        // czekaj na ich zakończenie używając metody ''join''
        for (MeanCalc mc : threads) {
            mc.join();
        }
        // oblicz średnią ze średnich
        double sum = 0;
        for (var mc : threads) {
            sum += mc.mean;
        }
        double mean = sum / threads.length;
        double t3 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2 - t1,
                t3 - t1,
                mean);
    }

    static void parallelMean2(int cnt) throws InterruptedException {
        // utwórz tablicę wątków
        MeanCalc[] threads = new MeanCalc[cnt];
        // utwórz wątki, podziel tablice na równe bloki i przekaż indeksy do wątków
        int blockLength = array.length / cnt;
        for (int i = 0; i < cnt; i++) {
            threads[i] = new MeanCalc(i * blockLength, i * blockLength + blockLength);
        }
        // załóż, że array.length dzieli się przez cnt)
        double t1 = System.nanoTime() / 1e6;
        //uruchom wątki
        for (var thread : threads) {
            thread.start();
        }
        double t2 = System.nanoTime() / 1e6;
        // czekaj na ich zakończenie używając metody ''join''
//        for (MeanCalc mc : threads) {
//            mc.join();
//        }
        // oblicz średnią ze średnich
        double sum = 0;
        for (var mc : threads) {
//            sum += mc.mean;
            sum += results.take();
        }
        double mean = sum / threads.length;
        double t3 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2 - t1,
                t3 - t1,
                mean);
    }

    static void parallelMean3(int cnt) throws InterruptedException {
        // utwórz tablicę wątków
        ExecutorService executor = Executors.newFixedThreadPool(cnt);
        int blockLength = array.length / cnt;
        for (int i = 0; i < cnt; i++) {
            int finalI = i;
            executor.execute(() -> {
                double sum = 0;
                int start = finalI * blockLength;
                int end = start + blockLength;
                for (int j = start; j < end; j++) {
                    sum += array[j];
                }
                double mean = sum / (end - start);
                System.out.printf(Locale.US, "%d-%d mean=%f\n", start, end, mean);
                try {
                    results.put(mean);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.shutdown();
        // załóż, że array.length dzieli się przez cnt)
        double t1 = System.nanoTime() / 1e6;
        //uruchom wątki
        double t2 = System.nanoTime() / 1e6;
        // czekaj na ich zakończenie używając metody ''join''
        // oblicz średnią ze średnich
        double sum = 0;
        for (int i = 0; i < cnt; i++) {
//            sum += mc.mean;
            sum += results.take();
        }
        double mean = sum / cnt;
        double t3 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n\n",
                array.length,
                cnt,
                t2 - t1,
                t3 - t1,
                mean);
    }

    static class MeanCalc extends Thread {
        private final int start;
        private final int end;
        double mean = 0;

        MeanCalc(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void run() {
            double sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            mean = sum / (end - start);
            try {
                results.put(mean);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf(Locale.US, "%d-%d mean=%f\n", start, end, mean);
        }
    }

}