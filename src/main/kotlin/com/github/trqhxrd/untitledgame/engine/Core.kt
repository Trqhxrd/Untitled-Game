package com.github.trqhxrd.untitledgame.engine

import com.github.trqhxrd.untitledgame.engine.threading.RenderThread
import java.lang.Thread.sleep
import kotlin.system.exitProcess

object Core {

    lateinit var renderer: RenderThread

    fun init() {
        Thread.currentThread().name = "main"
        this.renderer = RenderThread().also { it.start() }
    }

    fun close() {
        this.renderer.shutdownGracefully()
        while (!this.renderer.hasStopped) sleep(10)
        exitProcess(0)
    }
}
