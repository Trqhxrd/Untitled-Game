package com.github.trqhxrd.untitledgame.engine.gui.window

import com.github.trqhxrd.untitledgame.engine.gui.rendering.texture.Texture
import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import com.github.trqhxrd.untitledgame.engine.gui.util.Time
import com.github.trqhxrd.untitledgame.engine.objects.GameObject
import com.github.trqhxrd.untitledgame.engine.objects.components.SpriteRenderer

class DebugScene : Scene("Debug Scene!", background = Color.WHITE) {

    private var last = 0.0

    companion object {
        lateinit var texture: Texture
    }

    override fun init(window: Window) {
        super.init(window)
        this.loadVertex(this::class.java.getResource("/assets/shaders/vertex.glsl")!!.file)
        this.loadFragment(this::class.java.getResource("/assets/shaders/fragment.glsl")!!.file)

        this.validate()

        texture = Texture("/assets/textures/no_texture.png")
        texture.load()
        texture.upload()

        val obj = GameObject("Object", 100, 100, 256, 256)
        obj.add(SpriteRenderer(Color.WHITE))
        this.addObject(obj)
    }

    override fun preRender() {
        super.preRender()
        val dTime = last / Time.now()
        this.last = Time.now()
        this.shader.setUniform("textureSampler", 0)
        this.camera.x -= dTime.toFloat() / 5f
    }
}
