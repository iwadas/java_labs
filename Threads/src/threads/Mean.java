package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;

public class Mean {
    static double[] array;
    static BlockingQueue<Double> results = new ArrayBlockingQueue<Double>(1_000_000);


    static void initArray(int size){
        array = new double[size];
        for(int i=0;i<size;i++){
            array[i]= Math.random()*size/(i+1);
        }
    }

    static class MeanCalc extends Thread{
        private final int start;
        private final int end;
        double mean = 0;

        MeanCalc(int start, int end){
            this.start = start;
            this.end = end;
        }


        public void run(){
            for(int i=start;i<=end;i++){
                mean += array[i];
            }
            mean /= (end-start + 1);
//            System.out.printf(Locale.US,"%d-%d mean=%f\n",start,end,mean);
            try {
                results.put(mean);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static void parallelMeanV1(int cnt){
        // utwórz tablicę wątków
        MeanCalc threads[]=new MeanCalc[cnt];
        // utwórz wątki, podziel tablice na równe bloki i przekaż indeksy do wątków
        for(int i=0;i<cnt;i++) {
            threads[i]=new MeanCalc(i*array.length/cnt ,(i+1)*array.length/cnt - 1);
        }

        // załóż, że array.length dzieli się przez cnt)
        double t1 = System.nanoTime()/1e6;
        //uruchom wątki
        for(MeanCalc mc:threads) {
            mc.start();
        }
        double t2 = System.nanoTime()/1e6;
        // czekaj na ich zakończenie używając metody ''join''
        for(MeanCalc mc:threads) {
            try {
                mc.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // oblicz średnią ze średnich
        double mean = 0;
        for (MeanCalc mc : threads) {
            mean += mc.mean;
        }
        mean /= cnt;

        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2-t1,
                t3-t1,
        mean);
    }

    static void parallelMeanV2(int cnt){
        results.clear();
        MeanCalc threads[]=new MeanCalc[cnt];
        for(int i=0;i<cnt;i++) {
            threads[i]=new MeanCalc(i*array.length/cnt ,(i+1)*array.length/cnt - 1);
        }
        double t1 = System.nanoTime()/1e6;
        for(MeanCalc mc:threads) {
            mc.start();
        }
        double t2 = System.nanoTime()/1e6;
        for(MeanCalc mc:threads) {
            try {
                mc.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        // oblicz średnią ze średnich

        double mean = 0;
        for(int i=0;i<cnt;i++) {
            try {
                mean += results.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        mean /= cnt;

        double t3 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2-t1,
                t3-t1,
                mean);
    }

    static void parallelMeanV3(int cnt) {
        results.clear(); // Clear the shared BlockingQueue
        ExecutorService executor = Executors.newFixedThreadPool(cnt);
        int chunkSize = array.length / cnt;

        double t1 = System.nanoTime() / 1e6;

        // Divide work into chunks and assign to threads
        for (int i = 0; i < cnt; i++) {
            int start = i * chunkSize;
            executor.execute(new MeanCalc(i * chunkSize, (i + 1) * chunkSize - 1));
        }

        double t2 = System.nanoTime() / 1e6;

        // Calculate the mean based on results from the queue
        double mean = 0;
        for (int i = 0; i < cnt; i++) {
            try {
                mean += results.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while retrieving result", e);
            }
        }
        mean /= cnt;

        double t3 = System.nanoTime() / 1e6;

        executor.shutdown();

        System.out.printf(Locale.US, "size = %d cnt = %d > t2-t1 = %f t3-t1 = %f mean = %f\n",
                array.length,
                cnt,
                t2 - t1,
                t3 - t1,
                mean);
    }

    public static void main(String[] args) {
        int size = 1_000_000;
        initArray(size);
        parallelMeanV1(4);
        parallelMeanV2(4);
        for(int cnt:new int[]{1,2,4,10,20}){
            parallelMeanV3(cnt);
        }
    }
}