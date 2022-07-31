package com.github.trqhxrd.untitledgame.engine.gui.rendering.texture

import de.matthiasmann.twl.utils.PNGDecoder
import org.apache.logging.log4j.LogManager
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer

class Texture(val path: String) {
    val logger = LogManager.getLogger()!!
    val decoder = PNGDecoder(FileInputStream(File(path)))
    val width = this.decoder.width
    val height = this.decoder.height
    lateinit var buffer: ByteBuffer
        private set
    var id = -1
        private set
        get() {
            if (field == -1) throw NullPointerException("Texture id of ${this.path} requested before texture was loaded.")
            else return field
        }

    companion object {
        const val BYTES_PER_PIXEL = 4
    }

    fun load() {
        this.logger.debug("Loading texture from ${this.path}.")

        this.buffer = BufferUtils.createByteBuffer(this.decoder.width * this.decoder.height * BYTES_PER_PIXEL)
        this.decoder.decode(this.buffer, this.decoder.width * BYTES_PER_PIXEL, PNGDecoder.Format.RGBA)
        this.buffer.flip()

        this.logger.debug("Loaded texture from ${this.path}.")
    }

    fun upload() {
        this.logger.debug("Uploading texture from ${this.path}")

        this.id = GL30.glGenTextures()
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, this.id)
        GL30.glPixelStorei(GL30.GL_UNPACK_ALIGNMENT, 1)
        GL30.glTexImage2D(
            GL30.GL_TEXTURE_2D, 0, GL30.GL_RGBA, this.width, this.height,
            0, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, this.buffer
        )

        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST)
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST)
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_REPEAT)
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_REPEAT)

        GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D)

        this.logger.debug("Uploaded texture from ${this.path}")
    }

    fun bind() = GL30.glBindTexture(GL30.GL_TEXTURE_2D, this.id)

    fun cleanup() = GL30.glDeleteTextures(this.id)
}
