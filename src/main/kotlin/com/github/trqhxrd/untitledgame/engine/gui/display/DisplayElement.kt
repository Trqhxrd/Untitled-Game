package com.github.trqhxrd.untitledgame.engine.gui.display

import com.github.trqhxrd.untitledgame.engine.gui.BoundingBox
import com.github.trqhxrd.untitledgame.engine.gui.display.callbacks.ClickCallback
import java.awt.Graphics2D

interface DisplayElement {

    val bounds: BoundingBox
    var clickCallback: ClickCallback?

    fun draw(graphics: Graphics2D)
}
