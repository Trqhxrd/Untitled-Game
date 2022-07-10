package com.github.trqhxrd.untitledgame.engine.gui.rendering

import com.github.trqhxrd.untitledgame.engine.gui.window.DebugScene
import com.github.trqhxrd.untitledgame.engine.gui.window.Scene
import com.github.trqhxrd.untitledgame.engine.objects.components.SpriteRenderer
import org.apache.logging.log4j.LogManager
import org.lwjgl.opengl.GL30
import kotlin.math.abs

class RenderBatch(val index: Int, val scene: Scene, val maxBatchSize: Int = 1024) {
    /*
     *  Vertex
     * --------
     * Pos                      Color                           Texture
     * float, float, float,     float, float, float, float,     float, float
     */
    companion object {
        private const val POSITION_SIZE = 3
        private const val POSITION_OFFSET = 0
        private const val COLOR_SIZE = 4
        private const val COLOR_OFFSET = POSITION_OFFSET + POSITION_SIZE * Float.SIZE_BYTES
        private const val TEXTURE_SIZE = 2
        private const val TEXTURE_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.SIZE_BYTES
        private const val VERTEX_SIZE = POSITION_SIZE + COLOR_SIZE + TEXTURE_SIZE
        private const val VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.SIZE_BYTES
    }

    private val logger = LogManager.getLogger()!!
    private val vertices = FloatArray(this.maxBatchSize * VERTEX_SIZE * 4) // 4 Vertices per sprite.
    private val sprites = arrayOfNulls<SpriteRenderer>(this.maxBatchSize)
    var numSprites = 0
        private set
    var hasRoom = true
        private set
    private var vao = -1
    private var vbo = -1

    init {
        this.logger.debug("Initializing new RenderBatch with max size of ${this.maxBatchSize} with ID #${this.index}!")
        this.scene.shader.validate()

        // Generate and bind VAO
        this.vao = GL30.glGenVertexArrays()
        GL30.glBindVertexArray(this.vao)

        // Allocate space for vertices
        this.vbo = GL30.glGenBuffers()
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.vbo)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, this.vertices.size * Float.SIZE_BYTES.toLong(), GL30.GL_DYNAMIC_DRAW)

        // Create and upload indices buffer

        val ebo = GL30.glGenBuffers()
        val indices = this.generateIndices()
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, ebo)
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indices, GL30.GL_STATIC_DRAW)

        // Enable the buffer attribute pointers
        GL30.glVertexAttribPointer(0, POSITION_SIZE, GL30.GL_FLOAT, false, VERTEX_SIZE_BYTES, POSITION_OFFSET.toLong())
        GL30.glEnableVertexAttribArray(0)
        GL30.glVertexAttribPointer(1, COLOR_SIZE, GL30.GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET.toLong())
        GL30.glEnableVertexAttribArray(1)
        GL30.glVertexAttribPointer(2, TEXTURE_SIZE, GL30.GL_FLOAT, false, VERTEX_SIZE_BYTES, TEXTURE_OFFSET.toLong())
        GL30.glEnableVertexAttribArray(2)
    }

    fun render() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.vbo)
        GL30.glBufferSubData(GL30.GL_ARRAY_BUFFER, 0, this.vertices)

        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, DebugScene.texture.id)

        this.scene.uploadCameraDataToGPU()

        GL30.glBindVertexArray(this.vao)

        // Two triangles per square. Three indices per triangle.
        GL30.glDrawElements(GL30.GL_TRIANGLES, this.numSprites * 6, GL30.GL_UNSIGNED_INT, 0)

        GL30.glBindVertexArray(0)
    }

    fun addSprite(sprite: SpriteRenderer): Boolean {
        if (!this.hasRoom) return false
        this.sprites[this.numSprites] = sprite
        this.loadVertexProperties(this.numSprites)

        this.logger.debug("Added new SpriteRenderer with index ${this.numSprites} to RenderBatch #${this.index}.")

        this.numSprites++

        if (this.numSprites >= this.maxBatchSize) this.hasRoom = false
        return true
    }

    private fun generateIndices(): IntArray {
        // Two triangles per square. Three indices per triangle.
        val elements = IntArray(this.maxBatchSize * 6)
        for (i in 0 until maxBatchSize) {
            val offsetIndex = 6 * i
            val offset = 4 * i

            // Element order: 3 2 0 0 2 1
            // Triangle 1
            elements[offsetIndex] = offset + 3
            elements[offsetIndex + 1] = offset + 2
            elements[offsetIndex + 2] = offset + 0

            // Triangle 2
            elements[offsetIndex + 3] = offset + 0
            elements[offsetIndex + 4] = offset + 2
            elements[offsetIndex + 5] = offset + 1
        }
        return elements
    }

    private fun loadVertexProperties(index: Int) {
        val sprite = this.sprites[index]!!
        // Find offset within array (4 vertices per sprite)
        var offset = index * 4 * VERTEX_SIZE

        var xAdd = 1f
        var yAdd = 1f
        for (i in 0..3) {
            when (i) {
                1 -> yAdd = 0f
                2 -> xAdd = 0f
                3 -> yAdd = 1f
                else -> {}
            }

            var add = 0
            // Position
            this.vertices[offset + add++] = sprite.obj!!.boundings.x + (xAdd * sprite.obj!!.boundings.width)
            this.vertices[offset + add++] = sprite.obj!!.boundings.y + (yAdd * sprite.obj!!.boundings.height)
            this.vertices[offset + add++] = sprite.obj!!.boundings.z

            // Color
            this.vertices[offset + add++] = sprite.color.red
            this.vertices[offset + add++] = sprite.color.green
            this.vertices[offset + add++] = sprite.color.blue
            this.vertices[offset + add++] = sprite.color.alpha

            // Texture
            this.vertices[offset + add++] = xAdd
            this.vertices[offset + add++] = abs(yAdd - 1f)

            offset += VERTEX_SIZE
        }
    }
}
