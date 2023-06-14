package movie.compression.moviecompression.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import movie.compression.moviecompression.DecodeMovie.Decode;
import movie.compression.moviecompression.EncodeMovie.Encode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private File filePathChooser;
    private File filePathChooserSave;
    public TextField textField;
    public TextField filePath;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private static ImageView imageView = new ImageView();

    @FXML
    protected void DisplayTheOriginalFrame(ActionEvent actionEvent) {
        File file = new File(filePathChooser.toString() + "/" + comboBox.getValue() + ".jpeg");
        Image image = new Image(file.toString());
        imageView.setImage(image);
    }
    @FXML
    protected void DisplayTheCompressedFrame(ActionEvent actionEvent) {
        //image.setImage(frame);
    }
    @FXML
    protected void selectFileInput(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:\\"));
        directoryChooser.setTitle("Select file");
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        filePathChooser = directoryChooser.showDialog(theStage);
        textField.setText(filePathChooser.toString());
    }

    public void selectFileOutput(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:\\"));
        directoryChooser.setTitle("Select file");
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        filePathChooserSave = directoryChooser.showDialog(theStage);
        filePath.setText(filePathChooserSave.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textField.setText("Путь к файлу");
        String[] items = new String[464];
        for (int i = 1; i < 465; i++) {
            items[i - 1] = String.valueOf(i - 1);
        }
        comboBox.getItems().addAll(items);
        comboBox.setValue("0");
    }
    public void Compressed(ActionEvent actionEvent) {
        Encode.comressing(filePathChooser.toString(), filePathChooserSave.toString());
    }

    public void PlayMovie(ActionEvent actionEvent) throws IOException {
        Decode.decompressing(filePathChooserSave.toString(), filePathChooserSave.toString());
    }

    public static void setFrame(Image frame) {
        imageView.setImage(frame);
    }
}