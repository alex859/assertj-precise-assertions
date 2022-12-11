package org.assertj.core.condition;

import org.assertj.core.api.Condition;
import org.assertj.core.description.Description;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.assertj.core.condition.NestableCondition.nestable;

public class CollectionsConditions {
    public static <V> Condition<Iterable<V>> contains(Condition<V> conditionOnElement) {
        return new Contains<>(conditionOnElement);
    }

    @SafeVarargs
    public static <V> Condition<List<V>> elementAt(int index, Condition<V>... conditionsOnElement) {
        return nestable(
                String.format("element at %s", index),
                elements -> elements.get(index),
                conditionsOnElement
        );
    }

    private static class Contains<T> extends Join<Iterable<T>> {
        private Contains(Condition<T> condition) {
            super(toConditionOnIterable(condition));
        }

        @Override
        public boolean matches(Iterable<T> value) {
            return conditions().stream().allMatch(it -> it.matches(value));
        }

        @Override
        public String descriptionPrefix() {
            return "contains";
        }

        private static <V> Condition<Iterable<V>> toConditionOnIterable(Condition<V> condition) {
            var description = condition.description();
            return new Condition<>() {
                @Override
                public boolean matches(Iterable<V> value) {
                    return StreamSupport.stream(value.spliterator(), false).anyMatch(condition::matches);
                }

                @Override
                public Description conditionDescriptionWithStatus(Iterable<V> actual) {
                    return description;
                }
            };
        }
    }
}


