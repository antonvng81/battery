package com.example.joananton.battery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View {

    public interface ChartInfo {
        int getSize();
        float getY(int n);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constants
    //

    private static final int TOP_PADDING = 2;
    private static final int BOTTOM_PADDING = 2;
    private static final int LEFT_PADDING = 0;
    private static final int RIGHT_PADDING = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Data members
    //

    private ChartInfo mChartInfo;
    private Path mPath1, mPath2;
    private Paint mPaint;
    private Shader mShader;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    //

    private void init() {
        mPath1 = new Path();
        mPath2 = new Path();
        mPaint = new Paint();
    }

    public ChartView(Context context) {
        super(context);
        init();
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setChartInfo(ChartInfo chartInfo) {
        mChartInfo = chartInfo;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x, y, offset = 0;
        if (mChartInfo == null)
            return;

        y = (int) (getHeight() - BOTTOM_PADDING - (getHeight() - TOP_PADDING - BOTTOM_PADDING) * (mChartInfo.getY(offset) / 100.0));
        x = LEFT_PADDING;

        mPath1.reset();
        mPath1.moveTo(x, y);

        for (offset = 1; offset < mChartInfo.getSize(); offset++) {
            x = (int) (LEFT_PADDING + (getWidth() - LEFT_PADDING - RIGHT_PADDING) * (offset / ((float) mChartInfo.getSize())));
            y = (int) (getHeight() - BOTTOM_PADDING - (getHeight() - TOP_PADDING - BOTTOM_PADDING) * (mChartInfo.getY(offset) / 100.0));
            mPath1.lineTo(x, y);
        }

        mPath2.set(mPath1);

        mPath1.lineTo(x, getHeight() - BOTTOM_PADDING);
        mPath1.lineTo(LEFT_PADDING, getHeight() - BOTTOM_PADDING);
        mPath1.close();

        mPaint.reset();
        mShader = new LinearGradient(0, TOP_PADDING,
                0, getHeight() - BOTTOM_PADDING - TOP_PADDING,
                0xff0022bb, 0x00000000, Shader.TileMode.CLAMP);
        mPaint.setShader(mShader);

        canvas.drawPath(mPath1, mPaint);

        mPaint.reset();
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setARGB(255, 100, 150, 140);

        canvas.drawPath(mPath2, mPaint);
    }

}