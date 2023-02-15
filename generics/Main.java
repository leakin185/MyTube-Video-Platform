package com.leajava.generics;

public class Main {
    public static void main(String[] args) {
        var user1 = new User(10);
        var user2 = new User(20);
        if (user1.compareTo(user2) < 0)
            System.out.println("user1 < user2");
        else if (user1.compareTo(user2) == 0)
            System.out.println("user1 == user2");
        else
            System.out.println("user1 == user2");

        var max = Utils.max(1,2);
        System.out.println(max);

        var User = Utils.max(user1, user2);
        System.out.println(User);

        Utils.print(1,"Lea");

        var list = new GenericList<String>();
        list.add("a");
        list.add("b");
        for (var item: list)
            System.out.println(item);
    }
}
