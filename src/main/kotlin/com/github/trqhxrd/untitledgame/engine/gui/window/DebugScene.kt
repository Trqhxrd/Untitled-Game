package com.github.trqhxrd.untitledgame.engine.gui.window

import com.github.trqhxrd.untitledgame.engine.gui.rendering.texture.Texture
import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import com.github.trqhxrd.untitledgame.engine.gui.util.Time
import com.github.trqhxrd.untitledgame.engine.objects.GameObject
import com.github.trqhxrd.untitledgame.engine.objects.components.SpriteRenderer
import org.lwjgl.opengl.GL30

class DebugScene : Scene("Debug Scene!", background = Color.BLACK) {

    private var last = 0.0
    private lateinit var texture: Texture

    override fun init(window: Window) {
        super.init(window)
        this.loadVertex(this::class.java.getResource("/assets/shaders/vertex.glsl")!!.file)
        this.loadFragment(this::class.java.getResource("/assets/shaders/fragment.glsl")!!.file)

        this.validate()

        this.texture = Texture("/assets/textures/no_texture.png")
        this.texture.load()
        this.texture.upload()

        for (x in 0..99) {
            for (y in 0..99) {
                val obj = GameObject("Object_${x}_${y}", x * 5 + 8, y * 5 + 8, 4, 4)
                obj.add(SpriteRenderer(Color(x / 100f, y / 100f, 0f, 1f)))
                this.addObject(obj)
            }
        }
    }

    override fun preRender() {
        val dTime = last / Time.now()
        this.last = Time.now()
        this.camera.x -= dTime.toFloat() / 5
        this.shader.setUniform("textureSampler", 0)
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE0, this.texture.id)
    }
}
