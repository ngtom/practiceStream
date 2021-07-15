package com.company;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;


public class Main {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        // find first
        System.out.println(numbers.stream()
                .filter(n->n>3)
                .filter(n->n%2==0)
                .map(n->n*3)
                .findFirst()
                .orElse(0)
        );

        //Return list of ur requests
        System.out.println(numbers.stream()
                .filter(n->n>3)
                .filter(n->n%2==0)
                .collect(Collectors.toList()));

        // imperative style creating map of name as key and age as value
        // given Person objects
        Map<String, Integer> demo = new HashMap<>();
        for( Person p : createPeople()){
            demo.put(p.getName(), p.getAge());
        }
        System.out.println(demo);


        //System.out.println(createPeople().stream()
                //.collect(toMap(Person::getName, Person::getAge)));

        // declarative, functional programming
        createPeople().stream()
                .filter(p -> p.getAge() > 30)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println(createPeople().stream()
                .map(Person::getAge) // method reference
                .reduce(0, Integer::sum)); // method reference

        createPeople().stream()
                .filter( p -> p.getAge() < 100)
                .forEach(System.out::println);

        System.out.println( createPeople().stream()
                .filter(p -> p.getAge()>30)
                .map(Person::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toList())
        );


        // imperative
        Map<String, List<Person>> myMap = new HashMap<>();
        for (Person p: createPeople()){
            List<Person> tempList;
            if(myMap.containsKey(p.getName())){
                tempList = myMap.get(p.getName());
            }
            else {
                tempList = new ArrayList<>();
                myMap.put(p.getName(), tempList);
            }
            tempList.add(p);
        }
        //System.out.println(myMap);

        // deklarative

        Map<String, List<Person>> myMap1 =
                createPeople().stream()
                        .collect(groupingBy(Person::getName));

        // mapping collector here need method ref and a collector to proceed
        // in result we are getting Map with List of Integer instead Person
        Map<String, List<Integer>> myMap2 =
                createPeople().stream()
                        .collect(groupingBy(Person::getName, mapping(Person::getAge,toList())));


        // counting return Long, here we need to convert to Integer
        // counting act as counter here
        Map<String, Integer> myMap3 =
                createPeople().stream()
                        .collect(groupingBy(Person::getName, collectingAndThen(counting(), Long::intValue)));
        //System.out.println(myMap3);

        System.out.println(createPeople().stream().max(comparing(Person::getAge)));

        String res = createPeople().stream()
                .collect(collectingAndThen(minBy(comparing(Person::getAge)), p->p.map(Person::getName).
                        orElse(""))
                );
        System.out.println(res);

        // mapping and filtering inside collect(...)
        System.out.println(createPeople().stream()
            .collect(groupingBy(Person::getAge, mapping(Person::getName, filtering(name -> name.length()>3, toList()))))
        );

        //teeing : combining two collectors together

        // grouping mapping  :  (Func, Collector)
        // collectingAndThen : (Collector, Func)
        // teeing : (Col, Col, operation)

        // flatMap good for one to many relation
        // [[.....],[...],[..]]
        // flatmap needs iterator not Collections
        // therefore here we use Stream.of(..)
        List<Integer> smallNumber = Arrays.asList(1,3,2);

        System.out.println(smallNumber.stream()
                .map(n -> List.of(n-1, n+1))
                .collect(toList())
        );

        System.out.println(smallNumber.stream()
                .flatMap(n -> List.of(n-1, n+1).stream())
                .collect(toList())
        );

        List<Person> people = createPeople();

        System.out.println(people.stream()
                .map(Person::getName)
                .flatMap(name -> Stream.of(name.split("")))
                .collect(toList()));

        System.out.println(
                people.stream()
                .collect(groupingBy(Person::getAge,
                        flatMapping(p -> Stream.of(p.getName().split("")), toSet())))
        );

        System.out.println(
                people.stream()
                        .collect(groupingBy(Person::getAge,
                                mapping(person->person.getName().toUpperCase(),
                                flatMapping(name -> Stream.of(name.split("")), toSet()))))
        );
    }

    public static List<Person> createPeople() {
        return List.of(
                new Person("Sarah", 23 ),
                new Person("Saroh", 11),
                new Person("Xaro", 13),
                new Person("Paul", 12),
                new Person("John", 16),
                new Person("Tom", 17),
                new Person("Sen", 19),
                new Person("Tom", 23),
                new Person("Saral", 31),
                new Person("Sars", 14),
                new Person("Tomix", 90)
        );
    }
}
