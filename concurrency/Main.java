package com.leajava.concurrency;

public class Main {
    public static void main (String[] args ){
        // 1. exploring threads
        System.out.println(Thread.activeCount());
        System.out.println(Runtime.getRuntime().availableProcessors());

        ThreadDemo.show();

    }
}
