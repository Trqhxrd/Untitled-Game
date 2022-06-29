package com.github.trqhxrd.untitledgame.engine.gui.renderer

import org.apache.logging.log4j.LogManager
import org.lwjgl.opengl.GL30
import java.io.IOException

open class Shader(val path: String, val type: Type) {

    var shaderId: Int = -1
        protected set
    var programId = -1
        protected set
    var src: String
    private val logger = LogManager.getLogger("Shader")

    init {
        val url = this::class.java.getResource(this.path)!!
        this.src = url.readText()
    }

    fun compile() {
        this.shaderId = GL30.glCreateShader(this.type.glType)
        GL30.glShaderSource(this.shaderId, this.src)
        GL30.glCompileShader(this.shaderId)

        var success: Int = GL30.glGetShaderi(this.shaderId, this.type.glType)
        if (success == GL30.GL_FALSE) {
            System.err.println(GL30.glGetShaderInfoLog(this.shaderId))
            throw IOException("Could not compile shader! (${this.path})")
        }

        this.programId = GL30.glCreateProgram()
        GL30.glAttachShader(this.programId, this.shaderId)
        GL30.glLinkProgram(this.programId)

        success = GL30.glGetProgrami(this.programId, GL30.GL_LINK_STATUS)
        if (success == GL30.GL_FALSE) {
            System.err.println(GL30.glGetShaderInfoLog(this.shaderId))
            throw IOException("Could not link shader! (${this.path})")
        }
    }

    fun link() = GL30.glUseProgram(this.shaderId)

    fun unlink() = GL30.glUseProgram(0)

    enum class Type(val glType: Int) { VERTEX(GL30.GL_VERTEX_SHADER), FRAGMENT(GL30.GL_FRAGMENT_SHADER) }
}
