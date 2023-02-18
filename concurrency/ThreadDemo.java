package com.leajava.concurrency;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadDemo {
  public static void show() {
    // We can create a thread using a lambda expression
    var thread1 = new Thread(() -> System.out.println("a"));
    var status = new DownloadStatus();

    // or using an instance of a class that implements the Runnable interface
    var thread2 = new Thread(new DownloadFileTask(status));

    // Next we start a thread
    thread1.start();

    // We can wait for the completion of a thread
    try {
      thread1.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // We can put a thread to sleep
    try {
      thread1.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // We can get the current thread
    var current = Thread.currentThread();
    System.out.println(current.getId());
    System.out.println(current.getName());

    // synchronized collections
    Collection<Integer> collection =
            Collections.synchronizedCollection(new ArrayList<>());
    var thread3 = new Thread(() -> {
      collection.addAll(Arrays.asList(1,2,3));
    });
    var thread4 = new Thread(() -> {
      collection.addAll(Arrays.asList(4,5,6));
    });
    thread3.start();
    thread4.start();

    try {
      thread3.join();
      thread4.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(collection);

    // concurrent collections
    Map<Integer, String> map = new ConcurrentHashMap<>();
    map.put(1,"a");
    map.get(1);
    map.remove(1);

    // eliminating race condition with adders
    List<Thread> threads = new ArrayList<>();

    for (var i = 0;i<10;i++){
      var thread = new Thread(new DownloadFileTask(status));
      thread.start();
      threads.add(thread);
    }
    for (var thread:threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println(status.getTotalBytes());
  }
}
