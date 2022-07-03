package com.github.trqhxrd.untitledgame.engine.gui.callback.mouse

import com.github.trqhxrd.untitledgame.engine.gui.Window
import java.awt.Point

@FunctionalInterface
interface MouseScrollListener {
    fun scroll(window: Window, offset: Point)
}