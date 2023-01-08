package org.assertj.core.condition

import domain.Address
import domain.House
import domain.Person
import org.assertj.core.api.Assertions.allOf
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.assertj.core.api.ObjectAssert
import org.assertj.core.condition.CollectionsConditions.contains
import org.assertj.core.condition.Conditions.*
import org.junit.jupiter.api.Test

class AssertJKotlinTest {
    @Test
    fun `port from java`() {
        val person = Person(
            "Alessandro", "Cicciomarra",
            Address(
                "5 Cobra Street", "line 2", "E3 1QT",
                listOf(
                    House(12, "bricks"),
                    House(13, "cement")
                )
            )
        )

        assertThat(person).`is`(
            person(
                firstName("Alessandro"),
                lastName("Ciccimarra"),
                address(
                    line1("5 Cobra Street"),
                    postcode("E3 1QT"),
                    houses(
                        contains(
                            house(
                                area(12),
                                wallType("bricks")
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `use has directly`() {
        val person = Person(
            "Alessandro", "Cicciomarra",
            Address(
                "5 Cobra Street", "line 2", "E3 1QT",
                listOf(
                    House(12, "bricks"),
                    House(13, "cement")
                )
            )
        )

        assertThat(person).has(
            firstName("Alessandro"),
            lastName("Ciccimarra"),
            address(
                line1("5 Cobra Street"),
                postcode("E3 1QT"),
                houses(
                    contains(
                        house(
                            area(12),
                            wallType("bricks")
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `use builder`() {
        val person = Person(
            "Alessandro", "Cicciomarra",
            Address(
                "5 Cobra Street", "line 2", "E3 1QT",
                listOf(
                    House(12, "bricks"),
                    House(13, "cement")
                )
            )
        )

        assertThat(person).has {
            firstName("Alessandro")
            lastName("Ciccimarra")
            address {
                line1("5 Cobra Street")
                postcode("E5 9TE")
                houses {
                    contains(
                        house {
                            area(13)
                            wallType("bricks")       
                        }
                    )
                }
            }
        }
    }
}

fun <T> ObjectAssert<T>.has(vararg conditions: Condition<T>): ObjectAssert<T> = has(allOf(*conditions))

fun <T> ObjectAssert<T>.has(config: ConditionBuilder<T>.() -> Unit): ObjectAssert<T> =
    has(allOf(ConditionBuilder<T>().apply(config).build().toList()))

class ConditionBuilder<T> {
    private val conditions = mutableListOf<Condition<T>>()
    
    fun add(condition: Condition<T>) {
        conditions.add(condition)
    }
    
    fun build() = conditions.toTypedArray()
}

fun ConditionBuilder<Person>.firstName(expected: String) = add(Conditions.firstName(expected))
fun ConditionBuilder<Person>.lastName(expected: String) = add(Conditions.lastName(expected))
fun ConditionBuilder<Person>.address(config: ConditionBuilder<Address>.() -> Unit) =
    add(Conditions.address(*ConditionBuilder<Address>().apply(config).build()))

fun ConditionBuilder<Address>.line1(expected: String) = add(Conditions.line1(expected))
fun ConditionBuilder<Address>.postcode(expected: String) = add(Conditions.postcode(expected))
fun ConditionBuilder<Address>.houses(config: ConditionBuilder<Iterable<House>>.() -> Unit) =
    add(houses(*ConditionBuilder<Iterable<House>>().apply(config).build()))

fun ConditionBuilder<Iterable<House>>.contains(condition: Condition<House>) =
    add(CollectionsConditions.contains(condition))

fun house(config: ConditionBuilder<House>.() -> Unit): Condition<House> = 
    Conditions.house(*ConditionBuilder<House>().apply(config).build())
fun ConditionBuilder<House>.area(expected: Int) = add(Conditions.area(expected))
fun ConditionBuilder<House>.wallType(expected: String) = add(Conditions.wallType(expected))

