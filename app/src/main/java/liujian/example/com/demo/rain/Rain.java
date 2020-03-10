package liujian.example.com.demo.rain;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.Random;

/**
 * @author : liujian
 * @since : 2020-03-09
 */
public class Rain {

    private static final int STATE_UP = 1;
    private static final int STATE_DOWN = 2;

    private int x;
    private int y;
    private int size;
    private int alpha; // 0 - 255

    private float initSpeed; // 初始的速度
    private float maxW; //最大的宽度
    private float maxH; // 最大的高度

    // 下降的最大位置
    private int downState;

    private Random random = new Random();

    Rain(float width, float height) {
        maxW = width;
        maxH = height;

        size = nextInt(random, 1, 4);
        alpha = nextInt(random, 20, 230);
        initSpeed = nextInt(random, 3, 5);

        x = nextInt(random, 0, (int) maxW);
        y = nextInt(random, 0, (int) maxH / 3);

        downState = nextInt(random, (int) (maxH / 2), (int) maxH);
    }

    // 速度插值计算 从慢到快
    private Interpolator interpolator = new AccelerateInterpolator();

    void refresh() {
        float speed = initSpeed + interpolator.getInterpolation(y / maxH) * initSpeed;
        switch (getState()) {
            case STATE_UP:
                y -= speed;
                break;
            case STATE_DOWN:
                y += speed;
                break;
        }
        Log.i("ddadadada", "refresh: y:" + y + "    speed:" + speed);
    }

    private int getState() {
        int state = STATE_DOWN;

        if (y <= downState) {
            state = STATE_DOWN;
        } else {
            y = 0;
            x = nextInt(random, 0, (int) maxW);
        }
        return state;
    }

    private int nextInt(Random random, int minValue, int maxValue) {
        if (minValue > maxValue) {
            return 0;
        }
        int num = maxValue - minValue;
        if (num == 0) {
            return maxValue;
        }
        return minValue + random.nextInt(num);
    }

    void drawRain(Canvas canvas, Paint paint) {
        int color = Color.WHITE;
        paint.setColor(ViewUtils.getAlphaColor(alpha, color));
        canvas.drawCircle(x, y, size, paint);
    }
}
