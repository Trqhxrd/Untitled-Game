package com.github.trqhxrd.untitledgame.engine.gui.rendering.shader

import com.github.trqhxrd.untitledgame.engine.gui.rendering.shader.types.FragmentShader
import com.github.trqhxrd.untitledgame.engine.gui.rendering.shader.types.VertexShader
import org.apache.logging.log4j.LogManager
import org.lwjgl.opengl.GL30
import java.io.File

class ShaderProgram(val name: String) {
    val id = GL30.glCreateProgram()
    var vertex: VertexShader? = null
        private set
    var fragment: FragmentShader? = null
        private set
    private val logger = LogManager.getLogger()!!

    fun validate(): Boolean {
        var error = 0
        if (this.id == -1) error += 1
        if (this.vertex == null) error += 2
        if (this.fragment == null) error += 4

        return if (error != 0) {
            logger.warn("Shader validation for shader '${this.name}' failed ($error).")
            false
        } else true
    }

    fun loadVertex(file: File) {
        this.vertex = VertexShader()
        this.vertex!!.create(file)
        this.vertex!!.attach(this)
    }

    fun loadFragment(file: File) {
        this.fragment = FragmentShader()
        this.fragment!!.create(file)
        this.fragment!!.attach(this)
    }

    fun link() = GL30.glLinkProgram(this.id)

    fun unlink() = GL30.glLinkProgram(0)
}
