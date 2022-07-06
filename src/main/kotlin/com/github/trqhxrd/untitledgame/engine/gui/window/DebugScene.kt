package com.github.trqhxrd.untitledgame.engine.gui.window

import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import com.github.trqhxrd.untitledgame.engine.gui.util.Rectangle
import com.github.trqhxrd.untitledgame.engine.objects.GameObject
import com.github.trqhxrd.untitledgame.engine.objects.components.SpriteRenderer

class DebugScene : Scene("Debug Scene!", background = Color.BLACK) {

    override fun init(window: Window) {
        super.init(window)
        this.loadVertex(this::class.java.getResource("/assets/shaders/vertex.glsl")!!.file)
        this.loadFragment(this::class.java.getResource("/assets/shaders/fragment.glsl")!!.file)

        this.validate()

        val obj = GameObject("Object", Rectangle(-.5f, -.5f, 1f, 1f))
        obj.add(SpriteRenderer(Color.CYAN))
        this.addObject(obj)
    }
}
