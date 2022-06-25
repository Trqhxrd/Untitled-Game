package com.github.trqhxrd.untitledgame.engine.gui.impl

import com.github.trqhxrd.untitledgame.engine.Core
import com.github.trqhxrd.untitledgame.engine.gui.display.elements.Button
import com.github.trqhxrd.untitledgame.engine.gui.display.Display
import com.github.trqhxrd.untitledgame.engine.gui.utils.BoundingBox

class MainDisplay() : Display() {
    init {
        this.addElement(Button(BoundingBox(Core.gui.frame.width / 2 - 100, Core.gui.frame.height / 2 - 35, 200, 70)))
    }
}
