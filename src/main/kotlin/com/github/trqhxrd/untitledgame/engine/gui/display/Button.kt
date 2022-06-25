package com.github.trqhxrd.untitledgame.engine.gui.display

import com.github.trqhxrd.untitledgame.engine.gui.BoundingBox
import com.github.trqhxrd.untitledgame.engine.gui.MouseButton
import com.github.trqhxrd.untitledgame.engine.gui.display.callbacks.ClickCallback
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point

class Button(override val bounds: BoundingBox) : DisplayElement {

    var color = Color.RED
    override var clickCallback: ClickCallback? = object : ClickCallback {
        override fun onClick(element: DisplayElement, mouseLocation: Point, clickType: MouseButton) {
            if (element !is Button) return
            element.color = Color.BLUE
        }
    }

    override fun draw(graphics: Graphics2D) {
        graphics.color = color
        graphics.fillRect(this.bounds.x, this.bounds.y, this.bounds.sizeX, this.bounds.sizeY)
    }
}
