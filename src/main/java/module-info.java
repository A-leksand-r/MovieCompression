module movie.compression.moviecompression {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires opencv;
    requires java.desktop;

    opens movie.compression.moviecompression to javafx.fxml;
    exports movie.compression.moviecompression;
}