package liujian.example.com.demo.rain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author : liujian
 * @since : 2020-03-09
 */
public class RainSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final long PERIOD = 1000 / (60);
    private List<Rain> mPointList = Collections.synchronizedList(new ArrayList<Rain>());

    private boolean isRunning;
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private float mWidth, mHeight;

    private Paint mPaint;

    public RainSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public RainSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RainSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (mPointList.size() < 300 && mWidth != 0 && mHeight != 0) {
                    int addNum = new Random().nextInt(6) + 1;
                    for (int i = 0; i < addNum; i++) {
                        mPointList.add(new Rain(mWidth, mHeight));
                    }
                }
                refreshAllPoint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, PERIOD);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        drawBackground();
        mWidth = width;
        mHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            drawing();
        }
    }

    private void drawing() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.BLACK);
            for (int i = 0; i < mPointList.size(); i++) {
                Rain temp = mPointList.get(i);
                temp.drawRain(mCanvas, mPaint);
            }
            // 清屏
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    private void drawBackground() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.BLACK);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    private void refreshAllPoint() {
        Iterator<Rain> i = mPointList.iterator();
        while (i.hasNext()) {
            Rain temp = i.next();
            temp.refresh();
        }
    }
}
