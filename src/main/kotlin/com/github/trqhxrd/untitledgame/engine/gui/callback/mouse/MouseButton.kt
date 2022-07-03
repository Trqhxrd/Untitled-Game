package com.github.trqhxrd.untitledgame.engine.gui.callback.mouse

import org.lwjgl.glfw.GLFW

enum class MouseButton(val glfwId: Int) {
    LEFT(GLFW.GLFW_MOUSE_BUTTON_LEFT),
    RIGHT(GLFW.GLFW_MOUSE_BUTTON_RIGHT),
    MIDDLE(GLFW.GLFW_MOUSE_BUTTON_MIDDLE),
    UNDEFINED(-1);

    companion object {
        fun fromGLFWId(button: Int) = values().firstOrNull { it.glfwId == button } ?: UNDEFINED
    }
}