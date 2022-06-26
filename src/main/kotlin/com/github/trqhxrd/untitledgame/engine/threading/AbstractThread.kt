package com.github.trqhxrd.untitledgame.engine.threading

abstract class AbstractThread(name: String, val delay: Long) : Thread(name) {

    private var shutdownScheduled = false
    var hasStopped = false
        private set

    override fun run() {
        this.init()
        while (!this.shutdownScheduled) {
            sleep(this.delay)
            this.loop()
        }
        this.hasStopped = true
    }

    abstract fun init()

    abstract fun loop()

    override fun interrupt() {
        super.interrupt()
        this.hasStopped = true
    }

    fun shutdownGracefully() {
        this.shutdownScheduled = true
    }
}
