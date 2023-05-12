package movie.compression.moviecompression.DecodeMovie;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

public class Decode {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] arg) {
        VideoCapture cap = new VideoCapture("src/main/resources/test2.mp4");
        VideoWriter videoWriter = new VideoWriter("src/main/resources/OutMovie/output_test2.mp4", 0, 29.97, new Size(1920, 1080));
        Mat frame = new Mat();
        int k = 0;
        while(cap.read(frame)) {
            frame = Imgcodecs.imread("src/main/resources/OutImage/OutImageDuplicate/" + k + ".png");
            k++;
        }
        videoWriter.release();
        cap.release();
        System.out.println(k);
    }
}
