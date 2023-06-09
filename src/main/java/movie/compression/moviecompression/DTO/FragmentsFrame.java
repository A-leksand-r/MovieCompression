package movie.compression.moviecompression.DTO;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.io.Serial;
import java.io.Serializable;

public class FragmentsFrame implements Serializable {
    @Serial
    private static final long serialVersion = 1L;
    private int x;
    private int y;
    private int width;
    private int height;
    private String type;
    private byte[] fragment;
    public FragmentsFrame() {}
    public FragmentsFrame (int x, int y, int width, int height, String type, byte[] fragment) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.fragment = fragment;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
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


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setFragment(byte[] fragment) {
        this.fragment = fragment;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public byte[] getFragment() {
        return fragment;
    }
}
