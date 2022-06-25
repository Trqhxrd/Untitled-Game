package com.github.trqhxrd.untitledgame.engine.threading

abstract class AbstractThread(name: String, val delay: Long) : Thread(name) {
    var shutdown = false
        private set

    override fun run() {
        while (!this.shutdown) {
            sleep(this.delay)
            this.loop()
        }
    }

    abstract fun loop()

    fun shutdownGracefully() {
        this.shutdown = true
    }
}
