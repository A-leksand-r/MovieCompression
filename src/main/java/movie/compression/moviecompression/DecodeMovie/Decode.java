package movie.compression.moviecompression.DecodeMovie;

import movie.compression.moviecompression.Controllers.Controller;
import movie.compression.moviecompression.DTO.FragmentsFrame;
import movie.compression.moviecompression.DTO.IFrame;
import movie.compression.moviecompression.DTO.PFrame;
import movie.compression.moviecompression.Tools.ConverterImage;
import movie.compression.moviecompression.Tools.Serializer;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import java.util.List;

public class Decode {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    private static final String filePathImageDuplicate = "S:/ImageForMovieCompression/OutImage/test2/OutImageDuplicate_2.0/";
    /*public static void main(String[] arg) {
        List<IFrame> IFrames = (List<IFrame>) Serializer.deserializationObject("S:/ImageForMovieCompression/MovieFile/test3/test3_Movie.jmc");
        convertClassToImage(IFrames);
    }*/
    public static void decompressing() {
        List<IFrame> IFrames = (List<IFrame>) Serializer.deserializationObject("S:/ImageForMovieCompression/MovieFile/test3/test3_Movie.jmc");
        convertClassToImage(IFrames);
    }

    public static void convertClassToImage (List<IFrame> IFrames) {
        int count = 0;
        for (IFrame iFrame : IFrames) {
            //Imgcodecs.imwrite(filePathImageDuplicate + count++ + ".jpeg", ConverterImage.convertByteArrayToMat(iFrame.getImage()));
            //Controller.DisplayTheCompressedFrame(ConverterImage.convertByteArrayToMat(iFrame.getImage()));
            //System.out.println("Создание " + count + " кадра");
            List<PFrame> pFrames = iFrame.getDependencyImage();
            for (PFrame pFrame : pFrames) {
                Mat referenceImage = ConverterImage.convertByteArrayToMat(iFrame.getImage().clone());
                for (FragmentsFrame fragmentsFrame : pFrame.getFragmentsFrame()) {
                    Mat sectionOfImage = referenceImage.submat(new Rect(fragmentsFrame.getX(), fragmentsFrame.getY(), fragmentsFrame.getWidth(), fragmentsFrame.getHeight()));
                    ConverterImage.convertByteArrayToMat(fragmentsFrame.getFragment()).copyTo(sectionOfImage);
                }
                //Controller.DisplayTheCompressedFrame(ConverterImage.convertByteArrayToMat(iFrame.getImage()));
                //Imgcodecs.imwrite(filePathImageDuplicate + count++ + ".jpeg", referenceImage);
                //System.out.println("Создание " + count + " кадра");
            }
        }
    }
}
