package movie.compression.moviecompression.EncodeMovie;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoWriter;

import javax.swing.*;

public class Encode {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] arg) {
        VideoWriter videoWriter = new VideoWriter("src/main/resources/OutMovie/output_test2.mp4", VideoWriter.fourcc('A', 'V', '1', 'x'), 29.97, new Size(1920, 1080));
        Mat frame;
        for (int i = 0; i < 463; i++) {
            frame = Imgcodecs.imread("src/main/resources/OutImage/OutImageOriginal/" + i + ".png");
            videoWriter.write(frame);
        }
        videoWriter.release();
    }
}
