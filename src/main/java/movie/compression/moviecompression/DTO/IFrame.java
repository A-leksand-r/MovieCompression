package movie.compression.moviecompression.DTO;

import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

public class IFrame {
    private Mat image;
    private List<PFrame> dependencyImage = new ArrayList<>();
    public IFrame() {}
    public IFrame(Mat image) {
        this.image = image;
    }
    public void setImage(Mat image) {
        this.image = image;
    }

    public void setDependencyImage(List<PFrame> dependencyImage) {
        this.dependencyImage = dependencyImage;
    }

    public Mat getImage() {
        return image;
    }

    public List<PFrame> getDependencyImage() {
        return dependencyImage;
    }
}
