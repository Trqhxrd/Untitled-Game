package com.github.trqhxrd.untitledgame.engine.gui.scene

import com.github.trqhxrd.untitledgame.engine.gui.Color
import com.github.trqhxrd.untitledgame.engine.gui.Utils
import com.github.trqhxrd.untitledgame.engine.gui.Window
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30


class DebugScene(window: Window) : Scene(window, background = Color.WHITE) {
    private val vertexArray = floatArrayOf(
        100.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
        0.5f, 100.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
        100.5f, 100.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
        0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f
    )

    private val elementArray = intArrayOf(
        2, 1, 0,
        0, 1, 3
    )

    private var vaoID = 0
    private var vboID: Int = 0
    private var eboID: Int = 0

    init {
        this.shader.addVertex("/assets/shaders/vertex.glsl")
        this.shader.addFragment("/assets/shaders/fragment.glsl")

        vaoID = GL30.glGenVertexArrays()
        GL30.glBindVertexArray(vaoID)

        val vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.size)
        vertexBuffer.put(vertexArray).flip()

        vboID = GL30.glGenBuffers()
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexBuffer, GL30.GL_STATIC_DRAW)

        val elementBuffer = BufferUtils.createIntBuffer(elementArray.size)
        elementBuffer.put(elementArray).flip()
        eboID = GL30.glGenBuffers()
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, eboID)
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL30.GL_STATIC_DRAW)

        val positionsSize = 3
        val colorSize = 4
        val floatSizeBytes = 4
        val vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes
        GL30.glVertexAttribPointer(0, positionsSize, GL30.GL_FLOAT, false, vertexSizeBytes, 0)
        GL30.glEnableVertexAttribArray(0)
        GL30.glVertexAttribPointer(
            1,
            colorSize,
            GL30.GL_FLOAT,
            false,
            vertexSizeBytes,
            (positionsSize * floatSizeBytes).toLong()
        )
        GL30.glEnableVertexAttribArray(1)
    }

    override fun update(dTime: Float) {
        this.camera.position.x -= dTime * 50f
        this.camera.position.y -= dTime * 20f

        this.shader.use()
        this.shader.uploadMat4f("uProjection", this.camera.projection)
        this.shader.uploadMat4f("uView", this.camera.view)
        this.shader.uploadFloat("uTime", Utils.getTime())

        GL30.glBindVertexArray(vaoID)

        GL30.glEnableVertexAttribArray(0)
        GL30.glEnableVertexAttribArray(1)
        GL30.glDrawElements(GL30.GL_TRIANGLES, elementArray.size, GL30.GL_UNSIGNED_INT, 0)

        GL30.glDisableVertexAttribArray(0)
        GL30.glDisableVertexAttribArray(1)
        GL30.glBindVertexArray(0)

        this.shader.detach()
    }
}
