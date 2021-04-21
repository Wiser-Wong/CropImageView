package com.wiser.crop

import android.content.res.Resources
import android.graphics.*
import android.graphics.Shader.TileMode
import android.graphics.drawable.BitmapDrawable


/**
 * @author Wiser
 *
 * 弧形Drawable
 */
class RoundImageDrawable(resources: Resources, bitmap: Bitmap,var roundRadius: Float = 46f) : BitmapDrawable(resources,bitmap) {

    private var mPaint: Paint

    private var rectF: RectF

    init {
        val shader = BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP)
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.shader = shader
        mPaint.color = Color.WHITE

        rectF = RectF()
    }

    override fun draw(canvas: Canvas) {
        val width = bitmap.width
        val height = bitmap.height
        val radius = (width / 2).coerceAtMost(height / 2)
        rectF.left = 0f
        rectF.top = 0f
        rectF.right = width.toFloat()
        rectF.bottom = height.toFloat()

        canvas.drawRoundRect(rectF,roundRadius, roundRadius, mPaint)
    }
}