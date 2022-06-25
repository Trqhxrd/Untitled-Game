package com.github.trqhxrd.untitledgame.engine

import com.github.trqhxrd.untitledgame.engine.gui.GUI
import com.github.trqhxrd.untitledgame.engine.threading.RenderThread
import kotlin.system.exitProcess

object Core {

    lateinit var gui: GUI
    lateinit var renderThread: RenderThread

    var closeRequested = false

    fun init() {
        this.gui = GUI(1920, 1080, "Untitled Game: ")
        this.renderThread = RenderThread().also { it.start() }
        this.gui.visibility(true)
    }

    fun close() {
        this.renderThread.shutdownGracefully()
        while (this.renderThread.state != Thread.State.TERMINATED) Thread.sleep(10)
        exitProcess(0)
    }
}
