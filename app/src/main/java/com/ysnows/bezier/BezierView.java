package com.ysnows.bezier;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;


/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class BezierView extends View {

    public String position = "right";

    private Path path;
    private Paint paint;
    private int slice;
    public PointF center;
    public int maxHeight = 80;
    public int bgColor = Color.parseColor("#C3000000");
    private PointF pOne;
    private PointF pControlOne;
    private PointF pControlTwo;
    private PointF pControlThree;
    private PointF pControlFour;
    private PointF PTwo;
    private PointF PThree;
    private int screenWidthPixels;
    private float v;
    private ValueAnimator valueAnimator;

    public BezierView(Context context) {
        this(context, null);

    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        screenWidthPixels = UiUtils.getScreenWidthPixels();
//      maxHeight = UiUtils.dp2Px(getContext(), 35);
        path = new Path();
        paint = new Paint();
        paint.setColor(bgColor);
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        slice = screenWidthPixels / 10;
        setBackgroundColor(Color.TRANSPARENT);
        setVisibility(GONE);

        center = new PointF(0, 0);

        pOne = new PointF();
        pControlOne = new PointF();
        pControlTwo = new PointF();
        pControlThree = new PointF();
        pControlFour = new PointF();
        PTwo = new PointF();
        PThree = new PointF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();

        if ("top".equals(position)) {
            pOne.set(center.x - 3.2f * slice, 0);
            pControlOne.set(center.x - 1.4f * slice, 0.5f * center.y);
            pControlTwo.set(center.x - 0.8f * slice, center.y);
            PTwo.set(center.x, center.y);
            pControlThree.set(center.x + 0.8f * slice, center.y);
            pControlFour.set(center.x + 1.4f * slice, 0.5f * center.y);
            PThree.set(center.x + 3.2f * slice, 0);
        } else if ("bottom".equals(position)) {
            pOne.set(center.x - 3.2f * slice, getMeasuredHeight());
            pControlOne.set(center.x - 1.4f * slice, getMeasuredHeight() - 0.5f * center.y);
            pControlTwo.set(center.x - 0.8f * slice, getMeasuredHeight() - center.y);
            PTwo.set(center.x, getMeasuredHeight() - center.y);
            pControlThree.set(center.x + 0.8f * slice, getMeasuredHeight() - center.y);
            pControlFour.set(center.x + 1.4f * slice, getMeasuredHeight() - 0.5f * center.y);
            PThree.set(center.x + 3.2f * slice, getMeasuredHeight());
        } else if ("left".equals(position)) {
            pOne.set(0, center.y - 3.2f * slice);
            pControlOne.set(0.5f * center.x, center.y - 1.4f * slice);
            pControlTwo.set(center.x, center.y - 0.8f * slice);
            PTwo.set(center.x, center.y);
            pControlThree.set(center.x, center.y + 0.8f * slice);
            pControlFour.set(0.5f * center.x, center.y + 1.4f * slice);
            PThree.set(0, center.y + 3.2f * slice);
        } else if ("right".equals(position)) {
            pOne.set(screenWidthPixels, center.y - 3.2f * slice);
            pControlOne.set(screenWidthPixels - 0.5f * center.x, center.y - 1.4f * slice);
            pControlTwo.set(screenWidthPixels - center.x, center.y - 0.8f * slice);
            PTwo.set(screenWidthPixels - center.x, center.y);
            pControlThree.set(screenWidthPixels - center.x, center.y + 0.8f * slice);
            pControlFour.set(screenWidthPixels - 0.5f * center.x, center.y + 1.4f * slice);
            PThree.set(screenWidthPixels, center.y + 3.2f * slice);
        }

        path.moveTo(pOne.x, pOne.y);
        path.cubicTo(pControlOne.x, pControlOne.y, pControlTwo.x, pControlTwo.y, PTwo.x, PTwo.y);
        path.cubicTo(pControlThree.x, pControlThree.y, pControlFour.x, pControlFour.y, PThree.x, PThree.y);
        path.close();
        canvas.drawPath(path, paint);

//        path.moveTo(slice * 1.8f, 0);
//        float firstAnchor = 3.6f;
//        float sencondaryAnchor = 4.2f;
//        path.cubicTo(slice * firstAnchor, center.y * 0.5f, slice * sencondaryAnchor, center.y, slice * 5, center.y);
//        path.cubicTo(slice * 5.8f, center.y, slice * (10 - firstAnchor), center.y * 0.5f, slice * 8.2f, 0);
//        path.close();
//        canvas.drawPath(path, paint);
    }

    /**
     * 处理滑动手势，在 onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) 的时候调用
     *
     * @param distanceX
     * @param distanceY
     */
    public void handleScroll(float distanceX, float distanceY) {

        if (position.equals("top") || position.equals("bottom")) {//如果是在顶部或者底部，处理下滑||上滑的动作
            if (distanceY < 0) {//下滑
                if (position.equals("bottom")) {
                    this.v -= Math.abs(distanceY);
                } else if (position.equals("top")) {
                    this.v += Math.abs(distanceY);
                }

                if (this.v > 255) {
                    center.y = maxHeight;
                } else {
                    center.y = (int) ((this.v / 255) * maxHeight);
                }

            } else if (distanceY > 0) {
                if (position.equals("bottom")) {
                    this.v += Math.abs(distanceY);
                } else if (position.equals("top")) {
                    this.v -= Math.abs(distanceY);
                }

                if (this.v > 255) {
                    center.y = maxHeight;
                } else {
                    center.y = (int) ((this.v / 255) * maxHeight);
                }

            }
        }


        if (position.equals("left") || position.equals("right")) {//如果是在左侧或者右侧，处理左滑||右滑的动作
            if (distanceX < 0) {//右滑
                if (position.equals("left")) {
                    this.v += Math.abs(distanceX);
                } else if (position.equals("right")) {
                    this.v -= Math.abs(distanceX);
                }

                if (this.v > 255) {
                    center.x = maxHeight;
                } else {
                    center.x = (int) ((this.v / 255) * maxHeight);
                }

            } else if (distanceX > 0) {//左滑
                if (position.equals("right")) {
                    this.v += Math.abs(distanceX);
                } else if (position.equals("left")) {
                    this.v -= Math.abs(distanceX);
                }

                if (this.v > 255) {
                    center.x = maxHeight;
                } else {
                    center.x = (int) ((this.v / 255) * maxHeight);
                }
            }
        }
        postInvalidate();
    }


    /**
     * 设置手势的中点，在OnDown(MotionEvent e)的时候调用
     *
     * @param e
     */
    public void handleDown(MotionEvent e) {
        setVisibility(VISIBLE);
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }

        if (position.equals("top") || position.equals("bottom")) {
            center.x = e.getRawX();
        } else if (position.equals("left") || position.equals("right")) {
            center.y = e.getRawY();
        }
    }

    /**
     * 处理松手后的操作
     */
    public void handleActionUp() {
        final float nowHeight = center.y;
        final float nowWidth = center.x;
        valueAnimator = ValueAnimator.ofFloat(1F, 0F);
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                Float value = (Float) animatedValue;
                if (position.equals("top") || position.equals("bottom")) {
                    center.y = (int) (nowHeight * value);
                } else if (position.equals("left") || position.equals("right")) {
                    center.x = (int) (nowWidth * value);
                }
                postInvalidate();

                if (value == 0) {
                    setVisibility(GONE);
                    v = 0;
                }
            }
        });
        valueAnimator.start();

    }


    public int getScreenWidthPixels() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) BaseApp.getInstance().getSystemService(BaseApp.getInstance().WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }
}
