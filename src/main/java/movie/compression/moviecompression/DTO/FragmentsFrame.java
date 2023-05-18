package movie.compression.moviecompression.DTO;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class FragmentsFrame {
    private int x;
    private int y;
    private int width;
    private int height;
    private Mat fragment;
    public FragmentsFrame() {}

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public FragmentsFrame (int x, int y, int width, int height, Mat fragment) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fragment = fragment;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setFragment(Mat fragment) {
        this.fragment = fragment;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Mat getFragment() {
        return fragment;
    }
}
