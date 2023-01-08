package org.assertj.core.condition

import domain.Address
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class StriktTest {
    @Test
    fun `test`() {
        val address = Address("line 1", "line 2", "E3 9RT", emptyList())
        expectThat(address) {
            isEqualTo(Address("", "", "", emptyList()))
            get { line1 }.isEqualTo("line 1")
            get { line1 }.isEqualTo("line 2")
        }
    }
}

data class AnAddress(
    val line1: String,
    val line2: String,
    val postcode: String,
) {
    fun test() = 11
}