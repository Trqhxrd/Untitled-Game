package com.github.trqhxrd.untitledgame.engine.gui.scene

import com.github.trqhxrd.untitledgame.engine.Core
import com.github.trqhxrd.untitledgame.engine.gui.Color
import com.github.trqhxrd.untitledgame.engine.gui.Window
import com.github.trqhxrd.untitledgame.engine.gui.callback.Action
import com.github.trqhxrd.untitledgame.engine.gui.callback.keyboard.KeyboardListener
import org.apache.logging.log4j.LogManager
import org.lwjgl.glfw.GLFW

class DebugScene(window: Window) : Scene(window, background = Color.CYAN) {

    private var isFading = false
    private var timeLeftToFade = 2f

    private var logger = LogManager.getLogger("Scene")

    init {
        this.keyHandler.listeners.add(object : KeyboardListener {
            override fun interact(window: Window, key: Int, action: Action) {
                if (!this@DebugScene.isFading && key == GLFW.GLFW_KEY_SPACE) this@DebugScene.isFading = true
            }
        })
    }

    override fun update(dTime: Float) {
        if (this.isFading && this.timeLeftToFade > 0) {
            this.timeLeftToFade -= dTime
            this.background.add(-(dTime), -dTime, -dTime).also { this.background = it }
        } else if (this.isFading) {
            this.logger.info("Fade done!")
            this.isFading = false
            this.timeLeftToFade = 2f
            Core.timer.add(50) { this.background = Color.values.random() }
        }
    }
}
