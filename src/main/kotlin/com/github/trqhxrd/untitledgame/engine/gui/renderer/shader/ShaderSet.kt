package com.github.trqhxrd.untitledgame.engine.gui.renderer.shader

import org.joml.*
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30
import java.io.IOException

class ShaderSet {
    var isValid = false
    var vertex: VertexShader? = null
        private set
    var fragment: FragmentShader? = null
        private set
    var program: Int = -1
    var inUse = false
        private set

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

    fun use() {
        if (!this.inUse) {
            this.inUse = true
            GL30.glUseProgram(this.program)
        }
    }

    fun detach() {
        GL30.glUseProgram(0)
        this.inUse = false
    }

    fun destroy() {

    }

    fun uploadMat4f(varName: String, matrix: Matrix4f) {
        val varLoc = this.prepareUpload(varName)
        val buf = BufferUtils.createFloatBuffer(4 * 4)
        matrix.get(buf)
        GL30.glUniformMatrix4fv(varLoc, false, buf)
    }

    fun uploadMat3f(varName: String, matrix: Matrix3f) {
        val varLoc = this.prepareUpload(varName)
        val buf = BufferUtils.createFloatBuffer(3 * 3)
        matrix.get(buf)
        GL30.glUniformMatrix3fv(varLoc, false, buf)
    }

    fun uploadMat2f(varName: String, matrix: Matrix2f) {
        val varLoc = this.prepareUpload(varName)
        val buf = BufferUtils.createFloatBuffer(2 * 2)
        matrix.get(buf)
        GL30.glUniformMatrix2fv(varLoc, false, buf)
    }

    fun uploadVec4f(varName: String, vector: Vector4f) {
        val varLoc = this.prepareUpload(varName)
        GL30.glUniform4f(varLoc, vector.x, vector.y, vector.z, vector.w)
    }

    fun uploadVec3f(varName: String, vector: Vector3f) {
        val varLoc = this.prepareUpload(varName)
        GL30.glUniform3f(varLoc, vector.x, vector.y, vector.z)
    }

    fun uploadVec2f(varName: String, vector: Vector2f) {
        val varLoc = this.prepareUpload(varName)
        GL30.glUniform2f(varLoc, vector.x, vector.y)
    }

    fun uploadFloat(varName: String, value: Float) {
        val varLoc = this.prepareUpload(varName)
        GL30.glUniform1f(varLoc, value)
    }

    fun uploadInt(varName: String, value: Int) {
        val varLoc = this.prepareUpload(varName)
        GL30.glUniform1i(varLoc, value)
    }

    private fun prepareUpload(varName: String): Int {
        val varLoc = GL30.glGetUniformLocation(this.program, varName)
        this.use()
        return varLoc
    }
}
