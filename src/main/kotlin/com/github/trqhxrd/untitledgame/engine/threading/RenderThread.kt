package com.github.trqhxrd.untitledgame.engine.threading

import com.github.trqhxrd.untitledgame.engine.Core
import org.lwjgl.glfw.GLFW

class RenderThread : AbstractThread("render", 10) {
    override fun loop() {
        Core.window.loop()
        if (GLFW.glfwWindowShouldClose(Core.window.glfw)) Core.close()
    }
}
