package com.github.trqhxrd.untitledgame.engine.gui

import org.junit.jupiter.api.Test
import java.util.concurrent.ThreadLocalRandom
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ColorTest {

    @Test
    fun testInit() {
        assertEquals(Color(1f, 0f, 0f, 1f), Color(3f, 0f, 0f, 1f))
        assertEquals(Color(0f, 0f, 0f, 1f), Color(-3f, 0f, 0f, 1f))
        assertNotEquals(Color(1f, 0f, 0f, 1f), Color(3f, 0f, 1f, 1f))
    }

    @Test
    fun testAdd() {
        val c1 = Color.RED
        val c2 = c1.add(Color.BLUE)

        assertTrue(c1 !== c2)
        assertEquals(Color.RED, c1)
        assertEquals(Color.MAGENTA, c2)
    }

    @Test
    fun testEquals() {
        assertNotEquals(Color.RED, Color.BLUE)
        assertEquals(Color(1f, 1f, 1f), Color(1f, 1f, 1f))
        assertNotEquals(Color(1f, 1f, 1f), Color(1f, 1f, 0f))
    }

    @Test
    fun testHashCode() {
        assertEquals(Color.RED.hashCode(), Color(1f, 0f, 0f, 1f).hashCode())
        assertNotEquals(Color.RED.hashCode(), Color.BLUE.hashCode())
    }

    @Test
    fun testToString() {
        val random = ThreadLocalRandom.current()
        val r = random.nextFloat()
        val g = random.nextFloat()
        val b = random.nextFloat()
        val a = random.nextFloat()
        val c = Color(r, g, b, a)
        assertEquals("Color(red=$r, green=$g, blue=$b, alpha=$a)", c.toString())
    }
}
