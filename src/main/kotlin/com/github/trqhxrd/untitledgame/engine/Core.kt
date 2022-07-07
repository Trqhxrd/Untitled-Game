package com.github.trqhxrd.untitledgame.engine

import com.github.trqhxrd.untitledgame.engine.gui.window.DebugScene
import com.github.trqhxrd.untitledgame.engine.gui.window.Window
import kotlin.system.exitProcess

object Core {

    lateinit var window: Window
    var isShutdownScheduled = false

    fun run() {
        this.init()
        do {
            this.loop()
        } while (!this.isShutdownScheduled)
        this.close()
    }

    private fun init() {
        Thread.currentThread().name = "main"

        this.window = Window(1920, 1080, "The Untitled Game: ")
        this.window.scene = DebugScene()
    }

    private fun loop() = this.window.update()

    private fun close() {
        this.window.destroy()
        exitProcess(0)
    }

    fun shutdown() {
        this.isShutdownScheduled = true
    }
}
