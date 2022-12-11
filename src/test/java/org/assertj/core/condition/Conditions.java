package org.assertj.core.condition;

import org.assertj.core.api.Condition;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.condition.NestableCondition.nestable;
import static org.assertj.core.condition.VerboseCondition.verboseCondition;

class Conditions {
    @SafeVarargs
    static Condition<Person> person(Condition<Person>... conditions) {
        return nestable("person", conditions);
    }

    static Condition<Person> firstName(String expected) {
        return isEqual("first name", Person::firstName, expected);
    }

    static Condition<Person> lastName(String expected) {
        return isEqual("last name", Person::lastName, expected);
    }

    @SafeVarargs
    static Condition<Person> address(Condition<Address>... conditions) {
        return nestable("address", Person::address,conditions);
    }

    static Condition<Address> line1(String expected) {
        return isEqual("line 1", Address::line1, expected);
    }
    
    static Condition<Address> postcode(String expected) {
        return isEqual("postcode", Address::postcode, expected);
    }

    @SafeVarargs
    static Condition<Address> houses(Condition<? super List<House>>... conditions) {
        return nestable("houses", Address::houses, conditions);
    }


    @SafeVarargs
    static Condition<House> house(Condition<House>... conditions) {
        return nestable("house", conditions);
    }

    static Condition<House> area(int expected) {
        return isEqual("area", House::area, expected);
    }

    static Condition<House> wallType(String expected) {
        return isEqual("wall type", House::wallType, expected);
    }
    
    static <T, V> Condition<T> isEqual(String description, Function<T, V> actual, V expected) {
        return verboseCondition(
                it -> expected.equals(actual.apply(it)),
                "%s: %s".formatted(description, expected),
                it -> " but was %s".formatted(actual.apply(it))
        );
    }
}
