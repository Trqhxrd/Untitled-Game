package com.github.trqhxrd.untitledgame.engine.gui.display.elements

import com.github.trqhxrd.untitledgame.engine.gui.utils.BoundingBox
import com.github.trqhxrd.untitledgame.engine.gui.display.callbacks.ClickCallback
import java.awt.Graphics2D

interface DisplayElement {

    val bounds: BoundingBox
    var clickCallback: ClickCallback?
    val internalClickCallback:ClickCallback?

    fun draw(graphics: Graphics2D)
}
