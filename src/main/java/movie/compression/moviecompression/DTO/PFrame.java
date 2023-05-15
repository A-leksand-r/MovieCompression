package movie.compression.moviecompression.DTO;

import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

public class PFrame {
    private List<FragmentsFrame> fragmentsFrame = new ArrayList<>();

    public void setFragmentsFrame(List<FragmentsFrame> fragmentsFrame) {
        this.fragmentsFrame = fragmentsFrame;
    }

    public List<FragmentsFrame> getFragmentsFrame() {
        return fragmentsFrame;
    }
}
