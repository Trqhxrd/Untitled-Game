package com.github.trqhxrd.untitledgame.engine.gui.callback.keyboard

import com.github.trqhxrd.untitledgame.engine.gui.window.Window
import com.github.trqhxrd.untitledgame.engine.gui.callback.Action

@FunctionalInterface
interface KeyboardListener {
    fun interact(window: Window, key: Int, action: Action)
}
