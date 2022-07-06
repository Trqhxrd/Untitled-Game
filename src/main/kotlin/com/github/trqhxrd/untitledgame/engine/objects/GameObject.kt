package com.github.trqhxrd.untitledgame.engine.objects

import com.github.trqhxrd.untitledgame.engine.gui.util.Rectangle
import org.apache.logging.log4j.LogManager

class GameObject(val name: String, var boundings: Rectangle) {

    private val components = mutableSetOf<Component>()
    private val logger = LogManager.getLogger()!!

    constructor(name: String, x: Int, y: Int, width: Int, height: Int) :
            this(name, Rectangle(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat()))

    init {
        this.logger.debug("Created new GameObject '${this.name}'.")
    }

    fun add(component: Component) {
        component.obj = this
        this.logger.debug("Added new component '${component::class.simpleName}' to GameObject '${this.name}'.")
        this.components.add(component)
    }

    fun <T : Component> get(clazz: Class<T>): T? {
        for (component in this.components)
            if (clazz.isAssignableFrom(component::class.java))
                return clazz.cast(component)
        return null
    }

    fun <T : Component> remove(clazz: Class<T>) {
        val component = this.get(clazz) ?: return
        this.logger.debug("Removing component '${clazz.simpleName}' from GameObject '${this.name}'.")
        component.obj = null
        this.components.remove(component)
    }
}
