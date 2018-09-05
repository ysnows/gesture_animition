package com.ysnows.bezier

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.log

/**
 * Created by gengzhibo on 2018/1/17.
 */
class BezierCurveView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    data class Point(var x: Float, var y: Float)   //坐标点的数据类

    var per = 0F
    var points: MutableList<Point> = ArrayList()
    val bezierPoints: MutableList<Point> = ArrayList()
    var viewTime = 15000F    //动画时间


    var inRunning = true    //是否在绘制图像

    var level = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val heitht = View.MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(width, heitht)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //初始化相关工具类
//
        if (inRunning && points.size > 0) {
            //绘制贝塞尔曲线
            drawBezier(canvas, per, points)
            level = 0
        }
    }


    //通过递归方法绘制贝塞尔曲线
    private fun drawBezier(canvas: Canvas, per: Float, points: MutableList<Point>) {

        //如果当前只有一个点
        //根据贝塞尔曲线定义可以得知此点在贝塞尔曲线上
        //将此点添加到贝塞尔曲线点集中(页面重新绘制后之前绘制的数据会丢失 需要重新回去前段的曲线路径)
        //将当前点绘制到页面中
        if (points.size == 1) {
            bezierPoints.add(Point(points[0].x, points[0].y))
            drawBezierPoint(bezierPoints, canvas)
            return
        }


        val nextPoints: MutableList<Point> = ArrayList()

        //更新路径信息
        //计算下一级控制点的坐标
        for (index in 1..points.size - 1) {

            val nextPointX = points[index - 1].x - (points[index - 1].x - points[index].x) * per
            val nextPointY = points[index - 1].y - (points[index - 1].y - points[index].y) * per
            nextPoints.add(Point(nextPointX, nextPointY))
        }

        //更新层级信息
        level++

        //绘制下一层
        drawBezier(canvas, per, nextPoints)
    }

    //绘制前段贝塞尔曲线部分
    private fun drawBezierPoint(bezierPoints: MutableList<Point>, canvas: Canvas) {
        val paintBse = Paint()
        paintBse.color = Color.GREEN
        paintBse.strokeWidth = 5F
        paintBse.style = Paint.Style.FILL

        val path = Path()
        var x = bezierPoints[0].x
        var y = bezierPoints[0].y

        Log.d("Hello", "x:" + x);
        Log.d("Hello", "y:" + y);

        path.moveTo(x, y)

        for (index in 1..bezierPoints.size - 1) {
            path.lineTo(bezierPoints[index].x, bezierPoints[index].y)
        }

//      path.close()

        canvas.drawPath(path, paintBse)
    }

    //开始动画
    fun changeView() {
        bezierPoints.clear()
        invalidate()

        val va = ValueAnimator.ofFloat(0F, 1F)
        va.duration = viewTime.toLong()
        va.addUpdateListener { animation ->
            Log.d("Hello", "per" + per)
            per = animation.animatedValue as Float
            invalidate()
        }
        va.start()
    }
}