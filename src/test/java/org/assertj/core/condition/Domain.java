package org.assertj.core.condition;

import java.util.List;

record Person(String firstName, String lastName, Address address) {
}

record Address(String line1, String line2, String postcode, List<House> houses) {
}

record House(int area, String wallType) {
}
