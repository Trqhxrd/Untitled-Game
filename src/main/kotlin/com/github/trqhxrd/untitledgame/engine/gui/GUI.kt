package com.github.trqhxrd.untitledgame.engine.gui

import com.github.trqhxrd.untitledgame.engine.Core
import com.github.trqhxrd.untitledgame.engine.gui.display.Button
import com.github.trqhxrd.untitledgame.engine.gui.display.Display
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.JFrame

class GUI(val width: Int, val height: Int, title: String) {

    val title: String
    val frame: JFrame
    val display: Display

    init {
        this.title = title + this.pickTitleSuffix()
        this.frame = JFrame(this.title)
        this.display = Display()

        this.frame.setBounds(0, 0, this.width, this.height)
        this.display.setBounds(0, 0, this.width, this.height)

        this.frame.layout = null

        this.frame.add(this.display)
        this.display.elements.add(Button(BoundingBox(100, 100, 100, 100)))

        this.frame.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE
        this.frame.addWindowListener(Listener)
        this.frame.addMouseListener(Listener)
    }

    private fun pickTitleSuffix(): String {
        var line: String
        do {
            val url = this::class.java.getResource("/title-suffixes.txt")!!
            line = url.readText().split("\n").random()
        } while (line.isBlank())
        return line
    }

    fun addWindowListener(listener: WindowListener) = this.frame.addWindowListener(listener)

    fun windowListeners() = this.frame.windowListeners

    fun visibility() = this.frame.isVisible

    fun visibility(visible: Boolean) {
        this.frame.isVisible = visible
    }

    private object Listener : WindowListener, MouseListener {
        /**
         * Invoked the first time a window is made visible.
         * @param e the event to be processed
         */
        override fun windowOpened(e: WindowEvent?) {}

        /**
         * Invoked when the user attempts to close the window
         * from the window's system menu.
         * @param e the event to be processed
         */
        override fun windowClosing(e: WindowEvent?) {
            Core.close()
        }

        /**
         * Invoked when a window has been closed as the result
         * of calling dispose on the window.
         * @param e the event to be processed
         */
        override fun windowClosed(e: WindowEvent?) {}

        /**
         * Invoked when a window is changed from a normal to a
         * minimized state. For many platforms, a minimized window
         * is displayed as the icon specified in the window's
         * iconImage property.
         * @param e the event to be processed
         * @see java.awt.Frame.setIconImage
         */
        override fun windowIconified(e: WindowEvent?) {}

        /**
         * Invoked when a window is changed from a minimized
         * to a normal state.
         * @param e the event to be processed
         */
        override fun windowDeiconified(e: WindowEvent?) {}

        /**
         * Invoked when the Window is set to be the active Window. Only a Frame or
         * a Dialog can be the active Window. The native windowing system may
         * denote the active Window or its children with special decorations, such
         * as a highlighted title bar. The active Window is always either the
         * focused Window, or the first Frame or Dialog that is an owner of the
         * focused Window.
         * @param e the event to be processed
         */
        override fun windowActivated(e: WindowEvent?) {}

        /**
         * Invoked when a Window is no longer the active Window. Only a Frame or a
         * Dialog can be the active Window. The native windowing system may denote
         * the active Window or its children with special decorations, such as a
         * highlighted title bar. The active Window is always either the focused
         * Window, or the first Frame or Dialog that is an owner of the focused
         * Window.
         * @param e the event to be processed
         */
        override fun windowDeactivated(e: WindowEvent?) {}

        /**
         * Invoked when the mouse button has been clicked (pressed
         * and released) on a component.
         * @param e the event to be processed
         */
        override fun mouseClicked(e: MouseEvent) {}

        /**
         * Invoked when a mouse button has been pressed on a component.
         * @param e the event to be processed
         */
        override fun mousePressed(e: MouseEvent) {
            Core.gui.display.elements
                .filter { it.clickCallback != null }
                .filter { it.bounds.contains(e.point) }
                .forEach { it.clickCallback!!.onClick(it, e.point, MouseButton.forAWTId(e.button)) }
        }

        /**
         * Invoked when a mouse button has been released on a component.
         * @param e the event to be processed
         */
        override fun mouseReleased(e: MouseEvent?) {}

        /**
         * Invoked when the mouse enters a component.
         * @param e the event to be processed
         */
        override fun mouseEntered(e: MouseEvent) {}

        /**
         * Invoked when the mouse exits a component.
         * @param e the event to be processed
         */
        override fun mouseExited(e: MouseEvent?) {}
    }
}
