package com.github.trqhxrd.untitledgame.engine.exceptions

open class GameException(message: String = "An error occurred!", cause: Throwable? = null) :
    Exception(message, cause)
