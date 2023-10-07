package com.zyt.widgets.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.zyt.widgets.views.protocol.SlidingComponent
import com.zyt.widgets.views.protocol.SlidingComponentManager
import com.zyt.widgets.utils.ScreenUtil.getScreenWidth
import com.zyt.widgets.utils.UiUtils
import kotlin.math.abs
import kotlin.properties.Delegates

class SlidingMenu(context: Context, attrs: AttributeSet? = null) : HorizontalScrollView(context),
    SlidingComponent {

    private var mScreenWidth = 0
    private var mMenuWidth = 0
    private val radio = 0.25f

    private var isOnce = true
    override var isOpened = false

    private var downTime by Delegates.notNull<Long>()
    private var mCustomOnClickListener: (() -> Unit)? = null

    lateinit var slidingComponentManager: SlidingComponentManager

    init {
        // 减去外边距 marginHorizontal; 左右两边5dp
        mScreenWidth = getScreenWidth(context) - UiUtils.dp2px(context, 10f)
        mMenuWidth = (mScreenWidth * radio).toInt()
        overScrollMode = OVER_SCROLL_NEVER
        isHorizontalScrollBarEnabled = false
    }

    fun setContentClickListener(listener: () -> Unit) {
        mCustomOnClickListener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isOnce) {
            val wrapper = getChildAt(0) as LinearLayout
            wrapper.getChildAt(0).layoutParams.width = mScreenWidth
            wrapper.getChildAt(1).layoutParams.width = mMenuWidth
            isOnce = false
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downTime = System.currentTimeMillis()
                slidingComponentManager.closeOpenedComponent()
                slidingComponentManager.setOpenedComponent(this)
            }

            MotionEvent.ACTION_UP -> {
                if (System.currentTimeMillis() - downTime <= 100 && scrollX == 0) {
                    performClick()
                }

                if (abs(scrollX) > mMenuWidth / 2) {
                    smoothScrollTo(mMenuWidth, 0)
                    slidingComponentManager.setOpenedComponent(this)
                    isOpened = true
                } else {
                    smoothScrollTo(0, 0)
                }
                return true
            }
        }
        return super.onTouchEvent(ev)
    }

    override fun performClick(): Boolean {
        if (mCustomOnClickListener != null) {
            mCustomOnClickListener?.invoke()
            return true
        }
        return super.performClick()
    }

    override fun close() {
        if (isOpened) {
            smoothScrollTo(0, 0)
            isOpened = false
        }
    }

}

