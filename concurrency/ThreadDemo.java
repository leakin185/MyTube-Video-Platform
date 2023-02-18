package com.leajava.concurrency;

import java.util.ArrayList;
import java.util.List;

public class ThreadDemo {
  public static void show() {
    // We can create a thread using a lambda expression
    var thread1 = new Thread(() -> System.out.println("a"));

    // or using an instance of a class that implements the Runnable interface
    var thread2 = new Thread(new DownloadFileTask());

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

    //1. confinement
    List<Thread> threads = new ArrayList<>();
    List<DownloadFileTask> tasks = new ArrayList<>();

    for (var i = 0; i < 10; i++) {
      var task = new DownloadFileTask();
      tasks.add(task);
      var thread = new Thread(task);
      thread.start();
      threads.add(thread);
    }

    // wait for all threads to finish execution
    // the join() method shouldn't be added above otherwise the threads will run consecutively and not concurrently
    for (var thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    var totalBytes = tasks.stream()
            .map(t->t.getStatus().getTotalBytes())
            .reduce(Integer::sum);
    System.out.println(totalBytes);

    // 2. Synchronization with Locks
    // implementation not shown


  }
}
