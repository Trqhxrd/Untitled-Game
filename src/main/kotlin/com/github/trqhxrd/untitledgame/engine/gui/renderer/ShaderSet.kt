package com.github.trqhxrd.untitledgame.engine.gui.renderer

import org.lwjgl.opengl.GL30
import java.io.IOException

class ShaderSet {
    var isValid = false
    var vertex: VertexShader? = null
        private set
    var fragment: FragmentShader? = null
        private set
    var program: Int = -1

    init {
        this.program = GL30.glCreateProgram()
    }

    fun validate(): Boolean {
        this.isValid = this.vertex != null && this.fragment != null
        return this.isValid
    }

    fun addVertex(path: String) {
        this.vertex = VertexShader(path)
        this.addShader(this.vertex!!)
    }

    fun addFragment(path: String) {
        this.fragment = FragmentShader(path)
        this.addShader(this.fragment!!)
    }

    private fun addShader(shader: Shader) {
        shader.compile()
        this.link(shader)

        this.validate()
    }

    private fun link(shader: Shader) {
        GL30.glAttachShader(this.program, shader.id)
        GL30.glLinkProgram(this.program)

        val success = GL30.glGetProgrami(this.program, GL30.GL_LINK_STATUS)
        if (success == GL30.GL_FALSE) {
            System.err.println(GL30.glGetShaderInfoLog(shader.id))
            throw IOException("Could not link shader! (${shader.path})")
        }
    }

    fun use() = GL30.glUseProgram(this.program)

    fun detach() = GL30.glUseProgram(0)

    fun destroy() {

    }
}
