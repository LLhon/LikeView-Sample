package com.example.likeviewsample.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.example.likeviewsample.utils.dp

private val TEXT_SIZE = 20.dp
private val TEXT_MARGIN = 5.dp

/**
 *
 * Created by LLhon on 2021/4/25 15:19.
 */
class CountView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var mCount = 99
    private var mOldCount = 0
    private var mIsLiked = false
    var curCountFraction = 1f
        set(value) {
            field = value
            invalidate()
        }
    var curOldCountFraction = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val mTextArray = arrayListOf("", "", "")

    private val mGestureListener = GestureListener()
    private val mGestureDetector = GestureDetectorCompat(context, mGestureListener)

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = TEXT_SIZE

    }

    private val mCountAnimator = ObjectAnimator.ofFloat(this, "curCountFraction", 1f, 0f)
    private val mOldCountAnimator = ObjectAnimator.ofFloat(this, "curOldCountFraction", 0f, 1f)

    init {
        mCountAnimator.duration = 500
        mOldCountAnimator.duration = 500
        mTextArray[1] = mCount.toString()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val contentWidth = (mPaint.measureText(mCount.toString()) + TEXT_SIZE).toInt()
        val contentHeight = (TEXT_SIZE + TEXT_MARGIN).toInt()
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val width = when(widthSpecMode) {
            MeasureSpec.EXACTLY -> contentWidth.coerceAtLeast(widthSpecSize)
            MeasureSpec.AT_MOST -> contentWidth
            else -> contentWidth
        }
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        val height = when(heightSpecMode) {
            MeasureSpec.EXACTLY -> contentHeight.coerceAtLeast(heightSpecSize)
            MeasureSpec.AT_MOST -> contentHeight
            else -> contentHeight
        }
//        val width = resolveSize(contentWidth, widthMeasureSpec)
//        val height = resolveSize(contentHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        var countOffsetY = 0f
        var oldCountOffsetY = 0f
        if (mIsLiked) {
            countOffsetY = -mPaint.fontMetrics.top+ curCountFraction * mPaint.fontSpacing
            oldCountOffsetY = -mPaint.fontMetrics.top+ curOldCountFraction * -mPaint.fontSpacing
        } else {

            countOffsetY = -mPaint.fontMetrics.top+ curCountFraction * -mPaint.fontSpacing
            oldCountOffsetY = -mPaint.fontMetrics.top+ curOldCountFraction * mPaint.fontSpacing
        }
        //100 -> 101
        //把数字拆分为三个部分进行绘制
        //绘制数字不变的部分 10
        canvas.drawText(mTextArray[0], 0f, -mPaint.fontMetrics.top, mPaint)
        val offsetX = mPaint.measureText(mTextArray[0])
        //绘制数字变化前的部分 0
        canvas.drawText(mTextArray[1], offsetX, oldCountOffsetY, mPaint)
        //绘制数字变化后的部分 1
        canvas.drawText(mTextArray[2], offsetX, countOffsetY, mPaint)

    }

    private fun calculateText() {
        //oldCount=0  count=1
        //oldCount=99  count=100
        //oldCount=100  count=101
        for ((index, oldCountChar) in mOldCount.toString().withIndex()) {
            val countChar = mCount.toString()[index]
            if (oldCountChar != countChar) {
                mTextArray[0] = if (index == 0) "" else mCount.toString().substring(0, index)
                mTextArray[1] = mOldCount.toString().substring(index)
                mTextArray[2] = mCount.toString().substring(index)
                break
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mGestureDetector.onTouchEvent(event)
        return true
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            mIsLiked = !mIsLiked
            mOldCount = mCount
            if (mIsLiked) {
                mCount++
            } else {
                mCount--
            }
            calculateText()
            mCountAnimator.start()
            mOldCountAnimator.start()
            return true
        }
    }
}