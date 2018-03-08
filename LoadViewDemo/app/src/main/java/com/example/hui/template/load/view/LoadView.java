package com.example.hui.template.load.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.example.hui.template.R;

/**
 * description：
 * <p/>
 * Created by 曾辉 on 2016/10/14.
 * QQ：240336124
 * Email: 240336124@qq.com
 * Version：1.0
 */
public class LoadView extends LinearLayout{

    private ShapeView mShapeView;
    private View mShadeIndicatorView;

    private Context mContext;

    public final static long DURATION_TIME = 500;

    public LoadView(Context context) {
        this(context, null);
    }

    public LoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        // 初始化 View 加载布局
        inflate(context, R.layout.ui_load_view,this);

        // 开启下落动画   位移动画  缩放动画
        mShapeView = (ShapeView) findViewById(R.id.shape_view);
        mShadeIndicatorView = findViewById(R.id.shade_indicator_iv);

        startFallAnimation();
    }

    /**
     * 启动下落的动画
     */
    private void startFallAnimation() {
        //  80dp  球的速度应该越来越快
        ObjectAnimator  translateAnimator = ObjectAnimator.ofFloat(mShapeView,
                "translationY",0,dip2px(mContext,80));
        // setInterpolator 设置差值器   速度越来越快   贝塞尔曲线
        translateAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadeIndicatorView,
                "scaleX", 1.0f,0.3f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION_TIME);
        // 两个动画一起执行
        animatorSet.playTogether(translateAnimator, scaleAnimator);
        animatorSet.start();

        // 等下落执行完之后上抛   监听动画执行
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 动画执行结束  上抛  改变ShapeView的形状
                startUpAnimation();

                // 改变ShapeView的形状
                mShapeView.exchangeShape();
            }
        });
    }

    /**
     * 启动上抛动画
     */
    private void startUpAnimation() {
        ObjectAnimator  translateAnimator = ObjectAnimator.ofFloat(mShapeView,
                "translationY",dip2px(mContext,80),0);
        // DecelerateInterpolator 开始的时候快 然后慢
        translateAnimator.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadeIndicatorView,
                "scaleX", 0.3f,1.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION_TIME);
        // 两个动画一起执行
        animatorSet.playTogether(translateAnimator, scaleAnimator);
        animatorSet.start();

        // 等上抛执行完之后下落  监听动画执行
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 动画执行结束
                startFallAnimation();
            }
        });
    }


    public static int dip2px(Context ctx,float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 解决内存优化问题
     */
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

        if(visibility == GONE){
            // 隐藏   清除动画
            mShapeView.clearAnimation();
            mShadeIndicatorView.clearAnimation();

            // 删除这个View  用代码 删除
            ViewGroup parent = (ViewGroup) this.getParent();
            if(parent != null){
                parent.removeView(this);
            }
        }
    }
}
