package com.github.trqhxrd.untitledgame.engine.gui.rendering.shader

import org.lwjgl.opengl.GL30

enum class ShaderType(val glType: Int) {
    VERTEX(GL30.GL_VERTEX_SHADER),
    FRAGMENT(GL30.GL_FRAGMENT_SHADER)
}
