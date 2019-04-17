package com.dragon.patternframework.loadingview

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


/**
 * Created by 40774 on 2017/12/20.
 */

open class CircleView/*跳动的五个圆 感觉有点浪费资源。。*/
(context: Context, attrs: AttributeSet?) : View(context, attrs), Animator.AnimatorListener {

    private var state: State = State.FIRST
    private var isReserve = false
    private var h: Int = 0
    private var w: Int = 0
    /*小球坐标 切线斜率*/
    private val mainPos = FloatArray(10)
    private val mainTan = FloatArray(10)
    private val otherPos = FloatArray(10)
    private val otherTan = FloatArray(10)
    /*动画*/
    lateinit var value: ValueAnimator
    private var animatorValue = 0f
    /*绘制五个圆的路径 如果以中点计算共有6个点*/
    private val A = Point(-100, 0)
    private val B = Point()
    private val C = Point()
    private val D = Point()
    private val E = Point()
    private val F = Point(100, 0)
    private val circleA = Path()
    private val circleB = Path()
    private val circleC = Path()
    private val circleD = Path()
    private val circleE = Path()
    private val mainCircle = Path()
    private val otherCircle = Path()
    private val mainMeasure: PathMeasure = PathMeasure()
    private val otherMeasure: PathMeasure = PathMeasure()
    val array: Array<RectF>
    /*两个不需要画的球*/
    private var ballA = -1
    private var ballB = -1
    private val blackBall = Paint()
    private val whiteBall = Paint()

    init {
        val gap = (F.x - A.x) / 5
        B.set(A.x + gap, A.y)
        C.set(A.x + 2 * gap, A.y)
        D.set(A.x + 3 * gap, A.y)
        E.set(A.x + 4 * gap, A.y)
        val rectA = RectF(A.x.toFloat(), (A.y - gap / 2).toFloat(), B.x.toFloat(), (B.y + gap / 2).toFloat())
        circleA.addOval(rectA, Path.Direction.CCW)
        val rectB = RectF(B.x.toFloat(), (A.y - gap / 2).toFloat(), C.x.toFloat(), (B.y + gap / 2).toFloat())
        circleB.addOval(rectB, Path.Direction.CCW)
        val rectC = RectF(C.x.toFloat(), (A.y - gap / 2).toFloat(), D.x.toFloat(), (B.y + gap / 2).toFloat())
        circleC.addOval(rectC, Path.Direction.CCW)
        val rectD = RectF(D.x.toFloat(), (A.y - gap / 2).toFloat(), E.x.toFloat(), (B.y + gap / 2).toFloat())
        circleD.addOval(rectD, Path.Direction.CCW)
        val rectE = RectF(E.x.toFloat(), (A.y - gap / 2).toFloat(), F.x.toFloat(), (B.y + gap / 2).toFloat())
        circleE.addOval(rectE, Path.Direction.CCW)

        blackBall.color = Color.parseColor("#A6A6A6")
        blackBall.isAntiAlias = true
        blackBall.strokeWidth = 1f
        blackBall.style = Paint.Style.FILL_AND_STROKE
        whiteBall.color = Color.BLACK
        whiteBall.isAntiAlias = true
        whiteBall.strokeWidth = 1f
        whiteBall.style = Paint.Style.FILL_AND_STROKE
        array = arrayOf(rect(A), rect(B), rect(C), rect(D), rect(E), rect(F))
        animator()
    }

    private fun animator() {
        value = ValueAnimator.ofFloat(0f, 1f)
        value.duration = 500
        value.start()
        value.repeatCount = ValueAnimator.INFINITE
        value.repeatMode = ValueAnimator.RESTART
        value.addUpdateListener {
            animatorValue = it.animatedValue as Float
            Log.d("TAG:", "属性为： $animatorValue")
            invalidate()
        }
        value.addListener(this)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        otherCircle.reset()
        mainCircle.reset()

        canvas?.run {
            translate(w / 2.toFloat(), h / 2.toFloat())
            /*路径显示*/
//            drawPath(circleA,blackBall)
//            drawPath(circleB,blackBall)
//            drawPath(circleC,blackBall)
//            drawPath(circleD,blackBall)
//            drawPath(circleE,blackBall)

            val currentPath = when (state) {
                State.FIRST -> /*动画 */circleA
                State.SECOND -> circleB
                State.THIRD -> circleC
                State.FOURTH -> circleD
                State.FIFTH -> circleE
            }
            /*动画 */
            mainMeasure.setPath(currentPath, false)
            /*为主小球的下一个球 */
            otherMeasure.setPath(currentPath, false)
            if (!isReserve) rightRun() else leftRun()
            /*不画当前球 每一个圆形路径 两个球不画*/
            filter(currentPath)
            (0..5).filter { it -> ballA != it && ballB != it }
                    .forEach { it -> drawOval(array[it], blackBall) }
            Log.i("小球坐标设置：", " x=${mainPos[0]} y=${mainPos[1]} ")
            /*主要的小球*/
            /*用判断语句防止小球颜色闪动
            * 小球闪动原因：因为一小节动画完结时回到了起始位置
            * 解决方案：当动画完结时将其颜色回置*/
            if (mainPos[1] != 0.toFloat())
                drawCircle(mainPos[0], mainPos[1], 10f, whiteBall)
            else
                drawCircle(mainPos[0], mainPos[1], 10f, blackBall)
            /*其余小球表现为*/
            drawCircle(otherPos[0], otherPos[1], 10f, blackBall)
        }
    }

    private fun rect(p: Point): RectF {
        return RectF((p.x - 10).toFloat(), (p.y - 10).toFloat(), (p.x + 10).toFloat(), (p.y + 10).toFloat())
    }

    private fun filter(currentPath: Path) {
        ballA = when (currentPath) {
            circleA -> 0
            circleB -> 1
            circleC -> 2
            circleD -> 3
            circleE -> 4
            else -> 0
        }
        ballB = ballA + 1
    }

    private fun leftRun() {
        mainMeasure.getSegment(mainMeasure.length - 10 - mainMeasure.length / 2 * animatorValue
                , mainMeasure.length - mainMeasure.length / 2 * animatorValue, mainCircle, false)
        mainMeasure.getPosTan(mainMeasure.length - mainMeasure.length / 2 * animatorValue, mainPos, mainTan)

        otherMeasure.getSegment(mainMeasure.length / 2 - 10 - mainMeasure.length / 2 * animatorValue
                , mainMeasure.length / 2 - mainMeasure.length / 2 * animatorValue, otherCircle, false)
        otherMeasure.getPosTan(mainMeasure.length / 2 - mainMeasure.length / 2 * animatorValue, otherPos, otherTan)
    }

    private fun rightRun() {
        mainMeasure.getSegment(mainMeasure.length / 2 - 10 - mainMeasure.length / 2 * animatorValue
                , mainMeasure.length / 2 - mainMeasure.length / 2 * animatorValue, mainCircle, false)
        mainMeasure.getPosTan(mainMeasure.length / 2 - mainMeasure.length / 2 * animatorValue, mainPos, mainTan)

        otherMeasure.getSegment(mainMeasure.length - 10 - mainMeasure.length / 2 * animatorValue
                , mainMeasure.length - mainMeasure.length / 2 * animatorValue, otherCircle, false)
        otherMeasure.getPosTan(mainMeasure.length - mainMeasure.length / 2 * animatorValue, otherPos, otherTan)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.h = h
        this.w = w
    }

    enum class State { FIRST, SECOND, THIRD, FOURTH, FIFTH }

    override fun onAnimationEnd(p0: Animator?) {

    }

    override fun onAnimationStart(p0: Animator?) {

    }

    override fun onAnimationRepeat(p0: Animator?) {
        if (!isReserve)
            state = when (state) {
                State.FIRST -> State.SECOND
                State.SECOND -> State.THIRD
                State.THIRD -> State.FOURTH
                State.FOURTH -> State.FIFTH
                State.FIFTH -> {
                    /*因为到结尾时要旋转一个整圆*/
                    isReserve = !isReserve
                    state
                }
            }
        else {
            state = when (state) {
                State.FIRST -> {
                    isReserve = !isReserve
                    state
                }
                State.SECOND -> State.FIRST
                State.THIRD -> State.SECOND
                State.FOURTH -> State.THIRD
                State.FIFTH -> State.FOURTH
            }
        }
    }

    override fun onAnimationCancel(p0: Animator?) {

    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == View.GONE)
            value.cancel()
    }
}
