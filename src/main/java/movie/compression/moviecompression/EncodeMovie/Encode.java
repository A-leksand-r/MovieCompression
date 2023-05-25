package movie.compression.moviecompression.EncodeMovie;

import movie.compression.moviecompression.DTO.FragmentsFrame;
import movie.compression.moviecompression.DTO.IFrame;
import movie.compression.moviecompression.DTO.PFrame;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoWriter;;import java.util.ArrayList;
import java.util.List;

public class Encode {
    private static String filePath = "src/main/resources/OutImage/OutImageOriginal/";
    private static List<IFrame> IFrames = new ArrayList<>();

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] arg) {
        ConvertImageToClass();
    }

    public static void ConvertImageToClass() {
        int k = 0;
        for (int i = 0; i < 464; i++) {

            Mat pImage = Imgcodecs.imread(filePath + i + ".png");
            IFrame iFrame = new IFrame(pImage);
            IFrames.add(iFrame);
            k++;
            i = getPFrameList(iFrame, i);
        }
        System.out.println(k);
    }

    private static int getPFrameList(IFrame iFrame, int index) {
        Mat kernel = Imgproc.getStructuringElement(1,new Size(4,6));
        Mat kernel1 = Imgproc.getStructuringElement(1,new Size(2,3));
        for (int i = index; i < 463; i++) {
            List<MatOfPoint> contours = new ArrayList<>();
            Mat image = Imgcodecs.imread(filePath + (i + 1) + ".png");
            Mat contoursImage = new Mat();

            imageConversion(iFrame, image, contoursImage, kernel, kernel1, contours);

            List<FragmentsFrame> fragmentsFrame = getFragmentsFrame(contours, contoursImage, image.width() * image.height());

            if (fragmentsFrame == null) return i;
            else iFrame.getDependencyImage().add(new PFrame(fragmentsFrame));
        }
        return 464;
    }

    private static void imageConversion(IFrame iFrame, Mat image, Mat contoursImage, Mat kernel, Mat kernel1, List<MatOfPoint> contours) {
        Mat hierarchy = new Mat();
        Core.absdiff(iFrame.getImage(), image, contoursImage);
        Imgproc.erode(contoursImage, contoursImage, kernel,new Point(-1,-1),1);
        Imgproc.dilate(contoursImage, contoursImage, kernel1);
        Imgproc.threshold(contoursImage, contoursImage, 20, 255, Imgproc.THRESH_BINARY);
        Imgproc.cvtColor(contoursImage, contoursImage, Imgproc.COLOR_RGB2GRAY);
        Imgproc.findContours(contoursImage, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
    }

    private static List<FragmentsFrame> getFragmentsFrame (List<MatOfPoint> contours, Mat contoursImage, int squareImage) {
        List<FragmentsFrame> fragmentsFrame = new ArrayList<>();
        List<Rect> boundRect = new ArrayList<>(contours.size());
        int square = 0;
        float multiplierRect = 1.125f;
        for(int i = 0; i < contours.size(); i++){
            Rect rect = Imgproc.boundingRect(contours.get(i));
            if (rect.x < Math.round(rect.width * multiplierRect) + 2) {
                rect.width = 2 * (Math.round(rect.width * multiplierRect) + 2) - rect.x;
                rect.x = 0;
            }
            else if(rect.x + Math.round(rect.width * multiplierRect) + 2 > contoursImage.width()) {
                rect.x = rect.x - (Math.round(rect.width * multiplierRect) + 2);
                rect.width = contoursImage.width() - rect.x;
            }
            else {
                rect.x = rect.x - (Math.round(rect.width * multiplierRect) + 2);
                rect.width = 2 * (Math.round(rect.width * multiplierRect) + 2);
            }
            if (rect.y < Math.round(rect.height * multiplierRect) + 2) {
                rect.height = 2 * (Math.round(rect.height * multiplierRect) + 2) - rect.y;
                rect.y = 0;
            }
            else if(rect.y + Math.round(rect.height * multiplierRect) + 2 > contoursImage.height()) {
                rect.y = rect.y - (Math.round(rect.height * multiplierRect) + 2);
                rect.height = contoursImage.height() - rect.y;
            }
            else {
                rect.y = rect.y - (Math.round(rect.height * multiplierRect) + 2);
                rect.height = 2 * (Math.round(rect.height * multiplierRect) + 2);
            }
            boundRect.add(new Rect(rect.x, rect.y, rect.width, rect.height));
            square += rect.width * rect.height;
            if (square > squareImage * 0.5) return null;
            Mat croppedImage = new Mat(contoursImage, boundRect.get(i));
            //Imgcodecs.imwrite("src/main/resources/OutImage/PartOfImage/croppedImage_1_2_" + i + ".jpeg", croppedImage);
            fragmentsFrame.add(new FragmentsFrame(rect.x, rect.y, rect.width, rect.height, croppedImage));
        }
        return fragmentsFrame;
    }

    /*public static void main(String[] arg) {
        VideoWriter videoWriter = new VideoWriter("src/main/resources/OutMovie/output_test2.mp4", VideoWriter.fourcc('A', 'V', '1', 'x'), 29.97, new Size(1920, 1080));
        Mat frame;
        for (int i = 0; i < 463; i++) {
            frame = Imgcodecs.imread("src/main/resources/OutImage/OutImageOriginal/" + i + ".png");
            videoWriter.write(frame);
        }
        videoWriter.release();
    }*/
}
