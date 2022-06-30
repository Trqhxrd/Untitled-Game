package com.github.trqhxrd.untitledgame.engine.gui

import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.toPath


object Utils {
    val timeStarted = System.nanoTime().toFloat()

    fun getTime() = ((System.nanoTime() - this.timeStarted) * 1E-9).toFloat()

    fun loadResource(path: String) = this::class.java.getResource(path)!!.readText()
}
