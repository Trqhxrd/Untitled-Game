package com.github.trqhxrd.untitledgame.engine.objects.components

import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import com.github.trqhxrd.untitledgame.engine.objects.Component

class SpriteRenderer(
    val color: Color = Color.WHITE,
    val textureCoordinates: Pair<Float, Float>,
    val textureDimensions: Pair<Float, Float>
) : Component()
