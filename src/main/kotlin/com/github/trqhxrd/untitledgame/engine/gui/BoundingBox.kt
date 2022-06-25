package com.github.trqhxrd.untitledgame.engine.gui

import java.awt.Point

data class BoundingBox(val x: Int, val y: Int, val sizeX: Int, val sizeY: Int) {
    fun contains(point: Point) = this.contains(point.x, point.y)
    fun contains(x: Int, y: Int) = this.x <= x && this.y <= y && this.x + this.sizeX >= x && this.y + this.sizeY >= y
}
