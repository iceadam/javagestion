package landforlife.tn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/animallistback.fxml")));

        // Create a scene with the loaded FXML file
        Scene scene = new Scene(root, 1000, 800);

        // Set the scene to the primary stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Animal Form");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
