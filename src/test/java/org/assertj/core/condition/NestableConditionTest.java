package org.assertj.core.condition;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.condition.CollectionsConditions.contains;
import static org.assertj.core.condition.Conditions.*;

class NestableConditionTest {
    @Test
    void returnsCorrectErrorMessage() {
        var person = new Person(
                "Alessandro", "Cicciomarra", 
                new Address(
                        "4 Cobra Street", "line 2", "E3 1QT", 
                        List.of(
                                new House(12, "bricks"),
                                new House(13, "cement")
                        )
                )
        );

        assertThatThrownBy(() -> assertThat(person).is(
                person(
                        firstName("Alessandro"),
                        lastName("Ciccimarra"),
                        address(
                                line1("5 Cobra Street"),
                                postcode("E3 1QT"),
                                houses(
                                        contains(house(area(12), wallType("cement")))
                                )
                        )
                )
        )).hasMessageContaining("""
                [✗] person:[
                   [✓] first name: Alessandro,
                   [✗] last name: Ciccimarra but was Cicciomarra,
                   [✗] address:[
                      [✗] line 1: 5 Cobra Street but was 4 Cobra Street,
                      [✓] postcode: E3 1QT,
                      [✗] houses:[
                         [✗] contains:[
                            house:[
                               area: 12,
                               wall type: cement
                            ]
                         ]
                      ]
                   ]
                ]""".stripIndent()
        );
    }

    @Test
    void doesNotFailWhenAllMatches() {
        var person = new Person(
                "Alessandro", "Ciccimarra",
                new Address(
                        "5 Cobra Street", "line 2", "E3 1QT",
                        List.of(
                                new House(12, "bricks"),
                                new House(13, "cement")
                        )
                )
        );

        assertThat(person).is(
                person(
                        firstName("Alessandro"),
                        lastName("Ciccimarra"),
                        address(
                                line1("5 Cobra Street"),
                                postcode("E3 1QT"),
                                houses(
                                        contains(house(area(12), wallType("bricks")))
                                )
                        )
                )
        );
    }
}
