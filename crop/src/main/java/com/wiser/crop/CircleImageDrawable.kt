package com.wiser.crop

import android.content.res.Resources
import android.graphics.*
import android.graphics.Shader.TileMode
import android.graphics.drawable.BitmapDrawable


/**
 * @author Wiser
 *
 * 圆形Drawable
 */
class CircleImageDrawable(resources: Resources, bitmap: Bitmap) : BitmapDrawable(resources,bitmap) {

    private var mPaint: Paint

    init {
        val shader = BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP)
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.shader = shader
        mPaint.color = Color.WHITE
    }

    override fun draw(canvas: Canvas) {
        val width = bitmap.width
        val height = bitmap.height
        val radius = (width / 2).coerceAtMost(height / 2)
        val x = if (width > radius + radius) width / 2 else radius
        val y = if (height > radius + radius) height / 2 else radius
        canvas.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), mPaint)
    }

}