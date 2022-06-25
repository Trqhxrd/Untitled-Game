package com.github.trqhxrd.untitledgame.engine.gui.display

import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JComponent

class Display : JComponent() {

    val elements = mutableListOf<DisplayElement>()

    override fun paintComponent(g: Graphics) {
        this.elements.forEach { it.draw(g as Graphics2D) }
    }
}
