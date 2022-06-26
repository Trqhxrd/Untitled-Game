package com.github.trqhxrd.untitledgame.engine.gui.listener

import org.lwjgl.glfw.GLFW

object KeyHandler {
    private val keyPressed = buildList { for (i in 0 until 350) this.add(false) }.toBooleanArray()

    @JvmStatic
    fun keyCallback(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        this.keyPressed[key] = action == GLFW.GLFW_PRESS
    }

    fun isPressed(key: Int) =  this.keyPressed[key]
}
