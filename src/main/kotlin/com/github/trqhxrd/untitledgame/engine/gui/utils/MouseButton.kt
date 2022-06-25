package com.github.trqhxrd.untitledgame.engine.gui.utils

enum class MouseButton {
    LEFT, RIGHT, MIDDLE, UNDEFINED;

    companion object {
        fun forAWTId(button: Int): MouseButton {
            return when (button) {
                1 -> LEFT
                2 -> RIGHT
                3 -> MIDDLE
                else -> UNDEFINED
            }
        }
    }
}
