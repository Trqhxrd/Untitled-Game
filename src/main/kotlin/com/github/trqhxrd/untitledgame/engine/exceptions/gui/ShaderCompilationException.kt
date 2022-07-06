package com.github.trqhxrd.untitledgame.engine.exceptions.gui

import com.github.trqhxrd.untitledgame.engine.exceptions.GameException

class ShaderCompilationException(message: String = "An error occurred!", cause: Throwable? = null) :
    GameException(message, cause)
