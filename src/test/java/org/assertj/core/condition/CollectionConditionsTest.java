package org.assertj.core.condition;

import domain.Address;
import domain.House;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.condition.CollectionsConditions.contains;
import static org.assertj.core.condition.CollectionsConditions.elementAt;
import static org.assertj.core.condition.CollectionsConditions.elementWithKey;
import static org.assertj.core.condition.Conditions.area;
import static org.assertj.core.condition.Conditions.house;
import static org.assertj.core.condition.Conditions.houses;
import static org.assertj.core.condition.Conditions.wallType;

class CollectionConditionsTest {
    @Test
    void containsThrows() {
        var house1 = new House(12, "bricks");
        var house2 = new House(1, "bricks");
        var address = new Address("line 1", "line 2", "E31QT", List.of(house1, house2));

        assertThatThrownBy(() -> assertThat(address).has(
                houses(
                        contains(house(area(2), wallType("bricks"))),
                        contains(house(area(1), wallType("bricks")))
                )

        ));
    }

    @Test
    void containsSucceeds() {
        var house1 = new House(12, "bricks");
        var house2 = new House(1, "cement");
        var address = new Address("line 1", "line 2", "E31QT", List.of(house1, house2));

        assertThat(address).has(
                houses(
                        contains(house(area(1), wallType("cement"))),
                        contains(house(area(12), wallType("bricks")))
                )

        );
    }

    @Test
    void containsMessage() {
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
    void elementAtFails() {
        var house1 = new House(12, "bricks");
        var house2 = new House(1, "bricks");
        var address = new Address("line 1", "line 2", "E31QT", List.of(house1, house2));

        assertThatThrownBy(() -> assertThat(address).has(
                houses(
                        elementAt(0, house(area(2), wallType("bricks"))),
                        elementAt(1, house(area(1), wallType("bricks")))
                )

        ));
    }

    @Test
    void elementAtSuccedes() {
        var house1 = new House(12, "bricks");
        var house2 = new House(1, "bricks");
        var address = new Address("line 1", "line 2", "E31QT", List.of(house1, house2));

        assertThat(address).has(
                houses(
                        elementAt(0, house(area(12), wallType("bricks"))),
                        elementAt(1, house(area(1), wallType("bricks")))
                )

        );
    }
    
    @Test
    void elementAtMessage() {
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

    @Test
    void keyFails() {
        var house1 = new House(12, "bricks");
        var house2 = new House(1, "bricks");
        var map = Map.of("house 1", house1, "house 2", house2);

        assertThatThrownBy(() -> assertThat(map).has(
                allOf(
                        elementWithKey("house 1", house(area(2), wallType("bricks"))),
                        elementWithKey("house 2", house(area(1), wallType("bricks")))
                )
        ));
    }   
    
    @Test
    void keyFailsWithMessage() {
        var house1 = new House(12, "bricks");
        var house2 = new House(1, "bricks");
        var map = Map.of("house 1", house1, "house 2", house2);

        assertThatThrownBy(() -> assertThat(map).has(
                allOf(
                        elementWithKey("house 1", house(area(2), wallType("bricks"))),
                        elementWithKey("house 2", house(area(1), wallType("bricks")))  
                )
        )).hasMessageContaining("""
                [✗] all of:[
                   [✗] element with key 'house 1':[
                      [✗] house:[
                         [✗] area: 2 but was 12,
                         [✓] wall type: bricks
                      ]
                   ],
                   [✓] element with key 'house 2':[
                      [✓] house:[
                         [✓] area: 1,
                         [✓] wall type: bricks
                      ]
                   ]
                ]""".stripIndent());
    }

    @Test
    void keySuccedes() {
        var house1 = new House(12, "bricks");
        var house2 = new House(1, "bricks");
        var map = Map.of("house 1", house1, "house 2", house2);

        assertThat(map).has(
                allOf(
                        elementWithKey("house 1", house(area(12), wallType("bricks"))),
                        elementWithKey("house 2", house(area(1), wallType("bricks")))
                )
        );
    }
}
