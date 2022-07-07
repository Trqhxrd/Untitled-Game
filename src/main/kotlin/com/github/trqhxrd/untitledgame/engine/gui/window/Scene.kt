package com.github.trqhxrd.untitledgame.engine.gui.window

import com.github.trqhxrd.untitledgame.engine.gui.listener.KeyHandler
import com.github.trqhxrd.untitledgame.engine.gui.listener.MouseHandler
import com.github.trqhxrd.untitledgame.engine.gui.rendering.camera.Camera
import com.github.trqhxrd.untitledgame.engine.gui.rendering.Renderer
import com.github.trqhxrd.untitledgame.engine.gui.rendering.shader.ShaderProgram
import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import com.github.trqhxrd.untitledgame.engine.objects.GameObject
import org.lwjgl.opengl.GL30
import java.io.File

@Suppress("LeakingThis")
abstract class Scene(
    val name: String,
    val shader: ShaderProgram = ShaderProgram(name),
    var background: Color = Color.WHITE
) {

    var window: Window? = null
        private set
    val camera = Camera(0f, 0f)
    val mouseHandler = MouseHandler(this)
    val keyHandler = KeyHandler(this)
    val renderer = Renderer(this)
    private val objs = mutableListOf<GameObject>()

    open fun preInit() {}

    open fun init(window: Window) {
        this.window = window
        this.mouseHandler.enable()
        this.keyHandler.enable()
    }

    open fun postInit() {
        this.shader.link()
        GL30.glEnable(GL30.GL_DEPTH_TEST)
    }

    open fun preRender() = this.shader.use()

    open fun postRender() = this.shader.detach()

    open fun render() {
        if (this.validate())
            this.renderer.render()
    }


    open fun stop() {
        this.mouseHandler.disable()
        this.keyHandler.disable()
    }

    fun loadVertex(file: File) = this.shader.loadVertex(file)

    fun loadVertex(file: String) = this.loadVertex(File(file))

    fun loadFragment(file: File) = this.shader.loadFragment(file)

    fun loadFragment(file: String) = this.shader.loadFragment(File(file))

    open fun validate() = this.shader.validate()

    open fun validateQuiet() = this.shader.validateQuiet() == 0

    open fun addObject(obj: GameObject) {
        this.objs.add(obj)
        this.renderer.add(obj)
    }

    fun uploadCameraDataToGPU() = this.camera.uploadToGPU(this.shader)
}
