package com.github.trqhxrd.untitledgame.engine.gui.window

import com.github.trqhxrd.untitledgame.engine.Core
import com.github.trqhxrd.untitledgame.engine.gui.util.Time
import org.apache.logging.log4j.LogManager
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GLUtil
import org.lwjgl.system.MemoryUtil
import kotlin.math.roundToInt

class Window(
    width: Int,
    height: Int,
    title: String
) {
    private val logger = LogManager.getLogger()!!
    var glfw: Long = -1
        private set
    var title: String = title
        set(value) {
            if (field != value) {
                logger.debug("Changed window title from '$field' to '$value'.")
                field = value
                GLFW.glfwSetWindowTitle(this.glfw, value)
            }
        }
    var width: Int = width
        set(value) {
            if (field != value) {
                GLFW.glfwSetWindowSize(this.glfw, value, this.height)
                GL30.glViewport(0, 0, this.width, this.height)
            }
            field = value
        }
    var height: Int = height
        set(value) {
            if (field != value) {
                GLFW.glfwSetWindowSize(this.glfw, this.width, value)
                GL30.glViewport(0, 0, this.width, this.height)
            }
            field = value
        }

    var scene: Scene? = null
        set(value) {
            field?.stop()
            logger.debug("Switching scene from '${field?.name}' to '${value?.name}'.")
            field = value
            value?.preInit()
            value?.init(this)
            value?.postInit()
        }

    private var beginTime = 0.0
    private var endTime = 0.0
    private var dTime = 0.0
    private var fpsUtil = 0.0

    init {
        this.title = title + randomTitleSuffix()

        GLFWErrorCallback.createPrint(System.err).set()

        if (!GLFW.glfwInit()) throw IllegalStateException("GLFW could not be initialized!")

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE)
        if (GLFW_DEBUG) GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE)

        this.glfw = GLFW.glfwCreateWindow(
            this.width, this.height, this.title,
            MemoryUtil.NULL, MemoryUtil.NULL
        )
        if (this.glfw == MemoryUtil.NULL) throw IllegalStateException("Failed to create window!")

        GLFW.glfwMakeContextCurrent(this.glfw)
        GLFW.glfwSwapInterval(1)
        GL.createCapabilities()

        if (GLFW_DEBUG) GLUtil.setupDebugMessageCallback()

        GLFW.glfwSetWindowSizeCallback(this.glfw) { _: Long, newWidth: Int, newHeight: Int ->
            this.width = newWidth
            this.height = newHeight
            logger.debug("Window '${this.title}' resized to width=$newWidth and height=$newHeight.")
        }

        GLFW.glfwSetWindowCloseCallback(this.glfw) { Core.shutdown() }

        GLFW.glfwShowWindow(this.glfw)
    }

    companion object {
        fun randomTitleSuffix(): String {
            val url = this::class.java.getResource("/assets/title-suffixes.txt")!!
            var suffix: String
            do suffix = url.readText().lines().random()
            while (suffix.isBlank())
            return suffix
        }

        const val GLFW_DEBUG = true
        const val DEBUG_FPS_UPDATE_DELAY = 1
    }

    fun update() {
        GLFW.glfwPollEvents()
        if (this.dTime >= 0) {
            if (this.scene != null) GL30.glClearColor(
                this.scene!!.background.red,
                this.scene!!.background.green,
                this.scene!!.background.blue,
                this.scene!!.background.alpha
            ) else GL30.glClearColor(1f, 1f, 1f, 1f)
            GL30.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)
        }

        this.scene?.preRender()
        this.scene?.render()
        this.scene?.postRender()

        GLFW.glfwSwapBuffers(this.glfw)

        this.endTime = Time.now()
        this.dTime = this.endTime - this.beginTime
        this.beginTime = this.endTime
        if (this.fpsUtil == .0 || (this.fpsUtil + DEBUG_FPS_UPDATE_DELAY < this.endTime)) {
            this.fpsUtil = this.endTime
            logger.debug("FPS: ${(1f / this.dTime).roundToInt()}")
        }
    }

    fun close() = GLFW.glfwSetWindowShouldClose(this.glfw, true)

    fun destroy() {
        GLFW.glfwDestroyWindow(this.glfw);

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null)?.free();
    }
}
