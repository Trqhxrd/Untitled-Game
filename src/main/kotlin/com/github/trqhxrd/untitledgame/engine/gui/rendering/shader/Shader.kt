package com.github.trqhxrd.untitledgame.engine.gui.rendering.shader

import org.apache.logging.log4j.LogManager
import org.lwjgl.opengl.GL30
import java.io.File
import java.nio.charset.StandardCharsets

open class Shader(val type: ShaderType) {
    var id: Int = -1
        protected set
    var program: ShaderProgram? = null
        private set
    private val logger = LogManager.getLogger()!!

    fun create(file: File): Int {
        val src = file.readText(StandardCharsets.UTF_8)
        this.id = GL30.glCreateShader(this.type.glType)
        GL30.glShaderSource(this.id, src)
        GL30.glCompileShader(this.id)

        if (GL30.glGetShaderi(this.id, GL30.GL_COMPILE_STATUS) == 0)
            logger.error(
                "Failed to compile ${type.name.uppercase()}-SHADER. " +
                        "Error code: ${GL30.glGetShaderInfoLog(this.id)}"
            )

        return id
    }

    fun attach(program: ShaderProgram) {
        this.program = program
        GL30.glAttachShader(this.program!!.id, this.id)
    }

    fun detach() {
        if (this.program == null) return
        GL30.glDetachShader(this.program!!.id, this.id)
        this.program = null
    }
}
