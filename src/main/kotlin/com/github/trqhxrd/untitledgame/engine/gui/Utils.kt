package com.github.trqhxrd.untitledgame.engine.gui

object Utils {
    val timeStarted = System.nanoTime().toFloat()

    fun getTime() = ((System.nanoTime() - this.timeStarted) * 1E-9).toFloat()

    fun loadResource(path: String) = this::class.java.getResource(path)!!.readText()
}
