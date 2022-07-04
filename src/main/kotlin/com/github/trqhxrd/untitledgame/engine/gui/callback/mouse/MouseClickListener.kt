package com.github.trqhxrd.untitledgame.engine.gui.callback.mouse

import com.github.trqhxrd.untitledgame.engine.gui.window.Window
import com.github.trqhxrd.untitledgame.engine.gui.callback.Action
import java.awt.Point

@FunctionalInterface
interface MouseClickListener {
    fun click(window: Window, pos: Point, button: MouseButton, action: Action)
}
