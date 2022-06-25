package com.github.trqhxrd.untitledgame.engine.gui.display.callbacks

import com.github.trqhxrd.untitledgame.engine.gui.MouseButton
import com.github.trqhxrd.untitledgame.engine.gui.display.DisplayElement
import java.awt.Point

@FunctionalInterface
interface ClickCallback {

    fun onClick(element: DisplayElement, mouseLocation: Point, clickType: MouseButton)
}
