package com.ysnows.bezier;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private int screenWidthPixels;
    private int slice;
    private View root;
    private GestureDetector gestureDetector;
    private float downX;
    private float downY;
    private BezierView bezier;
    private float v;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureDetector = new GestureDetector(this, this);
        root = findViewById(R.id.root);
        bezier = findViewById(R.id.bezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP) {
            final int nowHeight = bezier.height;
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1F, 0F);
            valueAnimator.setDuration(300);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object animatedValue = animation.getAnimatedValue();
                    Float value = (Float) animatedValue;
                    bezier.height = (int) (nowHeight * value);
                    if (BuildConfig.DEBUG)
                        Log.d(this.getClass().getName(), "bezier.height:" + bezier.height);
                    bezier.postInvalidate();
                    v = 0;
                }
            });
            valueAnimator.start();


            if (BuildConfig.DEBUG) Log.d(this.getClass().getName(), "up:" + action);

            float x = event.getX();
            float y = event.getY();

            float distanceX = x - downX;
            float distanceY = y - downY;

            Log.d(this.getClass().getName(), "onUp:" + distanceX + "  Y:" + distanceY + "  time:" + System.currentTimeMillis());


            float absX = Math.abs(distanceX);
            float absY = Math.abs(distanceY);


            if (Math.max(absX, absY) < 40) {
                return false;
            }

            String userAction = null;

            if (absX > absY) {
                //左右滑动
                if (distanceX < 0) {
                    //往左滑动
                } else {
                    //往右滑动
                }

            } else {
                //上下滑动

                if (distanceY < 0) {
                    //往上滑动
                } else {
                    //往下滑动

                }
            }


            return super.onTouchEvent(event);

        } else {
            return gestureDetector.onTouchEvent(event);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (BuildConfig.DEBUG) Log.d(this.getClass().getName(), distanceY + "Y");
        if (distanceY < 0) {//下滑
            v += Math.abs(distanceY);
            if (v > 255) {
                bezier.height = 100;
            } else {
                bezier.height = (int) ((v / 255) * 100);
            }

            bezier.invalidate();

        } else if (distanceY > 0) {
            v -= Math.abs(distanceY);
            if (v > 255) {
                bezier.height = 100;
            } else {
                bezier.height = (int) ((v / 255) * 100);
            }

            bezier.invalidate();
        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}

