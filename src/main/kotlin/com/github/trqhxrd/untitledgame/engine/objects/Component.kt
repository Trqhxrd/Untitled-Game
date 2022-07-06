package com.github.trqhxrd.untitledgame.engine.objects

import org.apache.logging.log4j.LogManager

abstract class Component {

    var obj: GameObject? = null
    private val logger = LogManager.getLogger()!!
    private var isUpdateMessageSent = false

    open fun init() {
        this.logger.debug("Initializing new Component of type ${this::class.simpleName}.")
    }

    open fun update() {
        if (!this.isUpdateMessageSent) {
            this.logger.debug("Updating component '${this::class.simpleName}' with GameObject '${this.obj?.name}'.")
            this.isUpdateMessageSent = true
        }
    }
}
