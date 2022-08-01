package com.github.trqhxrd.untitledgame.engine.gui.util

import org.lwjgl.glfw.GLFW

object Time {
    fun now() = GLFW.glfwGetTime()
}