package liujian.example.com.demo.rain;

/**
 * @author : liujian
 * @since : 2020-03-09
 */
public class ViewUtils {

    /**
     * 0-1
     */
    public static int getAlphaColor(float alpha, int color) {
        return getAlphaColor((int) (alpha * 255), color);
    }

    /**
     * 0-255
     */
    public static int getAlphaColor(int alpha, int color) {
        return (alpha << 24) | (color & 0xffffff);
    }

}
