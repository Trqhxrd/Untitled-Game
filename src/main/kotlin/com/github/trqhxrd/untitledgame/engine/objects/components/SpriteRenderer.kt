package com.github.trqhxrd.untitledgame.engine.objects.components

import com.github.trqhxrd.untitledgame.engine.gui.util.Color
import com.github.trqhxrd.untitledgame.engine.objects.Component

class SpriteRenderer(val color: Color = Color.WHITE, val texture: FloatArray = FloatArray(4) { 0f }) : Component()
