package com.github.trqhxrd.untitledgame.engine.gui.listener

import com.github.trqhxrd.untitledgame.engine.gui.callback.Action
import com.github.trqhxrd.untitledgame.engine.gui.callback.keyboard.KeyboardListener
import com.github.trqhxrd.untitledgame.engine.gui.window.Scene
import org.lwjgl.glfw.GLFW

open class KeyHandler(val scene: Scene) {

    private val keyPressed = buildList { for (i in 0 until 350) this.add(false) }.toBooleanArray()
    val listeners = mutableListOf<KeyboardListener>()

    fun keyCallback(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        val b = action != GLFW.GLFW_RELEASE
        this.keyPressed[key] = b

        this.listeners.forEach { it.interact(this.scene.window!!, key, Action.getByGLFWId(action)) }
    }

    fun isPressed(key: Int) = keyPressed[key]

    fun enable() = GLFW.glfwSetKeyCallback(this.scene.window!!.glfw, this::keyCallback)

    fun disable() = GLFW.glfwSetKeyCallback(this.scene.window!!.glfw, null)
}
