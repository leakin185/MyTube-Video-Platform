package com.leajava.concurrency;

import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

public class DownloadStatus {
  private boolean isDone;
  private LongAdder totalBytes = new LongAdder();
  private ReentrantLock lock = new ReentrantLock();
  private int totalFiles;

  public int getTotalBytes() {
    return totalBytes.intValue();
  }

  public void incrementTotalBytes() {
    lock.lock();
    try {
      totalBytes.increment();
    }
    finally {
      // put it in a finally-block, so it will always get unlocked, even with exception
      lock.unlock();
    }
  }

  public void incrementTotalFiles() {
    totalFiles++;
  }

  public int getTotalFiles() {
    return totalFiles;
  }

  public boolean isDone() {
    return isDone;
  }

  public void done() {
    isDone = true;
  }
}
