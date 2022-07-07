package com.github.trqhxrd.untitledgame.engine.gui.rendering

import com.github.trqhxrd.untitledgame.engine.gui.rendering.shader.ShaderProgram
import com.github.trqhxrd.untitledgame.engine.gui.window.Scene
import com.github.trqhxrd.untitledgame.engine.objects.GameObject
import com.github.trqhxrd.untitledgame.engine.objects.components.SpriteRenderer
import org.apache.logging.log4j.LogManager

class Renderer(val scene: Scene) {
    private val logger = LogManager.getLogger()

    companion object {
        const val MAX_BATCH_SIZE = 1024
    }

    private val batches = mutableListOf<RenderBatch>()

    fun add(obj: GameObject) {
        val sprite = obj.get(SpriteRenderer::class.java)
        if (sprite == null) {
            this.logger.warn("Tried to add GameObject '${obj.name}' to Renderer without a SpriteRenderer.")
            return
        }

        var added = false
        for (batch in this.batches) {
            if (batch.hasRoom) {
                batch.addSprite(sprite)
                added = true
                break
            }
        }

        if (!added) {
            val batch = RenderBatch(this.batches.size, this.scene, MAX_BATCH_SIZE)
            this.batches.add(batch)
            batch.addSprite(sprite)
        }
    }

    fun render() = this.batches.forEach { it.render() }
}
