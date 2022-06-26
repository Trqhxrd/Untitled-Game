package com.github.trqhxrd.untitledgame.engine.gui.callback.mouse

import com.github.trqhxrd.untitledgame.engine.gui.Window
import java.awt.Point

@FunctionalInterface
interface MouseMoveListener {
    fun move(window: Window, from: Point, to: Point, delta: Point)
}