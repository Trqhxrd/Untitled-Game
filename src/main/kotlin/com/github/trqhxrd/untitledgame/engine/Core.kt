package com.github.trqhxrd.untitledgame.engine

import com.github.trqhxrd.untitledgame.engine.gui.GUI
import com.github.trqhxrd.untitledgame.engine.gui.impl.MainDisplay
import com.github.trqhxrd.untitledgame.engine.threading.RenderThread
import com.github.trqhxrd.untitledgame.engine.threading.TimerThread
import kotlin.system.exitProcess

object Core {

    lateinit var gui: GUI
    lateinit var renderThread: RenderThread
    lateinit var timer: TimerThread

    var closeRequested = false

    fun init() {
        this.timer = TimerThread().also { it.start() }

        this.gui = GUI(1920, 1080, "Untitled Game: ")
        this.gui.setCurrentDisplay(MainDisplay())
        this.renderThread = RenderThread().also { it.start() }
        this.gui.visibility(true)
    }

    fun close() {
        this.renderThread.interrupt()
        //this.renderThread.shutdownGracefully()
        this.timer.shutdownGracefully()
        while (this.renderThread.state != Thread.State.TERMINATED ||
            this.timer.state != Thread.State.TERMINATED
        )
            Thread.sleep(10)
        exitProcess(0)
    }
}
