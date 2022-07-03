package com.github.trqhxrd.untitledgame.engine.threading

import com.github.trqhxrd.untitledgame.engine.gui.Window
import com.github.trqhxrd.untitledgame.engine.gui.util.Time
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
    }

    override fun loop() = this.window.update()

    private fun sync(loopStartTime: Double) {
        val loopSlot = 1f / 50
        val endTime = loopStartTime + loopSlot
        while (Time.now() < endTime) try {
            sleep(1)
        } catch (_: InterruptedException) {
        }
    }

    override fun close() {
        this.window.destroy()
    }
}
