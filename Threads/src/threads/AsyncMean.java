package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class AsyncMean {
    static double[] array;
    static void initArray(int size){
        array = new double[size];
        for(int i=0;i<size;i++){
            array[i]= Math.random()*size/(i+1);
        }
    }

    static class MeanCalcSupplier implements Supplier<Double> {
        int start;
        int end;

        MeanCalcSupplier(int start, int end){
            this.start = start;
            this.end = end;
        }

        @Override
        public Double get() {
            double mean=0;
            // oblicz
            for(int i=start;i<=end;i++){
                mean+=array[i];
            }
            mean/=(end-start+1);
//            System.out.printf(Locale.US,"%d-%d mean=%f\n",start,end,mean);
            return mean;
        }
    }

    public static void asyncMeanV1() {
        int size = 1_000;
        initArray(size);
        ExecutorService executor = Executors.newFixedThreadPool(16);
        int n=100;
        // Utwórz listę future
        List<CompletableFuture<Double>> partialResults = new ArrayList<>();
        for(int i=0;i<n;i++){
            CompletableFuture<Double> partialMean = CompletableFuture.supplyAsync(
                    new MeanCalcSupplier(i * size/n, (i+1) * size/n - 1), executor);
            partialResults.add(partialMean);
        }
        // zagreguj wyniki
        double mean=0;
        for(var pr:partialResults){
            // wywołaj pr.join() aby odczytać wartość future;
            // join() zawiesza wątek wołający
            mean+=pr.join();

        }
        mean /= n;
        System.out.printf(Locale.US,"mean=%f\n",mean);

        executor.shutdown();
    }

    static void asyncMeanV2() {
        int size = 1_000;
        initArray(size);
        ExecutorService executor = Executors.newFixedThreadPool(16);
        int n = 100;

        BlockingQueue<Double> queue = new ArrayBlockingQueue<>(n);

        for (int i = 0; i < n; i++) {
            CompletableFuture.supplyAsync(
                    new MeanCalcSupplier(i * size/n, (i+1) * size/n - 1), executor)
            .thenApply(d -> queue.offer(d));
        }


        // w pętli obejmującej n iteracji wywołaj queue.take() i oblicz średnią
        double mean=0;
        for(int i=0;i<n;i++){
            try {
                mean += queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        mean /= n;

        System.out.printf(Locale.US,"mean=%f\n", mean);

        executor.shutdown();
    }

    public static void main(String[] args) {
        asyncMeanV2();
        asyncMeanV1();
    }

}
