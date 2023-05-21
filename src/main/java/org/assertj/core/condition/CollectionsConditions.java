package org.assertj.core.condition;

import org.assertj.core.api.Condition;
import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;

import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static org.assertj.core.condition.NestableCondition.nestable;

public class CollectionsConditions {
    public static <V> Condition<Iterable<? extends V>> contains(Condition<V> conditionOnElement) {
        return new Contains<>(conditionOnElement);
    }

    public static <V> Condition<List<? extends V>> elementAt(int index, Condition<V> conditionsOnElement) {
        return nestable(
                String.format("element at %s", index),
                elements -> getOrNull(elements, index),
                checkingNull(conditionsOnElement, "missing element with index %s".formatted(index))
        );
    }
    
    private static <T> T getOrNull(List<? extends T> list, int index) {
        return list.size() > index 
                ? list.get(index) 
                : null;
    }

    private static class Contains<T> extends Join<Iterable<? extends T>> {
        private Contains(Condition<? super T> condition) {
            super(toConditionOnIterable(condition));
        }

        @Override
        public boolean matches(Iterable<? extends T> value) {
            return conditions().stream().allMatch(it -> it.matches(value));
        }

        @Override
        public String descriptionPrefix() {
            return "contains";
        }

        private static <V> Condition<Iterable<? extends V>> toConditionOnIterable(Condition<? super V> condition) {
            var description = condition.description();
            return new Condition<>() {
                @Override
                public boolean matches(Iterable<? extends V> value) {
                    return StreamSupport.stream(value.spliterator(), false).anyMatch(condition::matches);
                }

                @Override
                public Description conditionDescriptionWithStatus(Iterable<? extends V> actual) {
                    return description;
                }
            };
        }
    }

    public static <K, V> Condition<Map<K, V>> elementWithKey(K key, Condition<? super V> conditionsOnElement) {
        return nestable(
                String.format("element with key '%s'", key),
                elements -> elements.get(key),
                checkingNull(conditionsOnElement, "missing key '%s'".formatted(key))
        );
    }
    
    private static <V> Condition<V> checkingNull(Condition<V> condition, String message) {
        return new Condition<>(condition.description()) {
            @Override
            public boolean matches(V value) {
                return value != null && condition.matches(value);
            }

            @Override
            public Description conditionDescriptionWithStatus(V actual) {
                return actual == null 
                        ? new TextDescription(message) 
                        : condition.conditionDescriptionWithStatus(actual);
            }
        };
    }
}


