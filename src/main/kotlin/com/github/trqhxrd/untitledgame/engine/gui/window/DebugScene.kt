package com.github.trqhxrd.untitledgame.engine.gui.window

import com.github.trqhxrd.untitledgame.engine.gui.callback.Action
import com.github.trqhxrd.untitledgame.engine.gui.callback.keyboard.KeyboardListener
import com.github.trqhxrd.untitledgame.engine.gui.callback.mouse.MouseButton
import com.github.trqhxrd.untitledgame.engine.gui.callback.mouse.MouseClickListener
import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryUtil
import java.awt.Point

class DebugScene : Scene("Debug Scene!", background = Color.BLACK) {

    private val vertices = floatArrayOf(
        0f, .5f, 0f,
        -.5f, -.5f, 0f,
        .5f, -.5f, 0f
    )

    private var vao = -1
    private var vbo = -1

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

        val buf = MemoryUtil.memAllocFloat(this.vertices.size).put(this.vertices).flip()
        this.vao = GL30.glGenVertexArrays()
        GL30.glBindVertexArray(this.vao)

        this.vbo = GL30.glGenBuffers()
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.vbo)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buf, GL30.GL_STATIC_DRAW)

        MemoryUtil.memFree(buf)

        GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0)
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0)
        GL30.glBindVertexArray(0)
    }

    override fun render() {
        GL30.glBindVertexArray(this.vao)
        GL30.glEnableVertexAttribArray(0)
        GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, 3)
        GL30.glDisableVertexAttribArray(0)
        GL30.glBindVertexArray(0)
    }
}
