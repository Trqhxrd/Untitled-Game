package com.github.trqhxrd.untitledgame.engine.gui.rendering.texture

import de.matthiasmann.twl.utils.PNGDecoder
import org.apache.logging.log4j.LogManager
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

class TextureAtlas(val width: Int = MAX_IMAGE_SIZE, val height: Int = MAX_IMAGE_SIZE) {
    private val logger = LogManager.getLogger()!!
    private val indices = mutableMapOf<String, IntArray>()
    private val blocked = BooleanArray(this.width * this.height)
    var id = -1
        private set
        get() {
            if (field == -1) this.logger.warn("Tried to access texture handle without the texture being generated.")
            return field
        }
    private val image: BufferedImage

    companion object {
        val format = PNGDecoder.Format.RGBA
        var MAX_IMAGE_SIZE = -1
            private set

        fun init() {
            this.MAX_IMAGE_SIZE = GL30.glGetInteger(GL30.GL_MAX_3D_TEXTURE_SIZE)
        }
    }

    init {
        this.image = BufferedImage(MAX_IMAGE_SIZE, MAX_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB)
        this.id = GL30.glGenTextures()
        this.bind()
        GL30.glPixelStorei(GL30.GL_UNPACK_ALIGNMENT, 1)

        GL30.glTexParameteri(GL30.GL_TEXTURE_3D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST)
        GL30.glTexParameteri(GL30.GL_TEXTURE_3D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST)
        GL30.glTexParameteri(GL30.GL_TEXTURE_3D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_CLAMP_TO_EDGE)
        GL30.glTexParameteri(GL30.GL_TEXTURE_3D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_CLAMP_TO_EDGE)

        this.logger.debug("Initialized new texture atlas with size ${this.width}x${this.height} pixels.")
    }

    fun update() {
        this.bind()
        val stream = ByteArrayOutputStream()
        val buf = BufferUtils.createByteBuffer(this.width * this.height * format.numComponents)
        stream.use {
            ImageIO.write(this.image, "PNG", it)
            buf.put(stream.toByteArray().map { b -> (b + 128).toByte() }.toByteArray()).flip()
        }

        this.logger.debug("Updating Texture Atlas.")

        GL30.glTexImage3D(
            GL30.GL_TEXTURE_3D, 0, GL30.GL_RGBA, this.width, this.height, 128,
            0, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, buf
        )

        GL30.glGenerateMipmap(GL30.GL_TEXTURE_3D)
    }

    fun add(path: String): TextureLocation {
        val img = ImageIO.read(this::class.java.getResourceAsStream(path))
        this.logger.debug("Loaded file $path with size ${img.width}x${img.height}.")
        return this.add(path, img)
    }

    fun add(name: String, img: BufferedImage): TextureLocation {
        val free = this.freeLocation(img.width, img.height)
        if (free == -1 to -1) return TextureLocation(FloatArray(4) { -1f }, IntArray(4) { -1 })
        this.block(free.first, free.second, img.width, img.height)
        val combine = BufferedImage(this.width, this.height, BufferedImage.TYPE_4BYTE_ABGR)
        val graphics = combine.graphics
        graphics.drawImage(this.image, 0, 0, null)
        graphics.drawImage(img, free.first, free.second, null)

        val a = intArrayOf(free.first, free.second, free.first + img.width, free.second + img.height)
        this.indices[name] = a

        this.update()

        this.logger.debug("Added texture $name with size ${img.width}x${img.height} to Texture-Atlas.")
        return TextureLocation(FloatArray(4) { 1f / a[it].toFloat() }, a)
    }

    fun uv(name: String): TextureLocation {
        val a = FloatArray(4) { -1f }
        val d = this.indices[name]
        if (d == null) {
            this.logger.error(Exception("Could not find texture with name $name."))
            return TextureLocation(a, IntArray(4) { -1 })
        }
        for (i in d.indices) a[i] = 1f / d[i]
        return TextureLocation(a, d)
    }

    private fun freeLocation(width: Int, height: Int): Pair<Int, Int> {
        var x = 0
        var y = 0
        var blocked: Boolean

        do {
            blocked = false
            OuterLoop@ for (w in 0 until width) {
                for (h in 0 until height) {
                    if (this.blocked[h * this.width + w]) {
                        blocked = true
                        break@OuterLoop
                    }
                }
            }
        } while (blocked)

        return if (blocked) {
            this.logger.error(Exception("Could not find an empty space on the Texture-Atlas."))
            -1 to -1
        } else x to y
    }

    private fun block(x: Int, y: Int, width: Int, height: Int) {
        for (posX in x until x + width)
            for (posY in y until y + height)
                this.blocked[posY * this.height + posX] = true
    }

    fun bind() = GL30.glBindTexture(GL30.GL_TEXTURE_3D, this.id)

    fun unbind() = GL30.glBindTexture(GL30.GL_TEXTURE_3D, 0)

    data class TextureLocation(val floatLoc: FloatArray, val intLoc: IntArray) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as TextureLocation

            if (!floatLoc.contentEquals(other.floatLoc)) return false
            if (!intLoc.contentEquals(other.intLoc)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = floatLoc.contentHashCode()
            result = 31 * result + intLoc.contentHashCode()
            return result
        }
    }
}

