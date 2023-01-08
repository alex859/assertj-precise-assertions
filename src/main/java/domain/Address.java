package domain;

import java.util.List;

public record Address(String line1, String line2, String postcode, List<House> houses) {
}
