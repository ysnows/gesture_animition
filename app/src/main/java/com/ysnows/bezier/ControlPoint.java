package com.ysnows.bezier;


import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allever on 17-7-27.
 */

public class ControlPoint {
    private PointF conPoint1;
    private PointF conPoint2;

    public ControlPoint(PointF p1, PointF p2) {
        this.conPoint1 = p1;
        this.conPoint2 = p2;
    }

    public PointF getConPoint1() {
        return conPoint1;
    }

    public void setConPoint1(PointF conPoint1) {
        this.conPoint1 = conPoint1;
    }

    public PointF getConPoint2() {
        return conPoint2;
    }

    public void setConPoint2(PointF conPoint2) {
        this.conPoint2 = conPoint2;
    }

    public static List<ControlPoint> getControlPointList(List<PointF> pointFs) {
        List<ControlPoint> controlPoints = new ArrayList<>();

        PointF p1;
        PointF p2;
        float conP1x;
        float conP1y;
        float conP2x;
        float conP2y;
        for (int i = 0; i < pointFs.size() - 1; i++) {

            if (i == 0) {
                //第一断1曲线 控制点
                conP1x = pointFs.get(i).x + (pointFs.get(i + 1).x - pointFs.get(i).x) / 4;
                conP1y = pointFs.get(i).y + (pointFs.get(i + 1).y - pointFs.get(i).y) / 4;

                conP2x = pointFs.get(i + 1).x - (pointFs.get(i + 2).x - pointFs.get(i).x) / 4;
                conP2y = pointFs.get(i + 1).y - (pointFs.get(i + 2).y - pointFs.get(i).y) / 4;

            } else if (i == pointFs.size() - 2) {
                //最后一段曲线 控制点
                conP1x = pointFs.get(i).x + (pointFs.get(i + 1).x - pointFs.get(i - 1).x) / 4;
                conP1y = pointFs.get(i).y + (pointFs.get(i + 1).y - pointFs.get(i - 1).y) / 4;

                conP2x = pointFs.get(i + 1).x - (pointFs.get(i + 1).x - pointFs.get(i).x) / 4;
                conP2y = pointFs.get(i + 1).y - (pointFs.get(i + 1).y - pointFs.get(i).y) / 4;
            } else {
                conP1x = pointFs.get(i).x + (pointFs.get(i + 1).x - pointFs.get(i - 1).x) / 4;
                conP1y = pointFs.get(i).y + (pointFs.get(i + 1).y - pointFs.get(i - 1).y) / 4;

                conP2x = pointFs.get(i + 1).x - (pointFs.get(i + 2).x - pointFs.get(i).x) / 4;
                conP2y = pointFs.get(i + 1).y - (pointFs.get(i + 2).y - pointFs.get(i).y) / 4;
            }

            p1 = new PointF(conP1x, conP1y);
            p2 = new PointF(conP2x, conP2y);

            ControlPoint controlPoint = new ControlPoint(p1, p2);
            controlPoints.add(controlPoint);
        }

        return controlPoints;
    }

}

