package com.github.trqhxrd.untitledgame.engine.gui.callback

import org.lwjgl.glfw.GLFW

enum class Action(val glfwId: Int) {
    PRESS(GLFW.GLFW_PRESS), RELEASE(GLFW.GLFW_RELEASE);

    companion object {
        fun getByGLFWId(glfwId: Int) = if (glfwId == RELEASE.glfwId) RELEASE else PRESS
    }
}