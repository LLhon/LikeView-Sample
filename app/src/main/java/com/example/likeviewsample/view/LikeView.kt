package com.example.likeviewsample.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 *
 * Created by LLhon on 2021/4/29 16:11.
 */
class LikeView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

  private var mCountView: CountView
  private var mThumbView: ThumbView

    init {


      removeAllViews()
      clipChildren = false
      orientation = HORIZONTAL

      mThumbView = ThumbView(context, attrs)
      mCountView = CountView(context, attrs)
      addView(mThumbView)
      addView(mCountView)
    }
}