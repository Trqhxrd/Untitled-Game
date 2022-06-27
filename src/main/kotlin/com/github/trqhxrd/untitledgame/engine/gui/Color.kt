package com.github.trqhxrd.untitledgame.engine.gui

import kotlin.math.max
import kotlin.math.min

class Color(red: Float, green: Float, blue: Float, alpha: Float) {

    val red: Float = this.check(red)
    val green: Float = this.check(green)
    val blue: Float = this.check(blue)
    val alpha: Float = this.check(alpha)

    companion object {
        val WHITE = Color(1f, 1f, 1f, 1f)
        val GRAY = Color(.5f, .5f, .5f, 1f)
        val BLACK = Color(0f, 0f, 0f, 1f)
        val RED = Color(1f, 0f, 0f, 1f)
        val GREEN = Color(0f, 1f, 0f, 1f)
        val BLUE = Color(0f, 0f, 1f, 1f)
        val MAGENTA = Color(1f, 0f, 1f, 1f)
        val YELLOW = Color(1f, 1f, 0f, 1f)
        val CYAN = Color(0f, 1f, 1f, 1f)

        val values = arrayOf(WHITE, GRAY, BLACK, RED, GREEN, BLUE, MAGENTA, YELLOW, CYAN)
    }

    constructor(color: Color) : this(color.red, color.green, color.blue, color.alpha)
    constructor(red: Float, green: Float, blue: Float) : this(red, green, blue, 1f)

    fun add(color: Color) = this.add(color.red, color.green, color.blue, color.alpha)

    fun add(red: Float = 0f, green: Float = 0f, blue: Float = 0f, alpha: Float = 0f) = Color(
        this.red + red,
        this.green + green,
        this.blue + blue,
        this.alpha + alpha
    )

    private fun check(color: Float): Float = min(max(color, 0f), 1f)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Color

        if (red != other.red) return false
        if (green != other.green) return false
        if (blue != other.blue) return false
        if (alpha != other.alpha) return false

        return true
    }

    override fun hashCode(): Int {
        var result = red.hashCode()
        result = 31 * result + green.hashCode()
        result = 31 * result + blue.hashCode()
        result = 31 * result + alpha.hashCode()
        return result
    }

    override fun toString(): String {
        return "Color(red=$red, green=$green, blue=$blue, alpha=$alpha)"
    }
}
