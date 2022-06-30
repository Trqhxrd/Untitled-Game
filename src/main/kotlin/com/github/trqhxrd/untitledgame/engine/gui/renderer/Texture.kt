package com.github.trqhxrd.untitledgame.engine.gui.renderer

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30
import org.lwjgl.stb.STBImage
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import kotlin.io.path.absolutePathString

class Texture(path: String) {

    val path: String
    val id: Int = GL30.glGenTextures()
    val image: ByteBuffer

    init {
        this.path = File(path).toPath().absolutePathString()

        GL30.glBindTexture(GL30.GL_TEXTURE_2D, this.id)

        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_REPEAT)
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_REPEAT)

        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST)
        GL30.glTexParameteri(GL30.G_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST)

        val width = BufferUtils.createIntBuffer(1)
        val height = BufferUtils.createIntBuffer(1)
        val channels = BufferUtils.createIntBuffer(1)
        this.image = STBImage.stbi_load(this.path, width, height, channels, 0)
            ?: throw IOException("Could not load Image! (${this.path})\n${STBImage.stbi_failure_reason()}")

        val channelType =
            if (channels.get(0) == 3) GL30.GL_RGB
            else if (channels.get(0) == 4) GL30.GL_RGBA
            else throw IllegalArgumentException("The image is not formatted correctly! (${this.path})")

        GL30.glTexImage2D(
            GL30.GL_TEXTURE_2D,
            0,
            channelType,
            width.get(0),
            height.get(0),
            0,
            channelType,
            GL30.GL_UNSIGNED_BYTE,
            this.image
        )

        STBImage.stbi_image_free(this.image)
    }

    fun bind() = GL30.glBindTexture(GL30.GL_TEXTURE_2D, this.id)

    fun unbind() = GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0)
}
