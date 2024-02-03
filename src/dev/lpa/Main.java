package dev.lpa;

import dev.lpa.model.LPAStudent;
import dev.lpa.model.Student;
import dev.lpa.util.QueryItem;
import dev.lpa.util.QueryList;

import java.util.ArrayList;
import java.util.List;

record Employee(String name) implements QueryItem {
    @Override
    public boolean matchFieldValue(String fieldName, String value) {
        return false;
    }
}

public class Main {

    //When we specify Student as a type argument to a generic class or container, only Student, and not one of its
    // subtypes, is valid for this container.

    //Although we can add Students of any type to the container, we can't pass a List typed as LPAStudent to a reference
    //variable of List typed with Student.

    public static void main(String[] args) {

        int studentCount = 10;
        List<Student> students = new ArrayList<>();
        for(int i = 0; i < studentCount; i++){
            students.add(new Student());
        }
        students.add(new LPAStudent());
//        printList(students);
        printMoreLists(students);

        List<LPAStudent> lpaStudents = new ArrayList<>();
        for(int i = 0; i < studentCount; i++){
            lpaStudents.add(new LPAStudent());
        }
//        printList(lpaStudents);
        printMoreLists(lpaStudents);

        //Notes for above:

        //We know LPAStudent inherits from Student, and we can pass an instance of LPAStudent to any method, or assign
        //it to any reference type, declared with type Student.

        //We also know that ArrayList implements List, and we can pass an ArrayList to a method or assign it to a
        //reference of the List type.

        //So why cant we pass an ArrayList of LPA Student to the method parameter that's declared as a List of Student?

        //It's natural to assume that a method that takes a List with Students should accept a List with LPAStudents,
        //because LPAStudent is a Student after all.

        //But that isn't how it works.

        //When used as reference types, a container of one type has no relationship to the same container of another
        //type, even if the contained types do have a relationship.

        testList(new ArrayList<String>(List.of("Able", "Barry", "Charlie")));
        testList(new ArrayList<Integer>(List.of(1, 2, 3)));

        //We use var here, which means the type should be inferred, and then we're assigning it a new instance of the
        //QueryList class. Notice we don't specify a type argument on either side of the assignment operator. We pass
        //the list of lpaStudents to the constructor, and that's enough information for Java to infer that this class
        //is typed with lpaStudent.
        var queryList = new QueryList<>(lpaStudents);
        var matches = queryList.getMatches("Course", "Python");
        printMoreLists(matches);

        //By adding <Student> before the getMatches method, we're saying the return list will be a list of Students.
        var students2021 = QueryList.<Student>getMatches(students, "YearStarted", "2021");
        printMoreLists(students2021);

    }

    //Below, we are using a generic method, which can be done on any class, not just a generic class.

    //Doing this in our case is one way to get around the issue of not being able to add a typed list of one type to
    //a typed list of another type, without having to use the raw usage of List and without needing to duplicate code,
    //and also keep our code extensible.

    //Below we are setting up a type parameter for this method that goes in angle brackets just before the return type.

    //We can use T where we would otherwise have a type, so we can include angle brackets T after List in the method
    //parameter.

    //So, instead of saying this method will take only a List of Student, we're saying it will take a List of any kind
    //of type.

//    public static <T extends Student> void printList(List<T> students){
//        //Adding extends Student sets the upper bound
//        for(var student : students){
//            System.out.println(student.getYearStarted() + ": " + student);
//        }
//        System.out.println();
//    }

    //The ? is the syntax that Java calls a wildcard in the type argument.

    //A wildcard is represented by a question mark.
    public static void printMoreLists(List<? extends Student> students){
        //Adding extends Student sets the upper bound
        for(var student : students){
            System.out.println(student.getYearStarted() + ": " + student);
        }
        System.out.println();
    }

    public static void testList(List<?> list) {

        for(var element : list){
            if(element instanceof String s) {
                System.out.println("String: " + s.toUpperCase());
            } else if (element instanceof Integer i) {
                System.out.println("Integer: " + i.floatValue());
            }
        }
    }

    //In both cases in the methods below, the method parameters, after type erasure, would be a List of Objects.

    //This means these methods won't overload each other in the byte code. They would have exactly the same signature,
    //the same name, and parameter type.

//    public static void testList(List<String> list) {
//
//        for(var element : list){
//            System.out.println("String: " + element.toUpperCase());
//        }
//
//    }
//
//    public static void testList(List<Integer> list) {
//
//        for(var element : list){
//            System.out.println("Integer: " + element.floatValue());
//        }
//
//    }

}




//Limitation of a reference of generic class with a list argument:

//When we declare a variable or method parameter with:

// List<Student>

//Only List subtypes with Student elements can be assigned to this variable or method argument.

//We can't assign a list of Student subtypes to this!

//The generic method:

//For a method, type parameters are placed after any modifiers and before the method's return type.

//The type parameter can be referenced in method parameters, or as the method return type, or in the method code block,
//much as we saw a class's type parameter can be used.

//A generic method can be used for collections with type arguments to allow for variability of the elements in the
//collection, without using a raw version of the collection.

//A generic method can be used for static methods on a generic class, because static methods can't use class type
//parameters.

//A generic method can be used on a non-generic class, to enforce type rules on a specific method.

//The generic method type parameter is separate from a generic class type parameter.

//In fact, if you've used T for both, the T declared on the method means a different type than the T for the class.

//TYPE PARAMETERS, TYPE ARGUMENTS AND USING A WILDCARD:

//A type parameter is a generic class, or generic method's declaration of the type.

//In both examples below, T is said to be the type parameter.

//You can bind a type parameter with the use of the extends keyword to specify an upper bound.

//          Generic Class                                    Generic Method

//          public class Team<T>{}                           public <T> void doSomething(T t) {}

//A type argument declares the type to be used, and is specified in a type reference, such as a local variable reference
//, method parameter declaration, or field declaration.

//In the example below, BaseballPlayer is the type argument for the Team class:

// Team<BaseballPlayer> team = new Team<>();

//A wildcard can only be used in a type argument, not in the type parameter declaration.

//A wildcard is represented with the ? character.

//A wildcard means the type is unknown.

//For this reason, a wildcard limits what you can do when you specify a type this way:

// List<?> unknownList;

//A wildcard can't be used in an instantiation of a generic class.

//The code shown below is invalid:

// var myList = new ArrayList<?>();

//A wildcard can be unbounded, or alternately, specify either an upper bound or a lower bound.

//You can't specify both an upper bound and a lower bound in the same declaration.

//      Argument                 Example                        Description

//      unbounded               List<?>                         A list of any type can be passed or assigned to a List
//                                                              using this wildcard.
//      upper bound             List<? extends Student>         A list containing any type that is a Student or a sub
//                                                              type of Student can be assigned or passed to an argument
//                                                              specifying this wildcard.
//      lower bound             List<? super LPAStudent>        A list containing any type that is an LPAStudent or a
//                                                              super type of LPAStudent, so in our case, that would be
//                                                              Student AND Object.

//TYPE ERASURE

//Generics exist to enforce tighter type checks at compile time.

//The compiler transforms a generic class into a typed class, meaning the byte code, or class file, contains no type
//parameters.

//Everywhere a type parameter is used in a class, it gets replaced with either the type Object, if no upper bound was
//specified, or the upper bound type itself.

//This transformation process is called type erasure, because the T parameter (Or S, U, V), is erased, or replaced with
//a true type.

//Why is this important?

//Understanding how type erasure works for overloaded methods may be important.

