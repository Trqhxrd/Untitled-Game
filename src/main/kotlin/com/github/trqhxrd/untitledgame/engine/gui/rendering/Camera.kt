package com.github.trqhxrd.untitledgame.engine.gui.rendering

import com.github.trqhxrd.untitledgame.engine.gui.rendering.shader.ShaderProgram
import org.joml.Matrix4f
import org.joml.Vector3f

class Camera(x: Float, y: Float) {
    private val position = Vector3f()
    var x = x
        set(value) {
            field = value
            this.regeneratePositionVector()
        }
    var y = y
        set(value) {
            field = value
            this.regeneratePositionVector()
        }
    val projection = Matrix4f()
    val view = Matrix4f()
        get() {
            return field.identity()
                .lookAt(this.position, this.cameraFront().add(this.x, this.y, 0f), this.cameraUp())
        }
    private val cameraFront = { Vector3f(0f, 0f, -1f) }
    private val cameraUp = { Vector3f(0f, 1f, 0f) }
    private val regeneratePositionVector = { this.position.set(this.x, this.y, 20f).let { } }

    init {
        this.adjustProjection()
    }

    fun adjustProjection() {
        this.projection.identity()
        this.projection.ortho(0f, 32f * 40f, 0f, 32f * 21f, 0f, 100f)
    }

    fun uploadToGPU(shader: ShaderProgram) {
        shader.uploadMat4f("uProjection", this.projection)
        shader.uploadMat4f("uView", this.view)
    }
}
