package movie.compression.moviecompression.DecodeMovie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import movie.compression.moviecompression.Controllers.Controller;
import movie.compression.moviecompression.DTO.FragmentsFrame;
import movie.compression.moviecompression.DTO.IFrame;
import movie.compression.moviecompression.DTO.PFrame;
import movie.compression.moviecompression.Tools.ConverterImage;
import movie.compression.moviecompression.Tools.Serializer;
import org.opencv.core.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class Decode {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    private static String filePathImageDuplicate = "S:/ImageForMovieCompression/OutImage/test2/OutImageDuplicate_2.0/";
    private static String filePathSerializedMovie;
    /*public static void main(String[] arg) {
        List<IFrame> IFrames = (List<IFrame>) Serializer.deserializationObject("S:/ImageForMovieCompression/MovieFile/test3/test3_Movie.jmc");
        convertClassToImage(IFrames);
    }*/
    public static void decompressing(String pathImage, String pathSave) throws IOException {
        filePathImageDuplicate = pathImage;
        filePathSerializedMovie = pathSave + "\\" + "test2_Movie.jmc";
        List<IFrame> IFrames = (List<IFrame>) Serializer.deserializationObject(filePathSerializedMovie);
        convertClassToImage(IFrames);
    }

    public static void convertClassToImage (List<IFrame> IFrames) throws IOException {
        int count = 0;
        for (IFrame iFrame : IFrames) {
            //Imgcodecs.imwrite(filePathImageDuplicate + count++ + "Duplicate.jpeg", ConverterImage.convertByteArrayToMat(iFrame.getImage()));
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(iFrame.getImage()));
            Controller.setFrame(convertToFxImage(img));
            //System.out.println("Создание " + count + " кадра");
            List<PFrame> pFrames = iFrame.getDependencyImage();
            for (PFrame pFrame : pFrames) {
                Mat referenceImage = ConverterImage.convertByteArrayToMat(iFrame.getImage().clone());
                for (FragmentsFrame fragmentsFrame : pFrame.getFragmentsFrame()) {
                    Mat sectionOfImage = referenceImage.submat(new Rect(fragmentsFrame.getX(), fragmentsFrame.getY(), fragmentsFrame.getWidth(), fragmentsFrame.getHeight()));
                    ConverterImage.convertByteArrayToMat(fragmentsFrame.getFragment()).copyTo(sectionOfImage);
                }
                //Controller.DisplayTheCompressedFrame(ConverterImage.convertByteArrayToMat(iFrame.getImage()));
                //Imgcodecs.imwrite(filePathImageDuplicate + count++ + "Duplicate.jpeg", referenceImage);
                BufferedImage img2 = ImageIO.read(new ByteArrayInputStream(ConverterImage.convertMatToByteArray(referenceImage, "jpeg")));
                Controller.setFrame(convertToFxImage(img2));
                //System.out.println("Создание " + count + " кадра");
            }
        }
    }

    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }
}
