package com.github.trqhxrd.untitledgame.engine.gui.utils

import java.awt.event.WindowEvent
import java.awt.event.WindowListener

abstract class EmptyWindowListener : WindowListener {
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
    override fun windowClosing(e: WindowEvent?) {}

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
}
