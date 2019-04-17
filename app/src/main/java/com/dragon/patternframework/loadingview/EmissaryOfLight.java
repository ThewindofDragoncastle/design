package com.dragon.patternframework.loadingview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by 40774 on 2017/12/18.
 */

public class EmissaryOfLight extends View implements Animator.AnimatorListener {
    private final String TAG = getClass().getName();
    private int width = 0;
    private int height = 0;
    private Path mBig_Circle;
    private int BIG_CIRCLE_RADIUS = 50;
    private Path mLittle_Circle;
    //    画笔
    private Paint paint;
    private int LITTLE_CIRCLE_RADIUS = BIG_CIRCLE_RADIUS / 5 * 4;
    //    六角星 两个三角形
    private Path mHexagon1;
    private Path mHexagon2;
    private PathMeasure measure;
    //    中间路径
    private Path Big_circle_path;
    //    自我定义属性动画的值
    private float animatorValue = 0;
    //    动画
    private ValueAnimator animator;
    //    当前状态
    private State CurrentState = State.CIRCLE;
    //    画笔颜色
    private int color = Color.parseColor("#8A2BE2");

    //    枚举
    private enum State {
        CIRCLE, TRIANGLE1, TRIANGLE2;
    }

    public EmissaryOfLight(Context context) {
        super(context);
        initAll();
    }

    public EmissaryOfLight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAll();
    }

    public EmissaryOfLight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAll();
    }

    public EmissaryOfLight(Context context, int change) {
        super(context);
        ChangeSize(change);
        initAll();
    }

    public void initAll() {
        initPath();
        initAnimator();
    }

    public void initPath() {
        measure = new PathMeasure();
        Big_circle_path = new Path();
        paint = new Paint();
        paint.setStrokeWidth((float) 3.0);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
//        抗锯齿
        paint.setAntiAlias(true);
        mBig_Circle = new Path();
        mHexagon1 = new Path();
        mHexagon2 = new Path();
        mLittle_Circle = new Path();
//        圆
        RectF BiG_rectF = new RectF(-BIG_CIRCLE_RADIUS, -BIG_CIRCLE_RADIUS, BIG_CIRCLE_RADIUS, BIG_CIRCLE_RADIUS);
//        只能代表弧线的起始位置和结束位置
        mBig_Circle.addArc(BiG_rectF, 150, 359.9f);
        RectF Little_rectF = new RectF(-LITTLE_CIRCLE_RADIUS, -LITTLE_CIRCLE_RADIUS, LITTLE_CIRCLE_RADIUS, LITTLE_CIRCLE_RADIUS);
        mLittle_Circle.addArc(Little_rectF, 60, 359.9f);
        Point pointA = new Point();
        pointA.set(0, LITTLE_CIRCLE_RADIUS);
        Point pointB = new Point();
        pointB.set((int) (Math.sqrt(3) * LITTLE_CIRCLE_RADIUS / 2), LITTLE_CIRCLE_RADIUS / 2);
        Point pointC = new Point();
        pointC.set((int) (Math.sqrt(3) * LITTLE_CIRCLE_RADIUS / 2), -LITTLE_CIRCLE_RADIUS / 2);
        Point pointD = new Point();
        pointD.set(0, -LITTLE_CIRCLE_RADIUS);
        Point pointE = new Point();
        pointE.set(-(int) (Math.sqrt(3) * LITTLE_CIRCLE_RADIUS / 2), -LITTLE_CIRCLE_RADIUS / 2);
        Point pointF = new Point();
        pointF.set(-(int) (Math.sqrt(3) * LITTLE_CIRCLE_RADIUS / 2), LITTLE_CIRCLE_RADIUS / 2);
        Log.i(TAG, "大圆半径：" + BIG_CIRCLE_RADIUS + " 小圆半径：" + LITTLE_CIRCLE_RADIUS);
        mHexagon1.moveTo(pointE.x, pointE.y);
        mHexagon1.lineTo(pointC.x, pointC.y);
        mHexagon1.lineTo(pointA.x, pointA.y);
        mHexagon1.close();
        mHexagon2.rMoveTo(pointB.x, pointB.y);
        mHexagon2.lineTo(pointF.x, pointF.y);
        mHexagon2.lineTo(pointD.x, pointD.y);
        mHexagon2.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        Big_circle_path.reset();
//        第二个参数相当于画的长度 第一个开始画的位置
        switch (CurrentState) {
            case TRIANGLE1:
                canvas.drawPath(mLittle_Circle, paint);
                canvas.drawPath(mBig_Circle, paint);
                measure.setPath(mHexagon1, false);
                measure.getSegment(measure.getLength() - measure.getLength() * animatorValue, measure.getLength() - measure.getLength() * animatorValue + measure.getLength() / 20, Big_circle_path
                        , true);
                canvas.drawPath(Big_circle_path, paint);
                Big_circle_path.reset();
                measure.setPath(mHexagon2, false);
                measure.getSegment(measure.getLength() - measure.getLength() * animatorValue, measure.getLength() - measure.getLength() * animatorValue + measure.getLength() / 20, Big_circle_path
                        , true);
                canvas.drawPath(Big_circle_path, paint);
                break;
            case CIRCLE:
                //        将大圆路径和评估关联
                measure.setPath(mBig_Circle, false);
                measure.getSegment(measure.getLength() - measure.getLength() * animatorValue, measure.getLength(), Big_circle_path, true);
                canvas.drawPath(Big_circle_path, paint);
                Big_circle_path.reset();
                measure.setPath(mLittle_Circle, false);
                measure.getSegment(measure.getLength() - measure.getLength() * animatorValue, measure.getLength(), Big_circle_path, true);
                canvas.drawPath(Big_circle_path, paint);
                break;
            case TRIANGLE2:
                canvas.drawPath(mLittle_Circle, paint);
                canvas.drawPath(mBig_Circle, paint);
                measure.setPath(mHexagon1, false);
                measure.getSegment(measure.getLength() - measure.getLength() * animatorValue, measure.getLength(), Big_circle_path
                        , true);
                canvas.drawPath(Big_circle_path, paint);
                Big_circle_path.reset();
                measure.setPath(mHexagon2, false);
                measure.getSegment(measure.getLength() - measure.getLength() * animatorValue, measure.getLength(), Big_circle_path
                        , true);
                canvas.drawPath(Big_circle_path, paint);
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private void initAnimator() {
        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(3000);
        animator.start();
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatorValue = (float) valueAnimator.getAnimatedValue();
                Log.i(TAG, "光能使者：" + animatorValue);
                invalidate();
            }
        });
        animator.addListener(this);
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {
        switch (CurrentState) {
            case TRIANGLE1:
                CurrentState = State.TRIANGLE2;
                break;
            case TRIANGLE2:
                CurrentState = State.CIRCLE;
                break;
            case CIRCLE:
                CurrentState = State.TRIANGLE1;
                break;
        }
    }

    public void initClick() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 19) {
                    if (animator.isPaused())
                        animator.resume();
                    else if (animator.isStarted())
                        animator.pause();
                } else
                    Toast.makeText(getContext(), "你的手机版本过低！暂时不能暂停", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ChangeSize(int size) {
        BIG_CIRCLE_RADIUS = size;
        color = Color.WHITE;
        LITTLE_CIRCLE_RADIUS = BIG_CIRCLE_RADIUS / 5 * 4;
        setMeasuredDimension(size + 50, size + 50);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.cancel();
    }
}
