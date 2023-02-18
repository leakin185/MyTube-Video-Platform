package com.leajava.concurrency;

import java.util.concurrent.atomic.LongAdder;

public class DownloadStatus {
  private volatile boolean isDone;
  private LongAdder totalBytes = new LongAdder();;
  private int totalFiles;
  private Object totalBytesLock = new Object();
  private Object totalFilesLock = new Object();

  public int getTotalBytes() {
    return totalBytes.intValue();
  }

  public void incrementTotalBytes() {
    synchronized (totalBytesLock){
      totalBytes.increment();
    }
  }

  public void incrementTotalFiles() {
    synchronized (totalFilesLock){
      totalBytes.increment();
    }
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
