
package com.ysnows.bezier;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;


import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class UiUtils {

    private static int level;

    static public int getScreenWidthPixels() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) BaseApp.getInstance().getSystemService(BaseApp.getInstance().WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }


    static public int getScreenHeightPixels() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) BaseApp.getInstance().getSystemService(BaseApp.getInstance().WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        return dm.heightPixels;
    }



   /* public static  int  getScreenWidth(Context context){
        WindowManager windowManager = ((Activity)context).getWindowManager ();
        Display defaultDisplay = windowManager.getDefaultDisplay ();
        int width = defaultDisplay.getWidth ();
        return width;
    }
    public static  int  getScreenHeight(Context context){
        WindowManager windowManager = ((Activity)context).getWindowManager ();
        Display defaultDisplay = windowManager.getDefaultDisplay ();

        int height = defaultDisplay.getHeight ();
        return height;
    }*/


    /**
     * 将dp值转换为px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * dp转px
     *
     * @param context
     * @param dip
     *
     * @return
     */
    static public int dp2px(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }


    static public float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }


//    Rect frame = new Rect();
//    getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//    int statusBarHeight = frame.top;
//    上面这种方法基本上是可以的，但是下面这种方法更牛逼


    /**
     * 状态栏高度获取方法
     *
     * @return
     */
    public static int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return BaseApp.getInstance().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
            return 75;
        }
    }


    /**
     * 设置TopPadding为StatusBar的高度
     *
     * @param view
     * @param colorPrimary
     */
    public static void setStatusBarPadding(View view, int colorPrimary) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (colorPrimary > 0) {
                view.setBackgroundResource(colorPrimary);
            }
            view.setPadding(0, UiUtils.getStatusBarHeight(), 0, 0);

//            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
//            layoutParams.topMargin = UiUtils.getStatusBarHeight(view.getContext());
//            layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT;
//            layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
//            view.setLayoutParams(layoutParams);
        }
    }

    /**
     * 设置TopPadding为StatusBar的高度
     *
     * @param view
     * @param colorPrimary
     */
    public static void setStatusBarMargin(View view, int colorPrimary) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (colorPrimary > 0) {
                view.setBackgroundResource(colorPrimary);
            }

            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            layoutParams.topMargin = UiUtils.getStatusBarHeight();
            view.setLayoutParams(layoutParams);
        }
    }


    /**
     * 根据数据计算rv的高度
     *
     * @param size
     * @param colCount
     * @param rowHeight
     *
     * @return
     */

    public static int getRVHeith(Context context, int size, int colCount, int rowHeight) {
        rowHeight = dp2px(context, rowHeight);
        if (size % colCount == 0) {
            return size / colCount * rowHeight;
        } else {
            return ((size / colCount) + 1) * rowHeight;

        }
    }

    /**
     * 根据数据计算rv的高度
     *
     * @param size
     * @param colCount
     * @param rowHeight
     *
     * @return
     */

    public static int getRVHeithWithPx(Context context, int size, int colCount, int rowHeight) {
        if (size % colCount == 0) {
            return size / colCount * rowHeight;
        } else {
            return ((size / colCount) + 1) * rowHeight;
        }
    }


    /**
     * 根据数据计算rv的高度
     *
     * @param size
     * @param colCount
     * @param rowHeight
     *
     * @return
     */
    public static void setRVHeith(Context context, int size, int colCount, int rowHeight, View view) {
        if (view == null) {
            return;
        }
        int rvHeith = UiUtils.getRVHeith(context, size, colCount, rowHeight);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = rvHeith;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 根据数据计算rv的高度
     *
     * @param size
     * @param colCount
     * @param rowHeightPx
     *
     * @return
     */
    public static void setRVHeithWithPx(Context context, int size, int colCount, int rowHeightPx, View view) {
        if (view == null) {
            return;
        }

        int rvHeith = UiUtils.getRVHeithWithPx(context, size, colCount, rowHeightPx);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = rvHeith;
        view.setLayoutParams(layoutParams);
    }

    public static int px2dip(float pxValue) {
        final float scale = BaseApp.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    static public int dp2Px(Context context, float dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }


    public static int getEmuiLeval() {
        // Finals 2016-6-14 如果获取过了就不用再获取了，因为读取配置文件很慢
        if (level > 0) {
            return level;
        }
        Properties properties = new Properties();
        File propFile = new File(Environment.getRootDirectory(), "build.prop");
        FileInputStream fis = null;
        if (propFile != null && propFile.exists()) {
            try {
                fis = new FileInputStream(propFile);
                properties.load(fis);
                fis.close();
                fis = null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        if (properties.containsKey("ro.build.hw_emui_api_level")) {
            String valueString = properties.getProperty("ro.build.hw_emui_api_level");
            try {
                level = Integer.parseInt(valueString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return level;
    }

    public static void setRotionY(View view, float degree) {
        if (getEmuiLeval() <= 0) {
            view.setRotationY(degree);
        }
    }


}
