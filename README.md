# AssertJ precise assertions

The goal is to be able to make precise soft assertions on complex objects (i.e. where we don't need to assert the equality of the full object) and to have easy to read test code and assertion error messages.

Let's say that we have in our domain:

```java
public record Person(String firstName, String lastName, Address address) { }

public record Address(String line1, String line2, String postcode, List<House> houses) { }

public record House(int area, String wallType) { }
```

We'd like to write ans assertion like:

```java
assertThat(person).is(
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
)
```

With an assertion error like:

```
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
]
```