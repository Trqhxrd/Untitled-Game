package com.github.trqhxrd.untitledgame.engine

import org.apache.logging.log4j.LogManager
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import java.awt.Color
import kotlin.system.exitProcess

class Window(val name: String, var width: Int, var height: Int) {

    val windowID: Long

    companion object {
        val logger = LogManager.getLogger(Window::class.java)
    }

    init {
        if (!GLFW.glfwInit()) {
            logger.fatal("Could not initialize LWJGL.")
            exitProcess(1)
        }

        this.windowID = GLFW.glfwCreateWindow(this.width, this.height, this.name, 0, 0)
        this.makeContextCurrent()

        GL.createCapabilities()
    }

    fun show() = GLFW.glfwShowWindow(this.windowID)

    fun hide() = GLFW.glfwHideWindow(this.windowID)

    fun makeContextCurrent() = GLFW.glfwMakeContextCurrent(this.windowID)

    fun fillColor(color: Color) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
        GL11.glClearColor(
            (color.red / 255.0).toFloat(),
            (color.green / 255.0).toFloat(),
            (color.blue / 255.0).toFloat(),
            (color.alpha / 255.0).toFloat()
        )
    }

    fun swapBuffers() = GLFW.glfwSwapBuffers(this.windowID)

    fun checkForClose() = GLFW.glfwWindowShouldClose(this.windowID)

    fun close() = GLFW.glfwSetWindowShouldClose(this.windowID, true)
    fun pollEvents() = GLFW.glfwPollEvents()
}