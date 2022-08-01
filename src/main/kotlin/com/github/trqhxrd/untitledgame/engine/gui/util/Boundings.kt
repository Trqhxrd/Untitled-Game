package com.github.trqhxrd.untitledgame.engine.gui.util

import org.joml.Vector2f
import org.joml.Vector3f

data class Boundings(val x: Float, val y: Float, val width: Float, val height: Float, val z: Float = 0f) {

    fun vectors() = Vector3f(this.x, this.y, this.z) to Vector2f(this.width, this.height)
}
