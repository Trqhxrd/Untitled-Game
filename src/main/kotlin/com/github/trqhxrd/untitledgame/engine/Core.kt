package com.github.trqhxrd.untitledgame.engine

import com.github.trqhxrd.untitledgame.engine.gui.Window
import com.github.trqhxrd.untitledgame.engine.threading.RenderThread
import com.github.trqhxrd.untitledgame.engine.threading.TimerThread
import org.lwjgl.glfw.GLFW
import java.lang.Thread.sleep
import kotlin.system.exitProcess

object Core {

    lateinit var window: Window
    lateinit var renderThread: RenderThread
    lateinit var timer: TimerThread

    var closeRequested = false

    fun init() {
        this.window = Window(1920, 1080, "The Untitled Game: ")
        this.timer = TimerThread().also { it.start() }
        //   this.renderThread = RenderThread().also { it.start() }
        while (!GLFW.glfwWindowShouldClose(this.window.glfw)) this.window.loop()
    }

    fun close() {
      //  this.renderThread.shutdownGracefully()
        this.timer.shutdownGracefully()
        while (
    //        this.renderThread.state != Thread.State.TERMINATED ||
            this.timer.state != Thread.State.TERMINATED
        ) sleep(10)
        exitProcess(0)
    }
}
