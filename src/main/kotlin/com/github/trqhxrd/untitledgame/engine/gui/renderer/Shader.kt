package com.github.trqhxrd.untitledgame.engine.gui.renderer

import org.apache.logging.log4j.LogManager
import org.lwjgl.opengl.GL30
import java.io.IOException

open class Shader(val path: String, val type: Type) {

    var id: Int = -1
        protected set
    var src: String
    private val logger = LogManager.getLogger("Shader")

    init {
        val url = this::class.java.getResource(this.path)!!
        this.src = url.readText()
    }

    fun compile() {
        this.id = GL30.glCreateShader(this.type.glType)
        GL30.glShaderSource(this.id, this.src)
        GL30.glCompileShader(this.id)

        val success: Int = GL30.glGetShaderi(this.id, GL30.GL_COMPILE_STATUS)
        if (success != GL30.GL_TRUE) {
            System.err.println(GL30.glGetShaderInfoLog(this.id))
            throw IOException("Could not compile shader! (${this.path})")
        }
    }

    enum class Type(val glType: Int) { VERTEX(GL30.GL_VERTEX_SHADER), FRAGMENT(GL30.GL_FRAGMENT_SHADER) }
}
