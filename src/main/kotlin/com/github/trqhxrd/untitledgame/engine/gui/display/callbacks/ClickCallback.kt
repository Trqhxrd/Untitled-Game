package com.github.trqhxrd.untitledgame.engine.gui.display.callbacks

import com.github.trqhxrd.untitledgame.engine.gui.utils.MouseButton
import com.github.trqhxrd.untitledgame.engine.gui.display.elements.DisplayElement
import java.awt.Point

@FunctionalInterface
interface ClickCallback {

    fun onClick(element: DisplayElement, mouseLocation: Point, clickType: MouseButton)
}
