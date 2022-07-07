package com.github.trqhxrd.untitledgame.engine.gui.window

import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import com.github.trqhxrd.untitledgame.engine.objects.GameObject
import com.github.trqhxrd.untitledgame.engine.objects.components.SpriteRenderer
import kotlin.math.sin

class DebugScene : Scene("Debug Scene!", background = Color.BLACK) {

    override fun init(window: Window) {
        super.init(window)
        this.loadVertex(this::class.java.getResource("/assets/shaders/vertex.glsl")!!.file)
        this.loadFragment(this::class.java.getResource("/assets/shaders/fragment.glsl")!!.file)

        this.validate()

        for (x in 0..99) {
            for (y in 0..99) {
                val obj = GameObject("Object", x * 8 + 8, y * 8 + 8, 4, 4)
                obj.add(SpriteRenderer(Color(x / 100f, y / 100f, 0f, 1f)))
                this.addObject(obj)
            }
        }
    }
}
