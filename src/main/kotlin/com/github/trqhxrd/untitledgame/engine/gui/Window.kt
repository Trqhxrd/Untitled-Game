package com.github.trqhxrd.untitledgame.engine.gui

import com.github.trqhxrd.untitledgame.engine.Core
import com.github.trqhxrd.untitledgame.engine.gui.scene.Scene
import org.apache.logging.log4j.LogManager
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryUtil

class Window(val initialWidth: Int, val initialHeight: Int, title: String, scene: Scene? = null) {

    var glfw: Long = -1
        private set
    var title: String

    var scene: Scene? = scene
        set(value) {
            field?.disable()
            value?.enable()
            field = value
        }

    private var beginTime = Utils.getTime()
    private var endTime = -1f
    private var dTime = -1f

    init {
        this.title = title + randomTitleSuffix()

        GLFWErrorCallback.createPrint(System.err).set()
        if (!GLFW.glfwInit()) throw IllegalStateException("GLFW could not be initialized!")

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE)

        this.glfw = GLFW.glfwCreateWindow(
            this.initialWidth, this.initialHeight, this.title, MemoryUtil.NULL, MemoryUtil.NULL
        )
        if (this.glfw == MemoryUtil.NULL) throw IllegalStateException("Failed to create window!")

        GLFW.glfwMakeContextCurrent(this.glfw)
        GLFW.glfwSwapInterval(1)
        GL.createCapabilities()

        GLFW.glfwShowWindow(this.glfw)
    }

    companion object {
        val logger = LogManager.getLogger(Window::class.java)!!

        fun randomTitleSuffix(): String {
            val url = this::class.java.getResource("/assets/title-suffixes.txt")!!
            var suffix: String
            do suffix = url.readText().lines().random()
            while (suffix.isBlank())
            return suffix
        }
    }

    fun update() {
        GLFW.glfwPollEvents()

        GLFW.glfwSetWindowTitle(this.glfw, this.title)
        if (this.dTime >= 0 && this.scene != null) {
            GL11.glClearColor(
                this.scene!!.background.red,
                this.scene!!.background.green,
                this.scene!!.background.blue,
                this.scene!!.background.alpha
            )
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)

            this.scene!!.update(this.dTime)

        } else if (this.scene == null) GL11.glClearColor(1f, 1f, 1f, 1f)

        GLFW.glfwSwapBuffers(this.glfw)

        if (this.closeRequested()) Core.close()

        this.endTime = Utils.getTime()
        this.dTime = this.endTime - this.beginTime
        this.beginTime = this.endTime
    }

    fun closeRequested() = GLFW.glfwWindowShouldClose(this.glfw)

    fun close() {
        GLFW.glfwSetWindowShouldClose(this.glfw, true)
    }

    fun destroy() {
        GLFW.glfwDestroyWindow(this.glfw)
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)
    }
}
