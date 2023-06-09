package movie.compression.moviecompression;

import movie.compression.moviecompression.DTO.FragmentsFrame;
import movie.compression.moviecompression.DTO.IFrame;
import movie.compression.moviecompression.DTO.PFrame;
import movie.compression.moviecompression.Tools.ConverterImage;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Test {

    private static IFrame iFrame = new IFrame();
    private static PFrame pFrame = new PFrame();

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME );

        Mat new_img = new Mat(1080, 1920, CvType.CV_8UC3, new Scalar(0));

        //Mat img1 = Imgcodecs.imread("src/main/resources/OutImage/test3/OutImageOriginal/0.jpeg");
        Mat img1 = Imgcodecs.imread("S:/ImageForMovieCompression/OutImage/test2/OutImageOriginal/1.jpeg");

        BufferedImage bufferedImage = new BufferedImage(img1.cols(), img1.rows(), BufferedImage.TYPE_BYTE_BINARY);

        iFrame.setImage(ConverterImage.convertMatToByteArray(img1, "jpeg"));
        iFrame.getDependencyImage().add(pFrame);
        //Mat img2 = Imgcodecs.imread("src/main/resources/OutImage/test3/OutImageOriginal/23.jpeg");
        Mat img2 = Imgcodecs.imread("S:/ImageForMovieCompression/OutImage/test2/OutImageOriginal/2.jpeg");

        Mat img = new Mat();
        Mat erodeImg = new Mat();
        Mat dilateImg = new Mat();
        Mat threshImg = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();

        Mat hierarchy = new Mat();
        // Пиксели делают плохо
        Core.absdiff(img1, img2, img);
        Imgcodecs.imwrite("S:/ImageForMovieCompression/OutImage/test2/OutImageOriginal/1absdiff.jpeg", img);

        Mat kernel = Imgproc.getStructuringElement(1,new Size(4,4));
        Mat kernel1 = Imgproc.getStructuringElement(1,new Size(2,3));
        // Расширяем темные области, сужаем светлые
        Imgproc.erode(img, img, kernel,new Point(-1,-1),1);
        Imgcodecs.imwrite("S:/ImageForMovieCompression/OutImage/test2/OutImageOriginal/2erode.jpeg", img);
        // Расширяем светлые области, сужаем темные
        Imgproc.dilate(img, img, kernel1);
        Imgcodecs.imwrite("S:/ImageForMovieCompression/OutImage/test2/OutImageOriginal/3dilate.jpeg", img);
        // Обнаружение края
        Imgproc.threshold(img, img, 20, 255, Imgproc.THRESH_BINARY);
        Imgcodecs.imwrite("S:/ImageForMovieCompression/OutImage/test2/OutImageOriginal/4threshold.jpeg", img);
        // Преобразовать в оттенки серого
        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
        Imgcodecs.imwrite("S:/ImageForMovieCompression/OutImage/test2/OutImageOriginal/5cvtColor.jpeg", img);
        // Находим схему (3: CV_RETR_TREE, 2: CV_CHAIN_APPROX_SIMPLE)
        Imgproc.findContours(img, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));

        List<Rect> boundRect = new ArrayList<>(contours.size());
        float multiplierRect = 1.125f;
        String type = "jpeg";
        for(int i = 0; i < contours.size(); i++){
            Rect rect = Imgproc.boundingRect(contours.get(i));

            /*rect.x = rect.x - ((Math.round(rect.width * multiplierRect)) + 2) / 2;
            if (rect.x < 0) rect.x = 0;
            rect.y = rect.y - ((Math.round(rect.height * multiplierRect)) + 2) / 2;
            if (rect.y < 0) rect.y = 0;
            rect.width = Math.round(rect.width * multiplierRect) + 2;
            if (rect.x + rect.width > img2.width()) rect.width = img2.width() - rect.x;
            rect.height = Math.round(rect.height * multiplierRect) + 2;
            if (rect.y + rect.height > img2.height()) rect.height = img2.height() - rect.y;*/
            /*if (rect.x < ((Math.round(rect.width * (multiplierRect - 1))) + 2) / 2) {
                rect.x = 0;
                if (Math.round(rect.width * multiplierRect) + 2 > img2.width()) {
                    rect.width = img2.width();
                }
                else {
                    rect.width = Math.round(rect.width * multiplierRect) + 2;
                }
            }
            else {
                rect.x = rect.x - ((Math.round(rect.width * (multiplierRect - 1))) + 2) / 2;
                if (rect.x + Math.round(rect.width * multiplierRect) + 2 > img2.width()) {
                    rect.width = img2.width();
                }
                else {
                    rect.width = Math.round(rect.width * multiplierRect) + 2;
                }
            }
            if (rect.y < ((Math.round(rect.height * (multiplierRect - 1))) + 2) / 2) {
                rect.y = 0;
                if (Math.round(rect.height * multiplierRect) + 2 > img2.height()) {
                    rect.height = img2.height();
                }
                else {
                    rect.height = Math.round(rect.height * multiplierRect) + 2;
                }
            }
            else {
                rect.y = rect.y - ((Math.round(rect.height * (multiplierRect - 1))) + 2) / 2;
                if (rect.y + Math.round(rect.height * multiplierRect) + 2 > img2.height()) {
                    rect.height = img2.height();
                }
                else {
                    rect.height = Math.round(rect.height * multiplierRect) + 2;
                }
            }*/
            /*if (rect.x < (Math.round(rect.width * multiplierRect)) / 2) {
                rect.width = (Math.round(rect.width * multiplierRect) + 2) - rect.x;
                rect.x = 0;
            }
            else if(rect.x + Math.round(rect.width * multiplierRect) + 2 > img2.width() - 1) {
                rect.x = rect.x - (Math.round(rect.width * multiplierRect) + 2);
                rect.width = img2.width() - rect.x;
            }
            else {
                rect.x = rect.x - (Math.round(rect.width * (multiplierRect - 1)) + 2);
                rect.width = Math.round(rect.width * multiplierRect) + 2;
            }
            if (rect.y < Math.round(rect.height * multiplierRect) + 2) {
                rect.height = 2 * (Math.round(rect.height * multiplierRect) + 2) - rect.y;
                rect.y = 0;
            }
            else if(rect.y + Math.round(rect.height * multiplierRect) + 2 > img2.height() - 1) {
                rect.y = rect.y - (Math.round(rect.height * multiplierRect) + 2);
                rect.height = img2.height() - rect.y;
            }
            else {
                rect.y = rect.y - (Math.round(rect.height * multiplierRect) + 2);
                rect.height = 2 * (Math.round(rect.height * multiplierRect) + 2);
            }*/
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
            /*Mat copyImage = new_img.submat(rect);
            croppedImage.copyTo(copyImage);*/
            if (i == 100) {
                System.out.println(i);
            }
            if (rect.width * rect.height < 196)
                type = "bmp";
            else type = "jpeg";
            //Imgcodecs.imwrite("src/main/resources/OutImage/test3/PartOfImage/croppedImage_1_2_" + i + ".jpeg", croppedImage);
            pFrame.getFragmentsFrame().add(new FragmentsFrame(rect.x, rect.y, rect.width, rect.height, type, ConverterImage.convertMatToByteArray(croppedImage, "bmp")));
        }
        //Imgcodecs.imwrite("src/main/resources/OutImage/test3/new_img_2.jpeg", new_img);

        /*Mat copy_new_img = new_img.clone();
        contours = new ArrayList<>();
        hierarchy = new Mat();
        Imgproc.cvtColor(copy_new_img, copy_new_img, Imgproc.COLOR_RGB2GRAY);
        Imgproc.findContours(copy_new_img, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));*/

        /*for (MatOfPoint contour : contours) {
            Mat croppedImage = new Mat(new_img, Imgproc.boundingRect(contour));
            Mat copyImage = img1.submat(Imgproc.boundingRect(contour));
            croppedImage.copyTo(copyImage);
        }*/

        //Imgcodecs.imwrite("src/main/resources/OutImage/test3/out_image_0-23.jpeg", img1);

        for (FragmentsFrame fragmentsFrame : iFrame.getDependencyImage().get(0).getFragmentsFrame()) {
            Mat copyImage = img1.submat(new Rect(fragmentsFrame.getX(), fragmentsFrame.getY(), fragmentsFrame.getWidth(), fragmentsFrame.getHeight()));
            ConverterImage.convertByteArrayToMat(fragmentsFrame.getFragment()).copyTo(copyImage);
        }

        /*for(int i = 0; i < contours.size(); i++){
            Scalar color = new Scalar(0,0,255);
            // Рисуем контур
            Imgproc.drawContours(new_img, contours, i, color, 1, Imgproc.LINE_8, hierarchy, 0, new Point());
            // Рисуем прямоугольник
            Imgproc.rectangle(new_img, Imgproc.boundingRect(contours.get(i)).tl(), Imgproc.boundingRect(contours.get(i)).br(), color, 2, Imgproc.LINE_8, 0);
        }*/

        //Imgcodecs.imwrite("src/main/resources/OutImage/test3/new_img_3.jpeg", new_img);
        for(int i = 0; i < contours.size(); i++){
            Scalar color = new Scalar(0,0,255);
            // Рисуем контур
            Imgproc.drawContours(img1, contours, i, color, 1, Imgproc.LINE_8, hierarchy, 0, new Point());
            // Рисуем прямоугольник
            //Imgproc.rectangle(img1, boundRect.get(i).tl(), boundRect.get(i).br(), color, 2, Imgproc.LINE_8, 0);
            //System.out.println(Arrays.toString(hierarchy.get(0, i)));
            //Imgcodecs.imwrite("src/main/resources/OutImage/test3/copy.jpeg", copy);
        }

        /*Imgcodecs.imwrite("src/main/resources/OutImage/OutImageOriginal/erode_1_2.jpeg", erodeImg);
        Imgcodecs.imwrite("src/main/resources/OutImage/OutImageOriginal/dilate_1_2.jpeg", dilateImg);
        Imgcodecs.imwrite("src/main/resources/OutImage/OutImageOriginal/thresh_1_2.jpeg", threshImg);*/
        Imgcodecs.imwrite("S:/ImageForMovieCompression/OutImage/test2/OutImageOriginal/img1_1_3.jpeg", img1);
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
