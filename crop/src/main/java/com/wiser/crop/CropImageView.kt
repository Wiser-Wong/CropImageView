package com.wiser.crop

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

/**
 * @author Wiser
 *
 * 裁剪图片控件
 */
class CropImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    /**
     * 最小裁剪距离
     */
    private var minCropDistance: Int = 114

    /**
     * 加载图片展示类型
     */
    private var shapeType: Int = SHAPE_DEFAULT

    /**
     * 弧度半径
     */
    private var roundRadius: Float = 46f

    /**
     * 错误图的id
     */
    private var errorDrawableId = -1

    /**
     * 裁剪比重
     */
    private var cropGravityType = CROP_GRAVITY_TOP

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CropImageView)
        minCropDistance = ta.getDimension(
            R.styleable.CropImageView_civ_min_crop_distance,
            minCropDistance.toFloat()
        ).toInt()
        shapeType = ta.getInt(R.styleable.CropImageView_civ_shape, shapeType)
        cropGravityType = ta.getInt(R.styleable.CropImageView_civ_gravity, cropGravityType)
        roundRadius = ta.getDimension(R.styleable.CropImageView_civ_shape_round_radius, roundRadius)
        errorDrawableId = ta.getResourceId(
            R.styleable.CropImageView_civ_error_default_pic_src,
            errorDrawableId
        )
        ta.recycle()
    }

    companion object {
        /**
         * 圆形
         */
        const val SHAPE_CIRCLE = 1010

        /**
         * 弧形
         */
        const val SHAPE_ROUND = 1011

        /**
         * 默认
         */
        const val SHAPE_DEFAULT = 1012

        /**
         * 纵向裁剪顶部开始
         */
        const val CROP_GRAVITY_TOP = 1013

        /**
         * 纵向裁剪中间
         */
        const val CROP_GRAVITY_CENTER_Y = 1014

        /**
         * 纵向裁剪底部开始
         */
        const val CROP_GRAVITY_BOTTOM = 1015

        /**
         * 横向裁剪左边开始
         */
        const val CROP_GRAVITY_START = 1016

        /**
         * 横向裁剪中间
         */
        const val CROP_GRAVITY_CENTER_X = 1017

        /**
         * 横向裁剪右边开始
         */
        const val CROP_GRAVITY_END = 1018
    }

    fun setMinCropHeight(minCropHeight: Int) {
        this.minCropDistance = minCropHeight
    }

    fun getMinCropHeight(): Int = minCropDistance

    fun setShapeType(shapeType: Int) {
        this.shapeType = shapeType
    }

    fun getShapeType(): Int = shapeType

    fun setRoundRadius(roundRadius: Float) {
        this.roundRadius = roundRadius
    }

    fun getRoundRadius(): Float = roundRadius

    fun setCropGravityType(cropGravityType: Int) {
        this.cropGravityType = cropGravityType
    }

    fun getCropGravityType(): Int = cropGravityType

    /**
     * 加载裁剪高图片
     * @param url 图片地址
     * @param cropHeight 裁剪高度
     * @param width 图片宽度
     *
     * 默认按宽度压缩，裁剪到控件高度
     */
    fun loadCropHeightImage(url: String?, cropHeight: Int = this.height, width: Int = this.width) {
        if (context == null || ((context is Activity) && (context as? Activity)?.isFinishing == true)) return
        context?.apply {
            Glide.with(this).asBitmap().load(url).skipMemoryCache(false).diskCacheStrategy(
                DiskCacheStrategy.ALL
            ).error(errorDrawableId).placeholder(errorDrawableId).listener(object :
                RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    setImageResource(errorDrawableId)
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            }).into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    if ((width == 0 && this@CropImageView.width == 0) || cropHeight == 0 && this@CropImageView.height == 0) {
                        setImageBitmap(resource)
                    } else {
                        if (cropHeight > 0 && cropHeight < resource.height) {
                            val bitmap = compressWidthAndHeightForBitmap(
                                resource,
                                if (width == 0) this@CropImageView.width else width,
                                if (cropHeight == 0) this@CropImageView.height else cropHeight
                            )
                            setImageCropHeightBitmap(
                                bitmap,
                                if (cropHeight > bitmap.height) bitmap.height else if (cropHeight > minCropDistance) cropHeight else minCropDistance
                            )
                        } else {
                            setImageBitmap(resource)
                        }
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }
    }

    /**
     * 加载裁剪高图片
     * @param url 图片地址
     * @param cropWidth 裁剪宽度
     * @param height 图片高度
     *
     * 默认按高度压缩，裁剪到控件宽度
     */
    fun loadCropWidthImage(url: String?, cropWidth: Int = this.width, height: Int = this.height) {
        if (context == null || ((context is Activity) && (context as? Activity)?.isFinishing == true)) return
        context?.apply {
            Glide.with(this).asBitmap().load(url).skipMemoryCache(false).diskCacheStrategy(
                DiskCacheStrategy.ALL
            ).error(errorDrawableId).placeholder(errorDrawableId).listener(object :
                RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    setImageResource(errorDrawableId)
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            }).into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    if ((height == 0 && this@CropImageView.height == 0) || cropWidth == 0 && this@CropImageView.width == 0) {
                        setImageBitmap(resource)
                    } else {
                        if (cropWidth > 0 && cropWidth < resource.width) {
                            val bitmap = compressWidthAndHeightForBitmap(
                                resource,
                                if (cropWidth == 0) this@CropImageView.width else cropWidth,
                                if (height == 0) this@CropImageView.height else height
                            )
                            setImageCropWidthBitmap(
                                bitmap,
                                if (cropWidth > bitmap.width) bitmap.width else if (cropWidth > minCropDistance) cropWidth else minCropDistance
                            )
                        } else {
                            setImageBitmap(resource)
                        }
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }
    }

    /**
     * 按图片适应宽度等比压缩
     */
    private fun compressWidthAndHeightForBitmap(
        originalBitmap: Bitmap,
        width: Int,
        height: Int
    ): Bitmap {
        // 计算比例
        val scaleX: Float = width.toFloat() / originalBitmap.width // 宽的比例
        val scaleY: Float = height.toFloat() / originalBitmap.height // 高的比例
        //新的宽高
        var newWidth = 0
        var newHeight = 0
        if (scaleX > scaleY) {
            newWidth = ((originalBitmap.width * scaleX).toInt())
            newHeight = ((originalBitmap.height * scaleX).toInt())
        } else if (scaleX <= scaleY) {
            newWidth = ((originalBitmap.width * scaleY).toInt())
            newHeight = ((originalBitmap.height * scaleY).toInt())
        }
        return Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true)
    }

    /**
     * 设置裁剪图片
     */
    fun setImageCropHeightBitmap(originalBitmap: Bitmap, cropHeight: Int = this.height) {
        var bitmap: Bitmap? = null
        val cropDistance =
            if (cropHeight > originalBitmap.height) originalBitmap.height else cropHeight
        when (cropGravityType) {
            CROP_GRAVITY_TOP -> {
                bitmap = Bitmap.createBitmap(
                    originalBitmap,//原图
                    0,//图片裁剪横坐标开始位置
                    0,//图片裁剪纵坐标开始位置
                    originalBitmap.width,//要裁剪的宽度
                    cropDistance//要裁剪的高度
                )
            }
            CROP_GRAVITY_CENTER_Y -> {
                bitmap = Bitmap.createBitmap(
                    originalBitmap,//原图
                    0,//图片裁剪横坐标开始位置
                    originalBitmap.height / 2 - cropDistance / 2,//图片裁剪纵坐标开始位置
                    originalBitmap.width,//要裁剪的宽度
                    cropDistance//要裁剪的高度
                )
            }
            CROP_GRAVITY_BOTTOM -> {
                bitmap = Bitmap.createBitmap(
                    originalBitmap,//原图
                    0,//图片裁剪横坐标开始位置
                    originalBitmap.height - cropDistance,//图片裁剪纵坐标开始位置
                    originalBitmap.width,//要裁剪的宽度
                    cropDistance//要裁剪的高度
                )
            }
        }
        if (bitmap == null) {
            setImageBitmap(originalBitmap)
            return
        }
        when (shapeType) {
            SHAPE_CIRCLE -> {
                setImageDrawable(CircleImageDrawable(resources, bitmap))
            }
            SHAPE_ROUND -> {
                setImageDrawable(RoundImageDrawable(resources, bitmap, roundRadius))
            }
            SHAPE_DEFAULT -> {
                setImageBitmap(bitmap)
            }
            else -> {
                setImageBitmap(bitmap)
            }
        }
    }

    /**
     * 设置裁剪图片
     */
    fun setImageCropWidthBitmap(originalBitmap: Bitmap, cropWidth: Int = this.width) {
        var bitmap: Bitmap? = null
        val cropDistance = if (cropWidth > originalBitmap.width) originalBitmap.width else cropWidth
        when (cropGravityType) {
            CROP_GRAVITY_START -> {
                bitmap = Bitmap.createBitmap(
                    originalBitmap,//原图
                    0,//图片裁剪横坐标开始位置
                    0,//图片裁剪纵坐标开始位置
                    cropDistance,//要裁剪的宽度
                    originalBitmap.height//要裁剪的高度
                )
            }
            CROP_GRAVITY_CENTER_X -> {
                bitmap = Bitmap.createBitmap(
                    originalBitmap,//原图
                    originalBitmap.width / 2 - cropDistance / 2,//图片裁剪横坐标开始位置
                    0,//图片裁剪纵坐标开始位置
                    cropDistance,//要裁剪的宽度
                    originalBitmap.height//要裁剪的高度
                )
            }
            CROP_GRAVITY_END -> {
                bitmap = Bitmap.createBitmap(
                    originalBitmap,//原图
                    originalBitmap.width - cropDistance,//图片裁剪横坐标开始位置
                    0,//图片裁剪纵坐标开始位置
                    cropDistance,//要裁剪的宽度
                    originalBitmap.height//要裁剪的高度
                )
            }
        }
        if (bitmap == null) {
            setImageBitmap(originalBitmap)
            return
        }
        when (shapeType) {
            SHAPE_CIRCLE -> {
                setImageDrawable(CircleImageDrawable(resources, bitmap))
            }
            SHAPE_ROUND -> {
                setImageDrawable(RoundImageDrawable(resources, bitmap, roundRadius))
            }
            SHAPE_DEFAULT -> {
                setImageBitmap(bitmap)
            }
            else -> {
                setImageBitmap(bitmap)
            }
        }
    }

    fun dp2px(dpValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}