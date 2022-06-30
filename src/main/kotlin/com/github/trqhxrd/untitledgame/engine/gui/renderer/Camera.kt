package com.github.trqhxrd.untitledgame.engine.gui.renderer

import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f

class Camera(val position: Vector2f = Vector2f()) {
    val projection: Matrix4f = Matrix4f()
    val view: Matrix4f = Matrix4f()
        get() {
            val cameraFront = Vector3f(0f, 0f, -1f)
            val cameraUp = Vector3f(0f, 1f, 0f)

            field.identity()
            field.lookAt(
                Vector3f(this.position.x, this.position.y, 20f),
                cameraFront.add(this.position.x, this.position.y, 0f),
                cameraUp
            )
            return field
        }

    init {
        this.adjustProjection()
    }

    fun adjustProjection() {
        this.projection.identity()
        this.projection.ortho(0f, 32f * 40f, 0f, 32f * 21f, 0f, 100f)
    }
}
