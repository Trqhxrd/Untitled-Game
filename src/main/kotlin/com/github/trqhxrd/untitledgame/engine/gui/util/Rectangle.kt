package com.github.trqhxrd.untitledgame.engine.gui.util

import org.joml.Vector2f

class Rectangle(val x: Float, val y: Float, val width: Float, val height: Float) {

    fun vectors() = Vector2f(this.x, this.y) to Vector2f(this.width, this.height)
}
