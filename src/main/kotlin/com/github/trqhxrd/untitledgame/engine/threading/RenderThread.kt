package com.github.trqhxrd.untitledgame.engine.threading

import com.github.trqhxrd.untitledgame.engine.gui.Window

class RenderThread : AbstractThread("render", 10) {

    lateinit var window: Window

    override fun init() {
        this.window = Window(1920, 1080, "The Untitled Game: ")
    }

    override fun loop() {
        this.window.update()
    }
}
