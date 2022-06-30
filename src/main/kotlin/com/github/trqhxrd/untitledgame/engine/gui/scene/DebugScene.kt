package com.github.trqhxrd.untitledgame.engine.gui.scene

import com.github.trqhxrd.untitledgame.engine.gui.Color
import com.github.trqhxrd.untitledgame.engine.gui.Utils
import com.github.trqhxrd.untitledgame.engine.gui.Window
import com.github.trqhxrd.untitledgame.engine.gui.renderer.Texture
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30


class DebugScene(window: Window) : Scene(window, background = Color.WHITE) {
    private val vertexArray = floatArrayOf(
        100.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1f, 0f,
        0.5f, 100.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0f, 1f,
        100.5f, 100.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1f, 1f,
        0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0f, 0f
    )

    private val elementArray = intArrayOf(
        2, 1, 0,
        0, 1, 3
    )

    private val texture: Texture

    private var vaoID = 0
    private var vboID: Int = 0
    private var eboID: Int = 0

    init {
        this.shader.addVertex("assets/shaders/vertex.glsl")
        this.shader.addFragment("assets/shaders/fragment.glsl")
        this.texture = Texture("assets/textures/testImage.png")

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
        val uvSize = 2
        val vertexSizeBytes = (positionsSize + colorSize + uvSize) * Float.SIZE_BYTES
        GL30.glVertexAttribPointer(0, positionsSize, GL30.GL_FLOAT, false, vertexSizeBytes, 0)
        GL30.glEnableVertexAttribArray(0)

        GL30.glVertexAttribPointer(
            1,
            colorSize,
            GL30.GL_FLOAT,
            false,
            vertexSizeBytes,
            (positionsSize * Float.SIZE_BYTES).toLong()
        )
        GL30.glEnableVertexAttribArray(1)

        GL30.glVertexAttribPointer(
            2,
            uvSize,
            GL30.GL_FLOAT,
            false,
            vertexSizeBytes,
            ((positionsSize + colorSize) * Float.SIZE_BYTES).toLong()
        )
        GL30.glEnableVertexAttribArray(2)
    }

    override fun update(dTime: Float) {
        this.camera.position.x -= dTime * 50f
        this.camera.position.y -= dTime * 20f

        this.shader.use()

        this.shader.uploadMat4f("uProjection", this.camera.projection)
        this.shader.uploadMat4f("uView", this.camera.view)
        this.shader.uploadFloat("uTime", Utils.getTime())

        this.shader.uploadTexture("TEX_SAMPLER", 0)
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        this.texture.bind()

        GL30.glBindVertexArray(vaoID)

        GL30.glEnableVertexAttribArray(0)
        GL30.glEnableVertexAttribArray(1)
        GL30.glDrawElements(GL30.GL_TRIANGLES, elementArray.size, GL30.GL_UNSIGNED_INT, 0)

        GL30.glDisableVertexAttribArray(0)
        GL30.glDisableVertexAttribArray(1)
        GL30.glBindVertexArray(0)

        this.texture.unbind()
        this.shader.detach()
    }
}
