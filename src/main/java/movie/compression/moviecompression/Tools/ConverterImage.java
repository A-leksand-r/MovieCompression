package movie.compression.moviecompression.Tools;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ConverterImage {
    public static byte[] convertMatToByteArray(Mat image, String type) {
        BufferedImage bufferedImage;
        byte[] bytes = null;
        try {
            bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_3BYTE_BGR);
            byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
            image.get(0, 0, data);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, type, byteArrayOutputStream);
            bytes = byteArrayOutputStream.toByteArray();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
    public static Mat convertByteArrayToMat(byte[] image) {
        Mat mat;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image);
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
            byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
            mat = new Mat(bufferedImage.getHeight(),bufferedImage.getWidth(), CvType.CV_8UC3);
            mat.put(0, 0, data);
        } catch(IOException e) {
            mat = null;
            e.printStackTrace();
        }
        return mat;
    }
}
