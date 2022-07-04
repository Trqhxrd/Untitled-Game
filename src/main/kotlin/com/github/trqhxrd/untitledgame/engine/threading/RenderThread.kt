package com.github.trqhxrd.untitledgame.engine.threading

import com.github.trqhxrd.untitledgame.engine.gui.window.DebugScene
import com.github.trqhxrd.untitledgame.engine.gui.window.Window
import org.apache.logging.log4j.LogManager


class RenderThread : AbstractThread("render", 10) {

    lateinit var window: Window
    private val logger = LogManager.getLogger()

    companion object {
        private const val FPS = 144
        private const val SPF = 1.0 / FPS
    }

    override fun init() {
        this.window = Window(1920, 1080, "The Untitled Game: ")
        this.window.scene = DebugScene(this.window)
    }

    override fun loop() = this.window.update()

    override fun close() {
        this.window.destroy()
    }
}
