package org.assertj.core.condition;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.condition.CollectionsConditions.contains;
import static org.assertj.core.condition.CollectionsConditions.elementAt;
import static org.assertj.core.condition.Conditions.*;

class CollectionConditionsTest {
    @Test
    @DisplayName("contains failure message")
    void containsHouse() {
        var house1 = new House(12, "bricks");
        var house2 = new House(1, "bricks");
        var address = new Address("line 1", "line 2", "E31QT", List.of(house1, house2));

        assertThatThrownBy(() -> assertThat(address).has(
                houses(
                        contains(house(area(2), wallType("bricks"))),
                        contains(house(area(1), wallType("bricks")))
                )

        )).hasMessageContaining("""
                [✗] houses:[
                   [✗] contains:[
                      house:[
                         area: 2,
                         wall type: bricks
                      ]
                   ],
                   [✓] contains:[
                      house:[
                         area: 1,
                         wall type: bricks
                      ]
                   ]
                ]""".stripIndent());
    }


    @Test
    @DisplayName("element at failure message")
    void containsHouseAtPosition() {
        var house1 = new House(12, "bricks");
        var house2 = new House(1, "bricks");
        var address = new Address("line 1", "line 2", "E31QT", List.of(house1, house2));

        assertThatThrownBy(() -> assertThat(address).has(
                houses(
                        elementAt(0, house(area(2), wallType("bricks"))),
                        elementAt(1, house(area(1), wallType("bricks")))
                )

        )).hasMessageContaining("""
                [✗] houses:[
                   [✗] element at 0:[
                      [✗] house:[
                         [✗] area: 2 but was 12,
                         [✓] wall type: bricks
                      ]
                   ],
                   [✓] element at 1:[
                      [✓] house:[
                         [✓] area: 1,
                         [✓] wall type: bricks
                      ]
                   ]
                ]""".stripIndent());
    }
}
