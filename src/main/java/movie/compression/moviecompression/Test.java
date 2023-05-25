package movie.compression.moviecompression;

import movie.compression.moviecompression.DTO.FragmentsFrame;
import movie.compression.moviecompression.DTO.IFrame;
import movie.compression.moviecompression.DTO.PFrame;
import org.controlsfx.tools.Utils;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Test {

    private static IFrame iFrame = new IFrame();
    private static PFrame pFrame = new PFrame();

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME );

        Mat img1 = Imgcodecs.imread("src/main/resources/OutImage/OutImageOriginal/1.png");
        iFrame.setImage(img1);
        iFrame.getDependencyImage().add(pFrame);
        Mat img2 = Imgcodecs.imread("src/main/resources/OutImage/OutImageOriginal/2.png");

        Mat img = new Mat();
        Mat erodeImg = new Mat();
        Mat dilateImg = new Mat();
        Mat threshImg = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();

        Mat hierarchy = new Mat();
        // Пиксели делают плохо
        Core.absdiff(img1, img2, img);

        Mat kernel = Imgproc.getStructuringElement(1,new Size(4,6));
        Mat kernel1 = Imgproc.getStructuringElement(1,new Size(2,3));
        // Расширяем темные области, сужаем светлые
        Imgproc.erode(img, img, kernel,new Point(-1,-1),1);
        // Расширяем светлые области, сужаем темные
        Imgproc.dilate(img, img, kernel1);
        // Обнаружение края
        Imgproc.threshold(img, img, 20, 255, Imgproc.THRESH_BINARY);
        // Преобразовать в оттенки серого
        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
        // Находим схему (3: CV_RETR_TREE, 2: CV_CHAIN_APPROX_SIMPLE)
        Imgproc.findContours(img, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));

        List<Rect> boundRect = new ArrayList<>(contours.size());
        float multiplierRect = 1.125f;
        for(int i = 0; i < contours.size(); i++){
            Rect rect = Imgproc.boundingRect(contours.get(i));
            if (rect.x < Math.round(rect.width * multiplierRect) + 2) {
                rect.width = 2 * (Math.round(rect.width * multiplierRect) + 2) - rect.x;
                rect.x = 0;
            }
            else if(rect.x + Math.round(rect.width * multiplierRect) + 2 > img2.width()) {
                rect.x = rect.x - (Math.round(rect.width * multiplierRect) + 2);
                rect.width = img2.width() - rect.x;
            }
            else {
                rect.x = rect.x - (Math.round(rect.width * multiplierRect) + 2);
                rect.width = 2 * (Math.round(rect.width * multiplierRect) + 2);
            }
            if (rect.y < Math.round(rect.height * multiplierRect) + 2) {
                rect.height = 2 * (Math.round(rect.height * multiplierRect) + 2) - rect.y;
                rect.y = 0;
            }
            else if(rect.y + Math.round(rect.height * multiplierRect) + 2 > img2.height()) {
                rect.y = rect.y - (Math.round(rect.height * multiplierRect) + 2);
                rect.height = img2.height() - rect.y;
            }
            else {
                rect.y = rect.y - (Math.round(rect.height * multiplierRect) + 2);
                rect.height = 2 * (Math.round(rect.height * multiplierRect) + 2);
            }
            boundRect.add(new Rect(rect.x, rect.y, rect.width, rect.height));
            Mat croppedImage = new Mat(img2, boundRect.get(i));
            Imgcodecs.imwrite("src/main/resources/OutImage/PartOfImage/croppedImage_1_2_" + i + ".jpeg", croppedImage);
            pFrame.getFragmentsFrame().add(new FragmentsFrame(rect.x, rect.y, rect.width, rect.height, croppedImage));
        }

        for (FragmentsFrame fragmentsFrame : iFrame.getDependencyImage().get(0).getFragmentsFrame()) {
            Mat copyImage = img1.submat(new Rect(fragmentsFrame.getX(), fragmentsFrame.getY(), fragmentsFrame.getWidth(), fragmentsFrame.getHeight()));
            fragmentsFrame.getFragment().copyTo(copyImage);
        }

        /*for(int i=0;i<contours.size();i++){
            Scalar color = new Scalar(0,0,255);
            // Рисуем контур
        	Imgproc.drawContours(img1, contours, i, color, 1, Imgproc.LINE_8, hierarchy, 0, new Point());
            // Рисуем прямоугольник
            Imgproc.rectangle(img1, boundRect.get(i).tl(), boundRect.get(i).br(), color, 2, Imgproc.LINE_8, 0);
        }*/

        /*Imgcodecs.imwrite("src/main/resources/OutImage/OutImageOriginal/erode_1_2.jpeg", erodeImg);
        Imgcodecs.imwrite("src/main/resources/OutImage/OutImageOriginal/dilate_1_2.jpeg", dilateImg);
        Imgcodecs.imwrite("src/main/resources/OutImage/OutImageOriginal/thresh_1_2.jpeg", threshImg);*/
        Imgcodecs.imwrite("src/main/resources/OutImage/OutImageOriginal/img1_1_2.jpeg", img1);
    }
}
