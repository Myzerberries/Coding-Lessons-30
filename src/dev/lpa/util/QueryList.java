package dev.lpa.util;

import dev.lpa.model.Student;

import java.util.ArrayList;
import java.util.List;

//Below, we're saying that any type that uses this class must be a Student or subtype of the Student class, and it also
//must implement the QueryItem interface.
public class QueryList <T extends Student & QueryItem>{

    private List<T> items;

    public QueryList(List<T> items) {
        this.items = items;
    }

    //The class's type parameter can't be used for a static method here (public static List<T>). The generic class's
    //type parameter only has meaning for an instance, and therefore for an instance method. At the class level, this
    //is unknown. When the generic class is loaded into memory, it's not loaded with any type parameter, so you can't
    //use it in a static method.

    //We can, however, make it a generic method by adding a type parameter<T> right before the return type. We add an
    //upper bound for this that extends the QueryItem.

    //What this setup means is that this type parameter is a totally different type, completely separate from the type
    //parameter declared for the class itself. This type either gets specified, or inferred, when you invoke this
    //static method on the class.

    //A generic method's type is unrelated to the type declared on the generic class.
    public static <T extends QueryItem>List<T> getMatches(List<T> items, String field, String value) {

        List<T> matches = new ArrayList<>();
        for(var item : items) {
            if(item.matchFieldValue(field, value)) {
                matches.add(item);
            }
        }
        return matches;
    }
    public List<T> getMatches(String field, String value) {

        List<T> matches = new ArrayList<>();
        for(var item : items) {
            if(item.matchFieldValue(field, value)) {
                matches.add(item);
            }
        }
        return matches;
    }
}

//Using multiple types to declare an upper bound:

//You can use multiple types to set a more restrictive upper bound, with the use of an ampersand between types.

//The conditions require a type argument to implement all interfaces declared, and to be a subtype of any class
//specified.

//You can extend only one class at most, and zero to many interfaces.

//You use extends for either a class or an interface or both.

//If you do extend a class as well as an interface or two, the class must be the first type listed.
