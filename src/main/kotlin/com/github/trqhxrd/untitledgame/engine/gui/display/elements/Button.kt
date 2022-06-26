package com.github.trqhxrd.untitledgame.engine.gui.display.elements

import com.github.trqhxrd.untitledgame.engine.Core
import com.github.trqhxrd.untitledgame.engine.gui.display.callbacks.ClickCallback
import com.github.trqhxrd.untitledgame.engine.gui.utils.BoundingBox
import com.github.trqhxrd.untitledgame.engine.gui.utils.MouseButton
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point

class Button(override val bounds: BoundingBox) : DisplayElement {

    private var color = Color.DARK_GRAY

    override var clickCallback: ClickCallback? = null
    override val internalClickCallback: ClickCallback = object : ClickCallback {
        override fun onClick(element: DisplayElement, mouseLocation: Point, clickType: MouseButton) {
            this@Button.color = Color.RED
            Core.timer.add(50) {
                this@Button.color = Color.DARK_GRAY
            }
        }
    }

    override fun draw(graphics: Graphics2D) {
        graphics.color = this.color
        graphics.fillRect(this.bounds.x, this.bounds.y, this.bounds.sizeX, this.bounds.sizeY)
    }
}
