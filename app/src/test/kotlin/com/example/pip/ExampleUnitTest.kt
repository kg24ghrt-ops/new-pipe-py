package com.example.pip

import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun subtraction_isCorrect() {
        assertEquals(0, 2 - 2)
    }

    @Test
    fun multiplication_isCorrect() {
        assertEquals(4, 2 * 2)
    }

    @Test
    fun division_isCorrect() {
        assertEquals(1, 2 / 2)
    }

    @Test
    fun string_concatenation() {
        val result = "Hello, " + "World!"
        assertEquals("Hello, World!", result)
    }

    @Test
    fun list_operations() {
        val list = listOf(1, 2, 3, 4, 5)
        assertEquals(5, list.size)
        assertEquals(1, list.first())
        assertEquals(5, list.last())
        assertEquals(15, list.sum())
    }

    @Test
    fun nullable_handling() {
        val nullable: String? = "test"
        val nonNull: String = nullable ?: "default"
        assertEquals("test", nonNull)

        val nullValue: String? = null
        val defaultValue: String = nullValue ?: "default"
        assertEquals("default", defaultValue)
    }
}