# AssertJ precise assertions

The goal is to be able to make precise soft assertions on complex objects (i.e. where we don't need to assert the equality of the full object) and to have easy to read test code and assertion error messages.

Example:

```java
assertThat(customer).is(
    customer(
        name(
            first("Boris"),
            last("Johnson")),
            address(
                firstLine("10, Downing Street"),
                postcode("SW2A 2AA")
         )
    )
)
``` 

and on failure get an error message like:

```
[✗] customer:[
   [✗] name:[
      [✓] first: Boris,
      [✗] last: Johnson but was Johnstone
   ],
   [✗] address:[
      [✓] first line: 10, Downing Street,
      [✗] postcode: SW2A 2AA but was SW1A 2AA
   ]
```
