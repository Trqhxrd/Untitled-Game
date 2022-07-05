package com.github.trqhxrd.untitledgame.engine.gui.window

import com.github.trqhxrd.untitledgame.engine.gui.listener.KeyHandler
import com.github.trqhxrd.untitledgame.engine.gui.listener.MouseHandler
import com.github.trqhxrd.untitledgame.engine.gui.rendering.shader.ShaderProgram
import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import java.io.File

@Suppress("LeakingThis")
abstract class Scene(
    val name: String,
    val shader: ShaderProgram = ShaderProgram(name),
    var background: Color = Color.WHITE
) {

    var window: Window? = null
        private set
    val mouseHandler = MouseHandler(this)
    val keyHandler = KeyHandler(this)

    open fun preInit() {}

    open fun init(window: Window) {
        this.window = window
        this.mouseHandler.enable()
        this.keyHandler.enable()
    }

    open fun postInit() = this.shader.link()

    open fun preRender() = this.shader.use()

    open fun postRender() = this.shader.detach()

    abstract fun render()

    open fun stop() {
        this.mouseHandler.disable()
        this.keyHandler.disable()
    }

    fun loadVertex(file: File) = this.shader.loadVertex(file)

    fun loadVertex(file: String) = this.loadVertex(File(file))

    fun loadFragment(file: File) = this.shader.loadFragment(file)

    fun loadFragment(file: String) = this.shader.loadFragment(File(file))

    fun validate() = this.shader.validate()
}
