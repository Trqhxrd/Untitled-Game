package com.github.trqhxrd.untitledgame.engine.threading

import com.github.trqhxrd.untitledgame.engine.gui.Color
import com.github.trqhxrd.untitledgame.engine.gui.Window
import com.github.trqhxrd.untitledgame.engine.gui.scene.DebugScene

class RenderThread : AbstractThread("render", 10) {

    lateinit var window: Window

    override fun init() {
        this.window = Window(1920, 1080, "The Untitled Game: ").also {
            it.scene = DebugScene(it)
        }
    }

    override fun loop() {
        this.window.update()
    }

    override fun close() {
        this.window.destroy()
    }
}
