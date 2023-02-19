package com.leajava.executors;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFuturesDemo {
  public static void show() {
      //combining completable futures
      var first = CompletableFuture
                      .supplyAsync(()->"20USD")
                      .thenApply( str -> {
                          var price = str.replace("USD", "");
                          return Integer.parseInt(price);
                      });
      var second = CompletableFuture.supplyAsync(()->0.9);
      first.thenCombine(second,(price, exchangeRate) -> price * exchangeRate)
              .thenAccept(result ->System.out.println(result));

      // waiting for many tasks
      var third = CompletableFuture.supplyAsync(()->1);
      var forth = CompletableFuture.supplyAsync(()->2);
      var fifth = CompletableFuture.supplyAsync(()->3);
      var all = CompletableFuture.allOf(third, forth, fifth);
      all.thenRun(()-> {
          try {
              var firstResult = third.get();
              System.out.println(firstResult);
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          } catch (ExecutionException e) {
              throw new RuntimeException(e);
          }
          System.out.println("All TASKS COMPLETED SUCCESSFULLY");
      });

      // waiting for the first task; get the result immediately, don't have to wait for the slower server
      var sixth = CompletableFuture.supplyAsync(()-> {
          LongTask.simulate();
          return 20;
      });
      var seven = CompletableFuture.supplyAsync(()->20);
      var fastest = CompletableFuture.anyOf(first, second)
              .thenAccept(temp -> System.out.println(temp));

      // handling timeouts with async events
      var future = CompletableFuture.supplyAsync(() -> {
          LongTask.simulate();
          return 1;
      });
      try {
          var result = future.completeOnTimeout(1, 1, TimeUnit.SECONDS)
                  .get();
          // if timeout, give a default value to end user instead of an exception
          System.out.println(result);
      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      } catch (ExecutionException e) {
          throw new RuntimeException(e);
      }
  }
}
