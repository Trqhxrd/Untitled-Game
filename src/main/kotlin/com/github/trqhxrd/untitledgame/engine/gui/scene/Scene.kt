package com.github.trqhxrd.untitledgame.engine.gui.scene

import com.github.trqhxrd.untitledgame.engine.gui.Color
import com.github.trqhxrd.untitledgame.engine.gui.Window
import com.github.trqhxrd.untitledgame.engine.gui.listener.InputService
import com.github.trqhxrd.untitledgame.engine.gui.listener.KeyHandler
import com.github.trqhxrd.untitledgame.engine.gui.listener.MouseHandler
import com.github.trqhxrd.untitledgame.engine.gui.renderer.Camera
import com.github.trqhxrd.untitledgame.engine.gui.renderer.shader.ShaderSet
import org.joml.Vector2f
import org.lwjgl.glfw.GLFW

abstract class Scene(
    val window: Window,
    val shader: ShaderSet = ShaderSet(),
    val camera: Camera = Camera(),
    override var mouseHandler: MouseHandler = MouseHandler(window, false),
    override var keyHandler: KeyHandler = KeyHandler(window, false),
    var background: Color = Color.BLACK
) : InputService {

    abstract fun update(dTime: Float)

    fun enable() {
        GLFW.glfwSetCursorPosCallback(this.window.glfw, this.mouseHandler::mousePosCallback)
        GLFW.glfwSetMouseButtonCallback(this.window.glfw, this.mouseHandler::mouseButtonCallback)
        GLFW.glfwSetScrollCallback(this.window.glfw, this.mouseHandler::mouseScrollCallback)
        GLFW.glfwSetKeyCallback(this.window.glfw, this.keyHandler::keyCallback)

        this.mouseHandler.isEnabled = true
        this.keyHandler.isEnabled = true

        if (!this.shader.validate()) throw NullPointerException("The shader in invalid!")
    }

    fun disable() {
        this.mouseHandler.isEnabled = false
        this.keyHandler.isEnabled = false
    }

    fun addVertexShader(path: String) = this.shader.addVertex(path)

    fun addFragmentShader(path: String) = this.shader.addFragment(path)
}
