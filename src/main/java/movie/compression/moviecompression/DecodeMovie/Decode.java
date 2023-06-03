package movie.compression.moviecompression.DecodeMovie;

import movie.compression.moviecompression.DTO.IFrame;
import movie.compression.moviecompression.Tools.Serializer;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import java.io.FileInputStream;
import java.util.List;

public class Decode {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] arg) {
        //Decode();
        List<IFrame> IFrames = (List<IFrame>) Serializer.deserializationObject("S:/ImageForMovieCompression/MovieFile/test3/test3_Movie.jmc");
    }

    public static void Decode() {
        VideoCapture cap = new VideoCapture("src/main/resources/testMovie/test3.mp4");
        //VideoWriter videoWriter = new VideoWriter("src/main/resources/OutMovie/output_test2.mp4", 0, 29.97, new Size(1920, 1080));
        Mat frame = new Mat();
        int k = 0;
        while(cap.read(frame)) {
            Imgcodecs.imwrite("src/main/resources/OutImage/test3/OutImageOriginal/" + k + ".jpeg", frame);
            //frame = Imgcodecs.imread("src/main/resources/OutImage/OutImageOriginal/IPEG_Image" + k + ".jpeg");
            k++;
        }
        //videoWriter.release();
        cap.release();
        System.out.println(k);
    }
}
