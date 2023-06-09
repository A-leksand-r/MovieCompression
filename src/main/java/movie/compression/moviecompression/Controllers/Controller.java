package movie.compression.moviecompression.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import movie.compression.moviecompression.DecodeMovie.Decode;
import movie.compression.moviecompression.EncodeMovie.Encode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextField textField;
    public TextField filePath;
    @FXML
    private Label welcomeText;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ImageView image = new ImageView();

    @FXML
    protected void DisplayTheOriginalFrame(ActionEvent actionEvent) {
        //image.setImage(frame);
    }
    @FXML
    protected void DisplayTheCompressedFrame(ActionEvent actionEvent) {
        //image.setImage(frame);
    }
    @FXML
    protected void selectFile() {
        //FileChooser fileChooser = new FileChooser();
        //fileChooser.setTitle("Open Resource File");
        //File selectedFile = fileChooser.showOpenDialog(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textField.setText("Путь к файлу");
        String[] items = new String[464];
        for (int i = 1; i < 465; i++) {
            items[i - 1] = String.valueOf(i);
        }
        comboBox.getItems().addAll(items);
        comboBox.setValue("1");
    }
    public void Compressed(ActionEvent actionEvent) {
        Encode.comressing();
    }

    public void PlayMovie(ActionEvent actionEvent) {
        Decode.decompressing();
    }
}