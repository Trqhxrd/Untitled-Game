package com.github.trqhxrd.untitledgame.engine.gui.listener

import com.github.trqhxrd.untitledgame.engine.gui.Window
import com.github.trqhxrd.untitledgame.engine.gui.callback.Action
import com.github.trqhxrd.untitledgame.engine.gui.callback.keyboard.KeyboardListener
import org.lwjgl.glfw.GLFW

open class KeyHandler(val window: Window) {

    private val keyPressed = buildList { for (i in 0 until 350) this.add(false) }.toBooleanArray()
    val listeners = mutableListOf<KeyboardListener>()

    fun keyCallback(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        val b = action != GLFW.GLFW_RELEASE
        this.keyPressed[key] = b

        this.listeners.forEach { it.interact(this.window, key, Action.getByGLFWId(action)) }
    }

    fun isPressed(key: Int) = keyPressed[key]
}
