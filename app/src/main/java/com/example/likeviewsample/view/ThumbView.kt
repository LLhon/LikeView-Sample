package com.example.likeviewsample.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.OvershootInterpolator
import androidx.core.animation.addListener
import com.example.likeviewsample.R

private const val SCALE_MIN = 0.9f
private const val SCALE_MAX = 1f

/**
 *
 * Created by LLhon on 2021/4/26 16:33.
 */
class ThumbView(context: Context, attrs: AttributeSet?) : View(context, attrs), OnClickListener {

  private var mSelectedThumb: Bitmap
  private var mUnSelectedThumb: Bitmap
  private var mPointThumb: Bitmap
  private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
  private var mIsLiked = false
  private var mThumbWidth: Float
  private var mThumbHeight: Float
  private val mPath = Path()
  var curThumbScale = SCALE_MAX
    set(value) {
      field = value
      invalidate()
    }
  private val mAnimator = ObjectAnimator.ofFloat(this, "curThumbScale", SCALE_MAX, SCALE_MIN)

  init {
    mSelectedThumb = BitmapFactory.decodeResource(resources, R.drawable.ic_like_selected)
    mUnSelectedThumb = BitmapFactory.decodeResource(resources, R.drawable.ic_like_unselected)
    mPointThumb = BitmapFactory.decodeResource(resources, R.drawable.ic_like_selected_point)

    mThumbWidth = mSelectedThumb.width.toFloat()
    mThumbHeight = mSelectedThumb.height.toFloat()

    mPath.addCircle(mThumbWidth, mThumbHeight, mThumbHeight, Path.Direction.CW)

    setOnClickListener(this)

  }

  override fun onDraw(canvas: Canvas) {
    if (mIsLiked) {
      canvas.save()
      canvas.scale(curThumbScale, curThumbScale)
      canvas.drawBitmap(mSelectedThumb, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
//      canvas.drawBitmap(mPointThumb, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
      canvas.restore()
    } else {
      canvas.save()
      canvas.scale(curThumbScale, curThumbScale)
      canvas.drawBitmap(mUnSelectedThumb, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
      canvas.restore()
    }

  }

  override fun onClick(v: View) {
    if (!mIsLiked) {
      mAnimator.interpolator = OvershootInterpolator()
      mAnimator.start()
      mAnimator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationEnd(animation: Animator?) {
          mIsLiked = !mIsLiked
          mAnimator.reverse()
          mAnimator.removeAllListeners()
        }

        override fun onAnimationStart(animation: Animator?) {

        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationRepeat(animation: Animator?) {

        }
      })
    } else {
      mIsLiked = !mIsLiked
      postInvalidate()
    }
  }
}