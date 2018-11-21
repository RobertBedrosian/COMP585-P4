package facebooklite;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FacebookLite extends Application {

    @Override
    public void start(final Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FacebookLite.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 780);
        scene.getStylesheets().add(getClass().getResource("/FacebookLite.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
