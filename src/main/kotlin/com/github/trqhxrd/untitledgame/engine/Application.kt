package com.github.trqhxrd.untitledgame.engine

import org.lwjgl.glfw.GLFW

class Application(val name: String, var width: Int, var height: Int, var window: Long) {

    fun init(){
        if (!GLFW.glfwInit()){

        }
    }
}