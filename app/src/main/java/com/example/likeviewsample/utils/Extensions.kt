package com.example.likeviewsample.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * 扩展函数
 * Created by LLhon
 */

val Float.dp
  get() = TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP,
      this,
      Resources.getSystem().displayMetrics
  )

val Int.dp
  get() = this.toFloat().dp