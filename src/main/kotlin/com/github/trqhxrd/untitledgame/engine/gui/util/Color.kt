package com.github.trqhxrd.untitledgame.engine.gui.util

import java.util.concurrent.ThreadLocalRandom
import kotlin.math.min

class Color(red: Float, green: Float, blue: Float, alpha: Float) {

    val red: Float
    val green: Float
    val blue: Float
    val alpha: Float

    init {
        this.red = this.check(red)
        this.green = this.check(green)
        this.blue = this.check(blue)
        this.alpha = this.check(alpha)
    }

    companion object {
        val randomProvider = { ThreadLocalRandom.current() }
        fun random(): Color {
            return Color(
                this.randomProvider().nextFloat(),
                this.randomProvider().nextFloat(),
                this.randomProvider().nextFloat(),
                this.randomProvider().nextFloat()
            )
        }

        val WHITE = Color(1f, 1f, 1f, 1f)
        val GRAY = Color(.5f, .5f, .5f, 1f)
        val BLACK = Color(0f, 0f, 0f, 1f)
        val RED = Color(1f, 0f, 0f, 1f)
        val GREEN = Color(0f, 1f, 0f, 1f)
        val BLUE = Color(0f, 0f, 1f, 1f)
        val MAGENTA = Color(1f, 0f, 1f, 1f)
        val YELLOW = Color(1f, 1f, 0f, 1f)
        val CYAN = Color(0f, 1f, 1f, 1f)
    }

    constructor(color: Color) : this(color.red, color.green, color.blue, color.alpha)
    constructor(red: Float, green: Float, blue: Float) : this(red, green, blue, 1f)

    fun add(color: Color) = this.add(color.red, color.green, color.blue, color.alpha)

    fun add(red: Float = 0f, green: Float = 0f, blue: Float = 0f, alpha: Float = 0f) = Color(
        min(this.red + red, 1f),
        min(this.green + green, 1f),
        min(this.blue + blue, 1f),
        min(this.alpha + alpha, 1f)
    )

    private fun check(color: Float): Float {
        if (!(0f..1f).contains(color))
            throw IllegalArgumentException("Cannot assign color value smaller than 0 or greater than 1 ($color).")
        else return color
    }

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
