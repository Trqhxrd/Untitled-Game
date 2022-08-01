package com.github.trqhxrd.untitledgame.engine.threading

import org.apache.logging.log4j.LogManager

abstract class AbstractThread(name: String, val delay: Long) : Thread(name) {

    var shutdownScheduled = false
        private set
    var hasStopped = false
        private set
    private val logger = LogManager.getLogger()!!

    override fun run() {
        this.logger.debug("Initialized new Thread with name '${this.name}'.")
        this.init()
        do {
            sleep(this.delay)
            this.loop()
        } while (!this.shutdownScheduled)
        this.logger.debug("Stopping Thread '${this.name}'.")
        this.close()
        this.logger.debug("Stopped Thread '${this.name}'.")

        this.hasStopped = true
    }

    abstract fun init()

    abstract fun loop()

    abstract fun close()

    override fun interrupt() {
        super.interrupt()
        this.logger.warn("Thread '${this.name} has been interrupted!")
        this.hasStopped = true
    }

    fun shutdownGracefully() {
        this.shutdownScheduled = true
        this.logger.debug("Shutdown for Thread '${this.name}' has be scheduled.")
    }
}
