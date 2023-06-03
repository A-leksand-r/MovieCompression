package movie.compression.moviecompression.EncodeMovie;

import movie.compression.moviecompression.DTO.FragmentsFrame;
import movie.compression.moviecompression.DTO.IFrame;
import movie.compression.moviecompression.DTO.PFrame;
import movie.compression.moviecompression.Tools.ConverterImage;
import movie.compression.moviecompression.Tools.Serializer;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoWriter;

import java.util.ArrayList;
import java.util.List;

public class Encode {
    private static final String filePathImageOriginal = "S:/ImageForMovieCompression/OutImage/test2/OutImageOriginal/";
    private static final String filePathImageDuplicate = "S:/ImageForMovieCompression/OutImage/test2/OutImageDuplicate_2.0/";
    private static final String filePathSerializedMovie = "S:/ImageForMovieCompression/OutMovie/test2_Movie.jmc"; // jmc - Java Movie Compression
    private static final List<IFrame> IFrames = new ArrayList<>();

    private static final int framesCount = 464; // test2 - 464 кадра; test3 - 1200 кадров

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] arg) {
        convertImageToClass();
        Serializer.serializationObject(IFrames, filePathSerializedMovie);
        //convertClassToImage();
        //createMovie();
    }

    public static void convertImageToClass() {
        int k = 0;
        for (int i = 0; i < framesCount; i++) {
            System.out.println("IFrame: " + i); // Какой IFrame кадр
            Mat IImage = Imgcodecs.imread(filePathImageOriginal + i + ".jpeg");
            IFrame iFrame = new IFrame(ConverterImage.convertMatToByteArray(IImage, "jpeg"));
            IFrames.add(iFrame);
            k++;
            i = getPFrameList(iFrame, i);
        }
        System.out.println(k); // всего кадров
    }

    private static int getPFrameList(IFrame iFrame, int index) {
        Mat kernel = Imgproc.getStructuringElement(1,new Size(4,6));
        Mat kernel1 = Imgproc.getStructuringElement(1,new Size(2,3));
        for (int i = index + 1; i < framesCount; i++) {
            System.out.println("   PFrame: " + i); // какой PFrame кадр
            List<MatOfPoint> contours = new ArrayList<>();
            Mat image = Imgcodecs.imread(filePathImageOriginal + (i) + ".jpeg");
            Mat contoursImage = new Mat();

            imageConversion(iFrame, image, contoursImage, kernel, kernel1, contours);

            List<FragmentsFrame> fragmentsFrame = getFragmentsFrame(contours, image, image.width() * image.height());

            if (fragmentsFrame == null) return i - 1;
            else iFrame.getDependencyImage().add(new PFrame(fragmentsFrame));
        }
        return framesCount;
    }

    private static void imageConversion(IFrame iFrame, Mat image, Mat contoursImage, Mat kernel, Mat kernel1, List<MatOfPoint> contours) {
        Mat hierarchy = new Mat();
        Core.absdiff(ConverterImage.convertByteArrayToMat(iFrame.getImage()), image, contoursImage);
        Imgproc.erode(contoursImage, contoursImage, kernel,new Point(-1,-1),1);
        Imgproc.dilate(contoursImage, contoursImage, kernel1);
        Imgproc.threshold(contoursImage, contoursImage, 20, 255, Imgproc.THRESH_BINARY);
        Imgproc.cvtColor(contoursImage, contoursImage, Imgproc.COLOR_RGB2GRAY);
        Imgproc.findContours(contoursImage, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
    }

    private static List<FragmentsFrame> getFragmentsFrame (List<MatOfPoint> contours, Mat image, int squareImage) {
        List<FragmentsFrame> fragmentsFrame = new ArrayList<>();
        int square = 0;
        float multiplierRect = 1.125f;
        int k = 0;
        for(int i = 0; i < contours.size(); i++){
            k++;
            Rect rect = Imgproc.boundingRect(contours.get(i));
            if (rect.x < Math.round(rect.width * multiplierRect) + 2) {
                rect.width = 2 * (Math.round(rect.width * multiplierRect) + 2) - rect.x;
                rect.x = 0;
            }
            else if(rect.x + Math.round(rect.width * multiplierRect) + 2 > image.width()) {
                rect.x = rect.x - (Math.round(rect.width * multiplierRect) + 2);
                rect.width = image.width() - rect.x;
            }
            else {
                rect.x = rect.x - (Math.round(rect.width * multiplierRect) + 2);
                rect.width = 2 * (Math.round(rect.width * multiplierRect) + 2);
            }
            if (rect.y < Math.round(rect.height * multiplierRect) + 2) {
                rect.height = 2 * (Math.round(rect.height * multiplierRect) + 2) - rect.y;
                rect.y = 0;
            }
            else if(rect.y + Math.round(rect.height * multiplierRect) + 2 > image.height()) {
                rect.y = rect.y - (Math.round(rect.height * multiplierRect) + 2);
                rect.height = image.height() - rect.y;
            }
            else {
                rect.y = rect.y - (Math.round(rect.height * multiplierRect) + 2);
                rect.height = 2 * (Math.round(rect.height * multiplierRect) + 2);
            }
            square += rect.width * rect.height;
            if (square > squareImage * 0.5) return null;
            Mat croppedImage = new Mat(image, Imgproc.boundingRect(contours.get(i)));
            fragmentsFrame.add(new FragmentsFrame(rect.x, rect.y, rect.width, rect.height, ConverterImage.convertMatToByteArray(croppedImage, "bmp")));

        }
        System.out.println("Fragments: " + k); // количество фрагментов
        return fragmentsFrame;
    }

    public static void convertClassToImage () {
        int count = 0;
        for (IFrame iFrame : IFrames) {
            Imgcodecs.imwrite(filePathImageDuplicate + count++ + ".jpeg", ConverterImage.convertByteArrayToMat(iFrame.getImage()));
            System.out.println("Создание " + count + " кадра");
            List<PFrame> pFrames = iFrame.getDependencyImage();
            for (PFrame pFrame : pFrames) {
                Mat referenceImage = ConverterImage.convertByteArrayToMat(iFrame.getImage().clone());
                for (FragmentsFrame fragmentsFrame : pFrame.getFragmentsFrame()) {
                    Mat sectionOfImage = referenceImage.submat(new Rect(fragmentsFrame.getX(), fragmentsFrame.getY(), fragmentsFrame.getWidth(), fragmentsFrame.getHeight()));
                    ConverterImage.convertByteArrayToMat(fragmentsFrame.getFragment()).copyTo(sectionOfImage);
                }
                Imgcodecs.imwrite(filePathImageDuplicate + count++ + ".jpeg", referenceImage);
                System.out.println("Создание " + count + " кадра");
            }
        }
    }

    public static void createMovie() {
        VideoWriter videoWriter = new VideoWriter("S:/ImageForMovieCompression/OutMovie/output_test2.mp4", 0, 29.97, new Size(1920, 1080));
        //VideoWriter videoWriter = new VideoWriter("src/main/resources/OutMovie/output_test3.mp4", 0, 60, new Size(1920, 1012));
        Mat frame;
        for (int i = 0; i < 464; i++) {
            frame = Imgcodecs.imread("S:/ImageForMovieCompression/OutImage/test2/OutImageDuplicate_2.0/" + i + ".jpeg");
            videoWriter.write(frame);
        }
        videoWriter.release();
    }
}
