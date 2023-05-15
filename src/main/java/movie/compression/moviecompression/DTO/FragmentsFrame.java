package movie.compression.moviecompression.DTO;

import org.opencv.core.Mat;

public class FragmentsFrame {
    private int x;
    private int y;
    private Mat fragment;
    public FragmentsFrame() {}
    public FragmentsFrame (int x, int y, Mat fragment) {
    this.x = x;
    this.y = y;
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
