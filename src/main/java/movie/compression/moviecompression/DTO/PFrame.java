package movie.compression.moviecompression.DTO;

import org.opencv.core.Mat;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PFrame implements Serializable {
    @Serial
    private static final long serialVersion = 1L;
    private List<FragmentsFrame> fragmentsFrame = new ArrayList<>();
    public PFrame () {}
    public PFrame(List<FragmentsFrame> fragmentsFrame) {
        this.fragmentsFrame = fragmentsFrame;
    }

    public void setFragmentsFrame(List<FragmentsFrame> fragmentsFrame) {
        this.fragmentsFrame = fragmentsFrame;
    }

    public List<FragmentsFrame> getFragmentsFrame() {
        return fragmentsFrame;
    }
}
