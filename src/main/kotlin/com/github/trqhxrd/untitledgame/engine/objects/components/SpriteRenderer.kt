package com.github.trqhxrd.untitledgame.engine.objects.components

import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import com.github.trqhxrd.untitledgame.engine.objects.Component

class SpriteRenderer(
    val color: Color = Color.WHITE,
    val coordsNormalized: Pair<Float, Float>,
    val dimensionsNormalized: Pair<Float, Float>
) : Component()
