package com.github.trqhxrd.untitledgame.engine.gui.rendering.texture

import org.apache.logging.log4j.kotlin.Logging
import org.lwjgl.opengl.GL30
import java.awt.Point
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class TextureAtlas(
    val width: Int = GL30.glGetInteger(GL30.GL_MAX_TEXTURE_SIZE),
    val height: Int = GL30.glGetInteger(GL30.GL_MAX_TEXTURE_SIZE),
    val tileWidth: Int,
    val tileHeight: Int
) : Logging {
    val tileDimensions: Pair<Float, Float>
    val mapping = mutableMapOf<String, Point>()
    val image: BufferedImage
    val tiles: Array<Array<Boolean>>
    var isDone = false
        private set
    lateinit var file: File
        private set

    init {
        this.logger.debug(
            "Initializing new TextureAtlas with width=${this.width}, height=${this.height}, " +
                    "tileWidth=${this.tileWidth}, tileHeight=${this.tileHeight}. " +
                    "${(this.width / this.tileWidth) * (this.height / this.tileHeight)} " +
                    "tiles will be available on this atlas."
        )

        this.tileDimensions = this.tileWidth.toFloat() / this.width to this.tileHeight.toFloat() / this.height
        this.image = BufferedImage(
            this.width,
            this.height,
            BufferedImage.TYPE_INT_ARGB
        )
        this.tiles = Array(this.width / this.tileWidth) { Array(this.height / this.tileHeight) { false } }
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
        val x = tile.x * this.tileWidth
        val y = tile.y * this.tileHeight
        this.image.graphics.drawImage(image, x, y, null)
        this.logger.debug("Added image $name to atlas.")
    }

    fun done() {
        val file = File.createTempFile("atlas_", ".png")
        ImageIO.write(this.image, "PNG", file)
        this.file = file
        this.isDone = true
        this.logger.debug("Finalized atlas. Path is \"${this.file.absolutePath}\"")
    }

    fun upload(): Texture {
        this.logger.debug("Uploading atlas to GPU.")
        if (!this.isDone) this.done()
        val texture = Texture(this.file.absolutePath)
        texture.load()
        texture.upload()
        this.logger.debug("Uploaded atlas to GPU.")
        return texture
    }

    fun normalize(point: Point) = point.x.toFloat() / this.width to point.y.toFloat() / this.height

    fun get(name: String) = this.normalize(this.mapping[name]!!)

    private fun findTile(): Point {
        val index = this.tiles.flatten().indexOfFirst { !it }
        return Point(index / (this.width / this.tileWidth), index % (this.width / this.tileWidth))
    }
}