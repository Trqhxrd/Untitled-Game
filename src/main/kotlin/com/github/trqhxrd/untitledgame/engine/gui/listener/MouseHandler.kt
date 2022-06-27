package com.github.trqhxrd.untitledgame.engine.gui.listener

import com.github.trqhxrd.untitledgame.engine.gui.Window
import com.github.trqhxrd.untitledgame.engine.gui.callback.Action
import com.github.trqhxrd.untitledgame.engine.gui.callback.mouse.MouseButton
import com.github.trqhxrd.untitledgame.engine.gui.callback.mouse.MouseClickListener
import com.github.trqhxrd.untitledgame.engine.gui.callback.mouse.MouseMoveListener
import com.github.trqhxrd.untitledgame.engine.gui.callback.mouse.MouseScrollListener
import org.lwjgl.glfw.GLFW
import java.awt.Point

open class MouseHandler(val window: Window, override var isEnabled: Boolean = false) : InputHandler {
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

    val clickListeners = mutableListOf<MouseClickListener>()
    val moveListeners = mutableListOf<MouseMoveListener>()
    val scrollListeners = mutableListOf<MouseScrollListener>()

    fun mousePosCallback(window: Long, xPos: Double, yPos: Double) {
        this.lastX = this.xPos
        this.lastY = this.yPos
        this.xPos = xPos
        this.yPos = yPos
        this.isDragging = this.mouseButtonPressed.any { it }

        if (!this.isEnabled) return

        this.moveListeners.forEach {
            it.move(
                this.window,
                Point(this.lastX.toInt(), this.lastY.toInt()),
                Point(this.xPos.toInt(), this.yPos.toInt()),
                Point(this.deltaX().toInt(), this.deltaY().toInt())
            )
        }
    }

    fun mouseButtonCallback(window: Long, button: Int, action: Int, mods: Int) {
        if (button < this.mouseButtonPressed.size) {
            this.mouseButtonPressed[button] = action == GLFW.GLFW_PRESS
            if (action == GLFW.GLFW_RELEASE) this.isDragging = false
        }

        if (!this.isEnabled) return

        this.clickListeners.forEach {
            it.click(
                this.window,
                Point(this.xPos.toInt(), this.yPos.toInt()),
                MouseButton.fromGLFWId(button),
                Action.getByGLFWId(action)
            )
        }
    }

    fun mouseScrollCallback(window: Long, xOffset: Double, yOffset: Double) {
        this.scrollX = xOffset
        this.scrollY = yOffset

        if (!this.isEnabled) return

        this.scrollListeners.forEach {
            it.scroll(this.window, Point(this.scrollX.toInt(), this.scrollY.toInt()))
        }
    }

    fun deltaX() = (this.lastX - this.xPos)
    fun deltaY() = this.lastY - this.yPos
    fun mouseButtonDown(button: Int) =
        if (button < this.mouseButtonPressed.size) this.mouseButtonPressed[button]
        else false
}
