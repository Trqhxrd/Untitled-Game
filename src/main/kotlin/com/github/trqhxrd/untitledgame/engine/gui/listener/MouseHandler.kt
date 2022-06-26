package com.github.trqhxrd.untitledgame.engine.gui.listener

import org.lwjgl.glfw.GLFW

object MouseHandler {

    var scrollX: Double = .0
        private set
    var scrollY: Double = .0
        private set
    var xPos: Double = .0
        private set
    var yPos: Double = .0
        private set
    var lastX: Double = .0
        private set
    var lastY: Double = .0
        private set
    var isDragging: Boolean = false
        private set
    private var mouseButtonPressed: BooleanArray = booleanArrayOf(false, false, false)

    @JvmStatic
    fun mousePosCallback(window: Long, xPos: Double, yPos: Double) {
        this.lastX = this.xPos
        this.lastY = this.yPos
        this.xPos = xPos
        this.yPos = yPos
        this.isDragging = this.mouseButtonPressed.any { it }
    }

    @JvmStatic
    fun mouseButtonCallback(window: Long, button: Int, action: Int, mods: Int) {
        if (button < this.mouseButtonPressed.size) {
            this.mouseButtonPressed[button] = action == GLFW.GLFW_PRESS
            if (action == GLFW.GLFW_RELEASE) this.isDragging = false
        }
    }

    @JvmStatic
    fun mouseScrollCallback(window: Long, xOffset: Double, yOffset: Double) {
        this.scrollX = xOffset
        this.scrollY = yOffset
    }

    fun endFrame() {
        this.scrollX = .0
        this.scrollY = .0
        this.lastX = this.xPos
        this.lastY = this.yPos
    }

    fun deltaX() = (this.lastX - this.xPos)
    fun deltaY() = this.lastY - this.yPos
    fun mouseButtonDown(button: Int) =
        if (button < this.mouseButtonPressed.size) this.mouseButtonPressed[button]
        else false
}
