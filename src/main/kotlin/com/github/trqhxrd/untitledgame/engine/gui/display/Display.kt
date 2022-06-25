package com.github.trqhxrd.untitledgame.engine.gui.display

import com.github.trqhxrd.untitledgame.engine.Core
import com.github.trqhxrd.untitledgame.engine.gui.display.elements.DisplayElement
import java.awt.Graphics
import java.awt.Graphics2D
import java.util.function.Supplier
import javax.swing.JComponent

open class Display() {

    var size = Supplier<Pair<Int, Int>> { return@Supplier Core.gui.frame.width to  Core.gui.frame.height}
    val elements = mutableListOf<DisplayElement>()
    val component = Component(elements)

    fun addElement(element: DisplayElement) = this.elements.add(element)

    fun repaint() = this.component.repaint()

    class Component(val elements: MutableList<DisplayElement>) : JComponent() {
        override fun paintComponent(g: Graphics) {
            this.elements.forEach { it.draw(g as Graphics2D) }
        }
    }
}
