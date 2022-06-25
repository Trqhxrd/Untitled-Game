package com.github.trqhxrd.untitledgame.engine.threading

import com.github.trqhxrd.untitledgame.engine.Core

class RenderThread : AbstractThread("render", 10) {
    override fun loop() {
        Core.gui.display.repaint()
    }
}
