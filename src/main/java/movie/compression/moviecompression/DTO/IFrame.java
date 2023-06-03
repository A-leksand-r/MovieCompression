package movie.compression.moviecompression.DTO;

import org.opencv.core.Mat;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IFrame implements Serializable {
    @Serial
    private static final long serialVersion = 1L;
    private byte[] image;
    private List<PFrame> dependencyImage = new ArrayList<>();
    public IFrame() {}
    public IFrame(byte[] image) {
        this.image = image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setDependencyImage(List<PFrame> dependencyImage) {
        this.dependencyImage = dependencyImage;
    }

    public byte[] getImage() {
        return image;
    }

    public List<PFrame> getDependencyImage() {
        return dependencyImage;
    }
}
