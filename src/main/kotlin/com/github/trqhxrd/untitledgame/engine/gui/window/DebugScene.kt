package com.github.trqhxrd.untitledgame.engine.gui.window

import com.github.trqhxrd.untitledgame.engine.gui.rendering.texture.TextureAtlas
import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import com.github.trqhxrd.untitledgame.engine.gui.util.Time
import com.github.trqhxrd.untitledgame.engine.objects.GameObject
import com.github.trqhxrd.untitledgame.engine.objects.components.SpriteRenderer
import org.lwjgl.opengl.GL30
import javax.imageio.ImageIO

class DebugScene : Scene("Debug Scene!", background = Color.WHITE) {

    private var last = 0.0

    override fun init(window: Window) {
        super.init(window)
        this.loadVertex(this::class.java.getResource("/assets/shaders/vertex.glsl")!!.file)
        this.loadFragment(this::class.java.getResource("/assets/shaders/fragment.glsl")!!.file)

        this.validate()

        val atlas = TextureAtlas(32, 32, tileWidthInPixel = 8, tileHeightInPixel = 8)
        atlas.add("null", ImageIO.read(this::class.java.getResourceAsStream("/assets/textures/no_texture.png")))
        atlas.add("color", ImageIO.read(this::class.java.getResourceAsStream("/assets/textures/color.png")))
        atlas.add("grass", ImageIO.read(this::class.java.getResourceAsStream("/assets/textures/grass.png")))
        atlas.add("dirt", ImageIO.read(this::class.java.getResourceAsStream("/assets/textures/dirt.png")))
        atlas.done()
        atlas.upload()

        val obj = GameObject("Object", 100, 100, 100, 100)
        obj.add(SpriteRenderer(Color.WHITE, atlas.get("null").also { println(it) }, atlas.tileDimensionsNormalized))
        this.addObject(obj)
        val obj0 = GameObject("Object", 200, 100, 100, 100)
        obj0.add(SpriteRenderer(Color.CYAN, atlas.get("color").also { println(it) }, atlas.tileDimensionsNormalized))
        this.addObject(obj0)
        val obj1 = GameObject("Object1", 300, 100, 100, 100)
        obj1.add(SpriteRenderer(Color.WHITE, atlas.get("dirt").also { println(it) }, atlas.tileDimensionsNormalized))
        this.addObject(obj1)
        val obj2 = GameObject("Object2", 400, 100, 100, 100)
        obj2.add(SpriteRenderer(Color.WHITE, atlas.get("grass").also { println(it) }, atlas.tileDimensionsNormalized))
        this.addObject(obj2)

        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        atlas.bind()
    }

    override fun preRender() {
        super.preRender()
        val dTime = last / Time.now()
        this.last = Time.now()
        this.shader.setUniform("textureSampler", 0)
        this.camera.x -= dTime.toFloat() / 5f
    }
}
