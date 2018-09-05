package com.ysnows.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class BezierView extends View {


    private Path path;
    private Paint paint;
    private int slice;
    public int height;


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
        int screenWidthPixels = UiUtils.getScreenWidthPixels();

        path = new Path();

        paint = new Paint();
        paint.setColor(Color.parseColor("#9B9B9B"));
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        slice = screenWidthPixels / 10;

        height = 0;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(slice * 1.8f, 0);
        float firstAnchor = 4f;
        float sencondaryAnchor = 4.2f;
        path.cubicTo(slice * firstAnchor, height * 0.4f, slice * sencondaryAnchor, height, slice * 5, height);

        path.cubicTo(slice * 5.8f, height, slice * (10 - firstAnchor), height * 0.4f, slice * 8.2f, 0);
        path.close();
        canvas.drawPath(path, paint);
    }
}
