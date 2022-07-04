package com.github.trqhxrd.untitledgame.engine

import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import com.github.trqhxrd.untitledgame.engine.threading.RenderThread
import com.github.trqhxrd.untitledgame.engine.threading.TimerThread
import java.lang.Thread.sleep
import kotlin.system.exitProcess

object Core {

    lateinit var renderer: RenderThread
    lateinit var timer: TimerThread

    fun init() {
        Thread.currentThread().name = "main"
        this.timer = TimerThread().also { it.start() }
        this.renderer = RenderThread().also { it.start() }

        this.timer.add(1000) { this.renderer.window.background = Color.BLUE }
    }

    fun close() {
        this.renderer.shutdownGracefully()
        this.timer.shutdownGracefully()
        while (this.renderer.hasStopped || this.timer.hasStopped) sleep(10)
        exitProcess(0)
    }
}
