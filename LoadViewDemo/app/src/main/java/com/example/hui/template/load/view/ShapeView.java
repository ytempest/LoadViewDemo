package com.example.hui.template.load.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.hui.template.R;

/**
 * description：
 * <p/>
 * Created by 曾辉 on 2016/10/14.
 * QQ：240336124
 * Email: 240336124@qq.com
 * Version：1.0
 */
public class ShapeView extends View {

    private Paint mPaint;

    private Context mContext;

    private Shape mCurrentShape = Shape.Shape_Circle;// 当前的形状 默认是圆形

    private enum Shape{// 三种形状采用枚举表示
        Shape_Rect,Shape_Circle,Shape_Triangle;
    }


    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        // 画一个矩形看一下效果
        switch (mCurrentShape){
            case Shape_Circle:
                // 画圆
                mPaint.setColor(getColor(R.color.circle));
                canvas.drawCircle(width/2,height/2,width/2,mPaint);
                break;
            case Shape_Rect:
                // 画矩形
                mPaint.setColor(getColor(R.color.rect));
                canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
                break;
            case Shape_Triangle:
                // 画三角形
                mPaint.setColor(getColor(R.color.triangle));
                //定义一个Path对象,封闭成一个三角形
                Path path = new Path();
                path.moveTo(width/2, 0);// 移动到起点
                path.lineTo(0, (float) (Math.sqrt(3)/2*width));
                path.lineTo(width, (float) (Math.sqrt(3)/2*width));
                path.close();
                canvas.drawPath(path,mPaint);
                break;
        }
    }

    /**
     * 获取颜色值
     */
    private int getColor(int colorResId) {
        return ContextCompat.getColor(mContext, colorResId);
    }

    /**
     * 改变形状
     */
    public void exchangeShape() {
        // 形状是按循序改变的， 所以要知道当前的形状是什么
        switch (mCurrentShape){
            case Shape_Circle:
                mCurrentShape = Shape.Shape_Rect;
                break;
            case Shape_Rect:
                mCurrentShape = Shape.Shape_Triangle;
                break;
            case Shape_Triangle:
                mCurrentShape = Shape.Shape_Circle;
                break;
        }
        // 改变当前形状之后  一定要重新绘制  onDraw() 方法
        invalidate();

        // 还要开启旋转动画
        startRotateAnimation();
    }

    /**
     * 启动旋转动画
     */
    private void startRotateAnimation() {
        switch (mCurrentShape){
            case Shape_Rect:
                ObjectAnimator.ofFloat(this,"rotation",0,180).setDuration(LoadView.DURATION_TIME).start();
                break;
            case Shape_Triangle:
            case Shape_Circle:
                ObjectAnimator.ofFloat(this,"rotation",0,-120).setDuration(LoadView.DURATION_TIME).start();
                break;
        }

    }
}
