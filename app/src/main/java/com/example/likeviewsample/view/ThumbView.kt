package com.example.likeviewsample.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.likeviewsample.R

/**
 *
 * Created by LLhon on 2021/4/26 16:33.
 */
class ThumbView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

  private var mSelectedThumb: Bitmap
  private var mUnSelectedThumb: Bitmap
  private var mPointThumb: Bitmap
  private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
  private var mIsLiked = false

  init {
    mSelectedThumb = BitmapFactory.decodeResource(resources, R.drawable.ic_like_selected)
    mUnSelectedThumb = BitmapFactory.decodeResource(resources, R.drawable.ic_like_unselected)
    mPointThumb = BitmapFactory.decodeResource(resources, R.drawable.ic_like_selected_point)
  }

  override fun onDraw(canvas: Canvas) {
    if (mIsLiked) {
      canvas.drawBitmap(mSelectedThumb, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
      canvas.drawBitmap(mPointThumb, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
    } else {
      canvas.drawBitmap(mUnSelectedThumb, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
    }

  }
}