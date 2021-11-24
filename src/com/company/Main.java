package com.company;
import java.io.File;
public class Main
{
    public static void main(String[] args)
    {
        int Processors = Runtime.getRuntime().availableProcessors();
        String srcFolder = "images";
        String dstFolder = "dst";
        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();
        int middle = files.length / Processors;
        System.out.println("т.к. процессы выполняются одновременно, за время выполнения берется самое долгое окончание работы потока.Можно заметить, что это время меньше того, за которое выполняется этот код в одном потоке примерно в 2 раза.\nПотоки:");
        long start = System.currentTimeMillis();
        for (int i = 0; i < Processors - 1; i++){
            File[] files1 = new File[middle];
            System.arraycopy(files, 0 + (middle * i), files1, 0, middle);
            Cutter cutter1 = new Cutter(files1, dstFolder, start);
            new Thread(cutter1).start();
        }
        File[] files2 = new File[files.length - middle * (Processors - 1)];
        System.arraycopy(files, middle * (Processors - 1), files2, 0, files2.length);
        Cutter cutter2 = new Cutter(files2, dstFolder, start);
        new Thread(cutter2).start();
    }
}