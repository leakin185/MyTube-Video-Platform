package com.leajava.lambdas;

import java.util.List;
import java.util.function.*;

public class LambdasDemo {
  public static void show() {
    // the consumer interface
    List<String> list = List.of("a","b","c");
    Consumer<String> print = item -> System.out.println(item);
    Consumer<String> printUpperCase = item -> System.out.println(item.toUpperCase());
    list.forEach(print.andThen(printUpperCase));

    // the supplier interface
    Supplier<Double> getRandom = () -> Math.random();
    var random = getRandom.get();
    System.out.println(random);

    // the function interface
    Function<String, Integer> map = str -> str.length();
    var length = map.apply("sky");
    System.out.println(length);

    //Composing Functions
    Function<String, String> replaceColon = str -> str.replace(":", "=");
    Function<String, String> addBraces = str -> "{" + str + "}";
    // declarative programming
    // compose a few functions into a single function
    var res = replaceColon
            .andThen(addBraces)
            .apply("key:value");
    var res1 = addBraces.compose(replaceColon).apply("key:value");
    // same result, the .compose method() does it in the reverse order
    System.out.println(res);
    System.out.println(res1);

    // the predicate interface
    Predicate<String> isLongerThan5 = str -> str.length() > 5;
    var res2 = isLongerThan5.test("sky");
    System.out.println(res2);

    // chaining predicates
    Predicate<String> hasLeftBrace = str -> str.startsWith("{");
    Predicate<String> hasRightBrace = str -> str.endsWith("}");
    Predicate<String> hasLeftandRightBraces = hasLeftBrace.and(hasRightBrace);
    Predicate<String> hasLeftorRightBraces = hasLeftBrace.or(hasRightBrace);
    Predicate<String> hasLeftoopRightBraces = hasLeftBrace.negate();

    // binary operator
    BinaryOperator<Integer> add = (a, b) -> a + b;
    Function<Integer, Integer> square = a -> a * a;
    var result = add.andThen(square).apply(1,2);
    System.out.println(result);

    // unaryOperator
    UnaryOperator<Integer> square2 = n -> n * n;
    UnaryOperator<Integer> increment = n -> n + 1;

    var result2 = increment.andThen(square2).apply(1);
    System.out.println(result2);
  }
}
