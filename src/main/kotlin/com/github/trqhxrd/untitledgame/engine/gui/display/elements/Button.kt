package com.github.trqhxrd.untitledgame.engine.gui.display.elements

import com.github.trqhxrd.untitledgame.engine.gui.display.callbacks.ClickCallback
import com.github.trqhxrd.untitledgame.engine.gui.utils.BoundingBox
import java.awt.Graphics2D

class Button(override val bounds: BoundingBox) : DisplayElement {

    override var clickCallback: ClickCallback? = null
    override val internalClickCallback: ClickCallback? = null

    override fun draw(graphics: Graphics2D) {
        graphics.fillRect(this.bounds.x, this.bounds.y, this.bounds.sizeX, this.bounds.sizeY)
    }
}
