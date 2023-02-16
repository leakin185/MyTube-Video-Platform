package com.leajava.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class StreamsDemo {
  public static void show() {
    List<Movie> movies = List.of(
            new Movie("a",10,Genre.ACTION),
            new Movie("g", 30, Genre.COMEDY),
            new Movie("c", 20, Genre.THRILLER),
            new Movie("a",10,Genre.ACTION)
    );
    // 1. Filter() and count()
    Predicate<Movie> isPopular = m -> m.getLikes()>10;
    var count = movies.stream()
            .filter(isPopular)
            .count();
    System.out.println(count);

    // 2. mapping
    movies.stream()
            // .mapToInt(movie-> movie.getLikes())
            .map(movie -> movie.getTitle())
            .forEach(name->System.out.println(name));

    var stream = Stream.of(List.of(1,2,3), List.of(4,5,6));
    //stream.forEach(list -> System.out.println(list));
    // flatten Stream<List<x>> -> Stream<x>
    stream
            .flatMap(list->list.stream())
            .forEach(n->System.out.println(n));

    //3. slicing elements
    // pagination
    // 1000 movies, 10 movies per page, 3rd page
    // skip(20) = skip((page-1) x pageSize) -> formula
    // limit(10)
    movies.stream()
            .skip(2)
            .forEach(m->System.out.println(m.getTitle()));
    // stops until the condition becomes false
    movies.stream()
            .takeWhile(m->m.getLikes()<30)
            .forEach(m->System.out.println(m.getTitle()));
    // drop all the elements with likes <30 until this condition becomes false
    movies.stream()
            .dropWhile(m->m.getLikes()<30)
            .forEach(m->System.out.println(m.getTitle()));

    //4. sorting
    movies.stream()
            //.sorted((a,b) -> a.getTitle().compareTo(b.getTitle()))
            // the two statements are the same
            .sorted(Comparator.comparing(Movie::getTitle))
            // reverse order
            .sorted(Comparator.comparing(Movie::getTitle).reversed())
            .forEach(m->System.out.println(m.getTitle()));

    //5.getting unique elements
    movies.stream()
            .map(Movie::getLikes)
            .distinct()
            .forEach(System.out::println);

    //6. peeking elements
    movies.stream()
            .filter(isPopular)
            .peek(m->System.out.println("filtered: " + m.getTitle()))
            .map(Movie::getTitle)
            .peek(t->System.out.println("mapped: " + t))
            .forEach(System.out::println);

    // 7. reducers
    var numMatches = movies.stream()
            .anyMatch(m -> m.getLikes() > 20);
    var totalMatches = movies.stream()
            .allMatch(m -> m.getLikes() > 20);
    var noMatches = movies.stream()
            .noneMatch(m -> m.getLikes() > 20);
    var movie = movies.stream()
            .findFirst()
            .get();
    var movie2 = movies.stream()
            .findAny()
            .get();
    var movie3 = movies.stream()
            .max(Comparator.comparing(Movie::getLikes))
            .get();
    var movie4 = movies.stream()
            .min(Comparator.comparing(Movie::getLikes))
            .get();

    // 8. reducing a stream
    // [10,20,30] -> [30,30] -> [60]
    Optional<Integer> sum = movies.stream()
            .map(m->m.getLikes())
//            .reduce((a,b)->a+b);
            .reduce(Integer::sum);

    System.out.println(sum.orElse(0));

    // or we supply a digit so it no longer returns optionals
    Integer sum2 = movies.stream()
            .map(m->m.getLikes())
            .reduce(0,Integer::sum);

    System.out.println(sum2);

    // 9. collectors
    var results = movies.stream()
            .filter(isPopular)
//            .collect(Collectors.toMap(Movie::getTitle, Function.identity()));
        // .collect(Collectors.summingInt(Movie::getLikes)); // sum up
                .collect(Collectors.summarizingInt(Movie::getLikes));
    System.out.println(results);
    var results2 = movies.stream()
            .filter(isPopular)
            .map(Movie::getTitle)
            .collect(Collectors.joining(", "));
    System.out.println(results2);

    // 10. Grouping elements
    var results3 = movies.stream()
            .collect(Collectors.groupingBy(Movie::getGenre));
    System.out.println(results3);

    var results4 = movies.stream()
            .collect(Collectors.groupingBy(Movie::getGenre, Collectors.toSet()));
    System.out.println(results4);

    var results5 = movies.stream()
            .collect(Collectors.groupingBy(Movie::getGenre, Collectors.counting()));
    System.out.println(results5);

    var results6 = movies.stream()
            .collect(Collectors.groupingBy(Movie::getGenre,
                    Collectors.mapping(Movie::getTitle, Collectors.joining(", "))));
    System.out.println(results6);

    //11. partitioning elements
    var result7 = movies.stream()
            .collect(Collectors.partitioningBy(m->m.getLikes()>20));
    System.out.println(result7);
    var result8 = movies.stream()
            .collect(Collectors.partitioningBy(
                    m->m.getLikes()>20,
                    Collectors.mapping(Movie::getTitle,
                            Collectors.joining(", "))));
    System.out.println(result8);

    //12. primitive type streams
    IntStream.rangeClosed(1,5)
            .forEach(System.out::println);
    // doesn't include the last element
    IntStream.range(1,5)
            .forEach(System.out::println);



  }
}
