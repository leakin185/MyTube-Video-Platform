package com.leajava.generics;

public class Utils {
  public static <T extends Comparable<T>> T max(T first, T second) {
    return (first.compareTo(second) < 0) ? second : first;
  }

  public static <K, V> void print(K key, V value) {
    System.out.println(key + "=" + value);
  }

  public static void printUser(User user) {
    System.out.println(user);
  }

  // wildcard, this method accepts user and its subtypes in GenericList
  public static void printUsers
        (GenericList<? extends User> users) {
    Object x = users.get(0);
  }
  // wildcard with the super keyword
  public static void printUsers2
          (GenericList<? super User> users) {
    Object x = users.get(0);
    // User x = users.get(0);
    users.add(new Instructor((1)));
  }
}
