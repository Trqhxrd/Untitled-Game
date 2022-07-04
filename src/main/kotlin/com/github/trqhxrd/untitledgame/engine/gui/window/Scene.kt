package com.github.trqhxrd.untitledgame.engine.gui.window

import com.github.trqhxrd.untitledgame.engine.gui.listener.KeyHandler
import com.github.trqhxrd.untitledgame.engine.gui.listener.MouseHandler
import com.github.trqhxrd.untitledgame.engine.gui.util.Color

@Suppress("LeakingThis")
abstract class Scene(val window: Window, val name: String, var background: Color = Color.WHITE) {

    val mouseHandler = MouseHandler(this)
    val keyHandler = KeyHandler(this)

    open fun init() {
        this.mouseHandler.enable()
        this.keyHandler.enable()
    }

    abstract fun render()

    open fun stop() {
        this.mouseHandler.disable()
        this.keyHandler.disable()
    }
}
