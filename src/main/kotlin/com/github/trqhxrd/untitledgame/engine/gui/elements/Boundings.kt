package com.github.trqhxrd.untitledgame.engine.gui.elements

import java.awt.Point

data class Boundings(val x: Int, val y: Int, val width: Int, val height: Int) {

    fun contains(point: Point) = this.x <= point.x &&
            this.y <= point.y &&
            this.x + this.width > point.x &&
            this.y + this.height > point.y

    fun contains(x: Int, y: Int) = this.contains(Point(x, y))
}
