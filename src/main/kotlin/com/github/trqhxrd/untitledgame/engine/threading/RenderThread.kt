package com.github.trqhxrd.untitledgame.engine.threading

import com.github.trqhxrd.untitledgame.engine.Core
import kotlin.system.measureTimeMillis

class RenderThread : AbstractThread("render", 10) {
    override fun loop() {
        println(measureTimeMillis { Core.gui.display?.repaint() })
    }
}
