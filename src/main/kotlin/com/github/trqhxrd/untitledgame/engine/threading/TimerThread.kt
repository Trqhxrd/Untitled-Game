package com.github.trqhxrd.untitledgame.engine.threading

import java.util.concurrent.ConcurrentHashMap

class TimerThread : AbstractThread("timer", 10) {

    private val callbacks = ConcurrentHashMap<Runnable, Long>()

    fun add(delay: Long, callback: () -> Unit) = this.callbacks.put(callback, delay)

    override fun init() {}

    override fun loop() {
        callbacks.keys().toList().forEach {
            this.callbacks[it] = this.callbacks[it]!! - 1
            if (this.callbacks[it]!! <= 0) {
                it.run()
                this.callbacks.remove(it)
            }
        }
    }
}
