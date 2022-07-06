package com.github.trqhxrd.untitledgame.engine.threading

abstract class AbstractThread(name: String, val delay: Long) : Thread(name) {

    var shutdownScheduled = false
        private set
    var hasStopped = false
        private set

    override fun run() {
        this.init()
        do {
            sleep(this.delay)
            this.loop()
        } while (!this.shutdownScheduled)
        this.close()

        this.hasStopped = true
    }

    abstract fun init()

    abstract fun loop()

    abstract fun close()

    override fun interrupt() {
        super.interrupt()
        this.hasStopped = true
    }

    fun shutdownGracefully() {
        this.shutdownScheduled = true
    }
}
