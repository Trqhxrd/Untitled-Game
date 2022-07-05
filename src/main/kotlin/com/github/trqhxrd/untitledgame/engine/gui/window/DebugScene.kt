package com.github.trqhxrd.untitledgame.engine.gui.window

import com.github.trqhxrd.untitledgame.engine.gui.callback.Action
import com.github.trqhxrd.untitledgame.engine.gui.callback.keyboard.KeyboardListener
import com.github.trqhxrd.untitledgame.engine.gui.callback.mouse.MouseButton
import com.github.trqhxrd.untitledgame.engine.gui.callback.mouse.MouseClickListener
import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import org.lwjgl.glfw.GLFW
import java.awt.Point

class DebugScene(window: Window) : Scene("Debug Scene!", background = Color.BLACK) {

    override fun init(window: Window) {
        super.init(window)
        this.loadVertex(this::class.java.getResource("/assets/shaders/vertex.glsl")!!.file)
        this.loadFragment(this::class.java.getResource("/assets/shaders/fragment.glsl")!!.file)

        this.validate()

        this.keyHandler.listeners.add(object : KeyboardListener {
            override fun interact(window: Window, key: Int, action: Action) {
                if (key == GLFW.GLFW_KEY_SPACE) {
                    this@DebugScene.background = if (action == Action.PRESS) Color.CYAN else Color.BLACK
                    this@DebugScene.window!!.title = "Test"
                }
            }
        })

        this.mouseHandler.clickListeners.add(object : MouseClickListener {
            override fun click(window: Window, pos: Point, button: MouseButton, action: Action) {
                if (button == MouseButton.LEFT) this@DebugScene.background =
                    if (action == Action.PRESS) Color.MAGENTA else Color.BLACK
            }
        })
    }

    override fun render() {
    }
}
