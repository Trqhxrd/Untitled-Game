package com.github.trqhxrd.untitledgame.engine.gui.scene

import com.github.trqhxrd.untitledgame.engine.gui.Color
import com.github.trqhxrd.untitledgame.engine.gui.Window
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30.*


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
        vertexID = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexID, vertexShaderSrc)
        glCompileShader(vertexID)

        var success: Int = glGetShaderi(vertexID, GL_COMPILE_STATUS)
        if (success == GL_FALSE) {
            val len: Int = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH)
            println("ERROR: 'defaultShader.glsl'\n\tVertex shader compilation failed.")
            System.out.println(glGetShaderInfoLog(vertexID, len))
            assert(false) { "" }
        }

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fragmentID, fragmentShaderSrc)
        glCompileShader(fragmentID)

        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS)
        if (success == GL_FALSE) {
            val len: Int = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH)
            println("ERROR: 'defaultShader.glsl'\n\tFragment shader compilation failed.")
            System.out.println(glGetShaderInfoLog(fragmentID, len))
            assert(false) { "" }
        }

        shaderProgram = glCreateProgram()
        glAttachShader(shaderProgram, vertexID)
        glAttachShader(shaderProgram, fragmentID)
        glLinkProgram(shaderProgram)

        success = glGetProgrami(shaderProgram, GL_LINK_STATUS)
        if (success == GL_FALSE) {
            val len: Int = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH)
            println("ERROR: 'defaultShader.glsl'\n\tLinking of shaders failed.")
            System.out.println(glGetProgramInfoLog(shaderProgram, len))
            assert(false) { "" }
        }

        vaoID = glGenVertexArrays()
        glBindVertexArray(vaoID)

        val vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.size)
        vertexBuffer.put(vertexArray).flip()

        vboID = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vboID)
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW)

        val elementBuffer = BufferUtils.createIntBuffer(elementArray.size)
        elementBuffer.put(elementArray).flip()
        eboID = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW)

        val positionsSize = 3
        val colorSize = 4
        val floatSizeBytes = 4
        val vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0)
        glEnableVertexAttribArray(0)
        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize * floatSizeBytes).toLong())
        glEnableVertexAttribArray(1)
    }

    override fun update(dTime: Float) {
        glUseProgram(shaderProgram)
        glBindVertexArray(vaoID)

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glDrawElements(GL_TRIANGLES, elementArray.size, GL_UNSIGNED_INT, 0)

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glBindVertexArray(0)
        glUseProgram(0)
    }
}
