package com.github.trqhxrd.untitledgame.engine.gui.rendering.shader

import com.github.trqhxrd.untitledgame.engine.exceptions.gui.ShaderCompilationException
import com.github.trqhxrd.untitledgame.engine.gui.rendering.shader.types.FragmentShader
import com.github.trqhxrd.untitledgame.engine.gui.rendering.shader.types.VertexShader
import org.apache.logging.log4j.LogManager
import org.joml.Matrix4f
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryUtil
import java.io.File

class ShaderProgram(val name: String) {
    var id = GL30.glCreateProgram()
        private set
    var vertex: VertexShader? = null
        private set
    var fragment: FragmentShader? = null
        private set
    private val logger = LogManager.getLogger()!!

    fun validate(): Boolean {
        val result = this.validateQuiet()
        return if (result != 0) {
            logger.warn(ShaderCompilationException("Shader validation for shader '${this.name}' failed ($result)."))
            false
        } else true
    }

    fun validateQuiet(): Int {
        var error = 0
        if (this.id == -1) error += 1
        if (this.vertex == null) error += 2
        if (this.fragment == null) error += 4
        return error
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

    fun use() = GL30.glUseProgram(this.id)

    fun detach() = GL30.glUseProgram(0)

    fun cleanup() {
        this.unlink()
        GL30.glDeleteProgram(this.id)
        this.id = -1
    }

    fun setUniform(varName: String, value: Matrix4f) {
        val varLoc = GL30.glGetUniformLocation(this.id, varName)
        val buf = MemoryUtil.memAllocFloat(4 * 4)
        value.get(buf)
        GL30.glUniformMatrix4fv(varLoc, false, buf)
    }

    fun setUniform(varName: String, value: Int) {
        val varLoc = GL30.glGetUniformLocation(this.id, varName)
        GL30.glUniform1i(varLoc, value)
    }
}
