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

    private val vertexArray = floatArrayOf( // position               // color
        0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,  // Bottom right 0
        -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,  // Top left     1
        0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,  // Top right    2
        -0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f
    )

    // IMPORTANT: Must be in counter-clockwise order
    private val elementArray = intArrayOf( /*
                    x        x
                    x        x
             */
        2, 1, 0,  // Top right triangle
        0, 1, 3 // bottom left triangle
    )

    private var vaoID = 0
    private var vboID: Int = 0
    private var eboID: Int = 0

    fun LevelEditorScene() {}

    init {
        // ============================================================
        // Compile and link shaders
        // ============================================================

        // First load and compile the vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER)
        // Pass the shader source to the GPU
        glShaderSource(vertexID, vertexShaderSrc)
        glCompileShader(vertexID)

        // Check for errors in compilation
        var success: Int = glGetShaderi(vertexID, GL_COMPILE_STATUS)
        if (success == GL_FALSE) {
            val len: Int = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH)
            println("ERROR: 'defaultShader.glsl'\n\tVertex shader compilation failed.")
            System.out.println(glGetShaderInfoLog(vertexID, len))
            assert(false) { "" }
        }

        // First load and compile the vertex shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER)
        // Pass the shader source to the GPU
        glShaderSource(fragmentID, fragmentShaderSrc)
        glCompileShader(fragmentID)

        // Check for errors in compilation
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS)
        if (success == GL_FALSE) {
            val len: Int = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH)
            println("ERROR: 'defaultShader.glsl'\n\tFragment shader compilation failed.")
            System.out.println(glGetShaderInfoLog(fragmentID, len))
            assert(false) { "" }
        }

        // Link shaders and check for errors
        shaderProgram = glCreateProgram()
        glAttachShader(shaderProgram, vertexID)
        glAttachShader(shaderProgram, fragmentID)
        glLinkProgram(shaderProgram)

        // Check for linking errors
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS)
        if (success == GL_FALSE) {
            val len: Int = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH)
            println("ERROR: 'defaultShader.glsl'\n\tLinking of shaders failed.")
            System.out.println(glGetProgramInfoLog(shaderProgram, len))
            assert(false) { "" }
        }

        // ============================================================
        // Generate VAO, VBO, and EBO buffer objects, and send to GPU
        // ============================================================
        vaoID = glGenVertexArrays()
        glBindVertexArray(vaoID)

        // Create a float buffer of vertices
        val vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.size)
        vertexBuffer.put(vertexArray).flip()

        // Create VBO upload the vertex buffer
        vboID = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vboID)
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW)

        // Create the indices and upload
        val elementBuffer = BufferUtils.createIntBuffer(elementArray.size)
        elementBuffer.put(elementArray).flip()
        eboID = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW)

        // Add the vertex attribute pointers
        val positionsSize = 3
        val colorSize = 4
        val floatSizeBytes = 4
        val vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0)
        glEnableVertexAttribArray(0)
        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize * floatSizeBytes).toLong())
        glEnableVertexAttribArray(1)
    }

    override fun update(dt: Float) {
        // Bind shader program
        glUseProgram(shaderProgram)
        // Bind the VAO that we're using
        glBindVertexArray(vaoID)

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glDrawElements(GL_TRIANGLES, elementArray.size, GL_UNSIGNED_INT, 0)

        // Unbind everything
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glBindVertexArray(0)
        glUseProgram(0)
    }
}
