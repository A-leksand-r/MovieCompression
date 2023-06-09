package movie.compression.moviecompression;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    /*@Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Movie Compression");
        stage.setScene(scene);
        stage.show();
    }*/

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Movie Compression");
        stage.setScene(scene);
        stage.show();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setSpacing(10);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text("Ошибка! не выбран путь к файлу"));
        Button ok = new Button();
        ok.autosize();
        ok.setAlignment(Pos.CENTER);
        ok.setText("Ok");
        ok.setOnAction(
                actionEvent -> {
                    dialog.close();
                }
        );
        dialogVbox.getChildren().add(ok);
        Scene dialogScene = new Scene(dialogVbox, 100, 100);
        dialog.setScene(dialogScene);
        dialog.show();
    }


    public static void main(String[] args) {
        launch();
    }
}