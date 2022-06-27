package com.github.trqhxrd.untitledgame.engine.gui.scene

import com.github.trqhxrd.untitledgame.engine.gui.Color
import com.github.trqhxrd.untitledgame.engine.gui.Window
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30


class DebugScene(window: Window) : Scene(window, background = Color.BLACK) {
    private val vertexShaderSrc = """
        #version 330 core

        layout(location=0) in vec3 aPos;
        layout(location=1) in vec4 aColor;

        out vec4 fColor;

        void main() {
            fColor = aColor;
            gl_Position  = vec4(aPos, 1.0);
        }

    """.trimIndent()

    private val fragmentShaderSrc = """
        #version 330 core

        in vec4 fColor;

        out vec4 color;

        void main() {
            color = fColor;
        }

    """.trimIndent()

    private var vertexID = 0
    private var fragmentID: Int = 0
    private var shaderProgram: Int = 0

    private val vertexArray = floatArrayOf(
        0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
        -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
        0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
        -0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f
    )

    private val elementArray = intArrayOf(
        2, 1, 0,
        0, 1, 3
    )

    private var vaoID = 0
    private var vboID: Int = 0
    private var eboID: Int = 0

    init {
        vertexID = GL30.glCreateShader(GL30.GL_VERTEX_SHADER)
        GL30.glShaderSource(vertexID, vertexShaderSrc)
        GL30.glCompileShader(vertexID)

        var success: Int = GL30.glGetShaderi(vertexID, GL30.GL_COMPILE_STATUS)
        if (success == GL30.GL_FALSE) {
            val len: Int = GL30.glGetShaderi(vertexID, GL30.GL_INFO_LOG_LENGTH)
            println("ERROR: 'defaultShader.glsl'\n\tVertex shader compilation failed.")
            println(GL30.glGetShaderInfoLog(vertexID, len))
            assert(false) { "" }
        }

        fragmentID = GL30.glCreateShader(GL30.GL_FRAGMENT_SHADER)
        GL30.glShaderSource(fragmentID, fragmentShaderSrc)
        GL30.glCompileShader(fragmentID)

        success = GL30.glGetShaderi(fragmentID, GL30.GL_COMPILE_STATUS)
        if (success == GL30.GL_FALSE) {
            val len: Int = GL30.glGetShaderi(fragmentID, GL30.GL_INFO_LOG_LENGTH)
            println("ERROR: 'defaultShader.glsl'\n\tFragment shader compilation failed.")
            println(GL30.glGetShaderInfoLog(fragmentID, len))
            assert(false) { "" }
        }

        shaderProgram = GL30.glCreateProgram()
        GL30.glAttachShader(shaderProgram, vertexID)
        GL30.glAttachShader(shaderProgram, fragmentID)
        GL30.glLinkProgram(shaderProgram)

        success = GL30.glGetProgrami(shaderProgram, GL30.GL_LINK_STATUS)
        if (success == GL30.GL_FALSE) {
            val len: Int = GL30.glGetProgrami(shaderProgram, GL30.GL_INFO_LOG_LENGTH)
            println("ERROR: 'defaultShader.glsl'\n\tLinking of shaders failed.")
            println(GL30.glGetProgramInfoLog(shaderProgram, len))
            assert(false) { "" }
        }

        vaoID = GL30.glGenVertexArrays()
        GL30.glBindVertexArray(vaoID)

        val vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.size)
        vertexBuffer.put(vertexArray).flip()

        vboID = GL30.glGenBuffers()
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexBuffer, GL30.GL_STATIC_DRAW)

        val elementBuffer = BufferUtils.createIntBuffer(elementArray.size)
        elementBuffer.put(elementArray).flip()
        eboID = GL30.glGenBuffers()
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, eboID)
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL30.GL_STATIC_DRAW)

        val positionsSize = 3
        val colorSize = 4
        val floatSizeBytes = 4
        val vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes
        GL30.glVertexAttribPointer(0, positionsSize, GL30.GL_FLOAT, false, vertexSizeBytes, 0)
        GL30.glEnableVertexAttribArray(0)
        GL30.glVertexAttribPointer(1, colorSize, GL30.GL_FLOAT, false, vertexSizeBytes, (positionsSize * floatSizeBytes).toLong())
        GL30.glEnableVertexAttribArray(1)
    }

    override fun update(dTime: Float) {
        GL30.glUseProgram(shaderProgram)
        GL30.glBindVertexArray(vaoID)

        GL30.glEnableVertexAttribArray(0)
        GL30.glEnableVertexAttribArray(1)
        GL30.glDrawElements(GL30.GL_TRIANGLES, elementArray.size, GL30.GL_UNSIGNED_INT, 0)

        GL30.glDisableVertexAttribArray(0)
        GL30.glDisableVertexAttribArray(1)
        GL30.glBindVertexArray(0)
        GL30.glUseProgram(0)
    }
}
