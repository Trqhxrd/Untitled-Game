package com.github.trqhxrd.untitledgame.engine.gui.rendering.texture

import de.matthiasmann.twl.utils.PNGDecoder
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.kotlin.Logging
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30
import java.awt.Point
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import javax.imageio.ImageIO

class TextureAtlas(
    val widthInPixel: Int = GL30.glGetInteger(GL30.GL_MAX_TEXTURE_SIZE),
    val heightInPixel: Int = GL30.glGetInteger(GL30.GL_MAX_TEXTURE_SIZE),
    val tileWidthInPixel: Int,
    val tileHeightInPixel: Int
) : Logging {
    val tileDimensionsNormalized: Pair<Float, Float>
    val mapping = mutableMapOf<String, Point>()
    val image: BufferedImage
    val tiles: Array<Array<Boolean>>
    var isDone = false
        private set
    lateinit var file: File
        private set
    private lateinit var texture: TextureUtil

    init {
        this.logger.debug(
            "Initializing new TextureAtlas with width=${this.widthInPixel}, height=${this.heightInPixel}, " +
                    "tileWidth=${this.tileWidthInPixel}, tileHeight=${this.tileHeightInPixel}. " +
                    "${(this.widthInPixel / this.tileWidthInPixel) * (this.heightInPixel / this.tileHeightInPixel)} " +
                    "tiles will be available on this atlas."
        )

        this.tileDimensionsNormalized =
            this.tileWidthInPixel.toFloat() / this.widthInPixel to this.tileHeightInPixel.toFloat() / this.heightInPixel
        this.image = BufferedImage(
            this.widthInPixel,
            this.heightInPixel,
            BufferedImage.TYPE_INT_ARGB
        )
        this.tiles =
            Array(this.widthInPixel / this.tileWidthInPixel) { Array(this.heightInPixel / this.tileHeightInPixel) { false } }
    }

    fun tilesLeft() = this.tiles.flatten().filter { !it }.size

    fun add(name: String, image: BufferedImage) {
        if (this.isDone) {
            this.logger.error("Could not add textures to atlas. This atlas was already finalized.")
            return
        } else if (this.tilesLeft() - 1 <= 0) {
            this.logger.error("Not enough space left in atlas. Failed to add textures to atlas.")
            return
        }

        val tile = this.mapping.getOrPut(name) { this.findTile() }
        this.tiles[tile.x][tile.y] = true
        val x = tile.x * this.tileWidthInPixel
        val y = tile.y * this.tileHeightInPixel
        this.image.graphics.drawImage(image, x, y, this.tileWidthInPixel, this.tileHeightInPixel, null)
        this.logger.debug("Added image $name to atlas.")
    }

    fun done() {
        val file = File.createTempFile("atlas_", ".png")
        ImageIO.write(this.image, "PNG", file)
        this.file = file
        this.isDone = true
        this.logger.debug("Finalized atlas. Path is \"${this.file.absolutePath}\"")
    }

    fun upload() {
        this.logger.debug("Uploading atlas to GPU.")
        if (!this.isDone) this.done()
        this.texture = TextureUtil(this.file.absolutePath)
        this.texture.load()
        this.texture.upload()
        this.logger.debug("Uploaded atlas to GPU.")
    }

    fun normalize(point: Point) =
        (point.x.toFloat() * tileWidthInPixel) / widthInPixel to (point.y.toFloat() * tileHeightInPixel) / heightInPixel

    fun get(name: String) = this.normalize(this.mapping[name]!!)

    private fun findTile(): Point {
        val index = this.tiles.flatten().indexOfFirst { !it }
        return Point(
            index / (this.widthInPixel / this.tileWidthInPixel),
            index % (this.widthInPixel / this.tileWidthInPixel)
        )
    }

    fun bind() = this.texture.bind()
}

private class TextureUtil(val path: String) {
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