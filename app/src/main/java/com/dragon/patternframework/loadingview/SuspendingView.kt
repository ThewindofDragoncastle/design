package com.dragon.patternframework.loadingview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View


/**
 * Created by 40774 on 2017/12/20.
 */

class SuspendingView/*我想做一个悬挂的动画 代表有新的回复*/
(context: Context) : View(context) {
    private var he: Int = 0
    private var w: Int = 0
    private var time = "2017-11-12"
    private var text = "求　回　复"

    constructor(context: Context, attrs: AttributeSet?) : this(context) {
/*这里是系统xml添加*/
    }

    init {
        timeFormat(time)
    }

    constructor(context: Context, text: String, time: String) : this(context) {
//        文本三个字吧
        //输入格式 2018-1-26
        text.replace("", "\u3000")
        timeFormat(time)
        this.text = text.replace("", "\u3000")
    }

    private fun timeFormat(time: String) {
        this.time = "\u3000" + time.split("-")[0].replace("", " ") + " - " +
                time.split("-")[1] + " - " +
                time.split("-")[2].replace("", " ") + "\u3000"
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context) {

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.translate(w / 2f, he / 2f)
        val metrics = context.resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        Log.d("分辨率：", "宽度：$width 高度:$height")

/*左右侧线*/
        val pa = TextPaint()
        pa.textSize = 16f
        pa.color = Color.RED
        pa.strokeWidth = 1f
        pa.style = Paint.Style.STROKE
        pa.isAntiAlias = true

        val ovalPaint = Paint()
        ovalPaint.color = Color.RED
        ovalPaint.strokeWidth = 3f
        ovalPaint.isAntiAlias = true
        ovalPaint.style = Paint.Style.STROKE

        val ovalPath = Path()
        /*根据分辨率画一个图章*/
        val rf =
                if (width < 1080)
                    RectF(-40f, -40f, 40f, 40f)
                else {
                    pa.textSize = 20f
                    RectF(-40f, -40f, 80f, 80f)
                }
        ovalPath.arcTo(rf, 0f, 359.9f)
        canvas?.run {
            drawPath(ovalPath, ovalPaint)
            //参数一为文字水平方向的偏移量
            //参数二为文字垂直方向的偏移量
            drawTextOnPath("$time$text", ovalPath, 0f, 22f, pa)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.he = h
        this.w = w
    }
}
