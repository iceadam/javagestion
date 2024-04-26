package landforlife.tn.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import landforlife.tn.models.animal;
import landforlife.tn.util.MaConnection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class animallistfront {

    @FXML
    private FlowPane animalPane;

    private Connection connection = MaConnection.getInstance().getCnx();

    @FXML
    private void initialize() {
        loadAnimals();
    }

    private void loadAnimals() {
        List<animal> animals = new ArrayList<>();
        try {
            String query = "SELECT * FROM animal"; // Corrected table name
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                animal a = new animal();
                a.setId(resultSet.getInt("id"));
                a.setName(resultSet.getString("name"));
                a.setAge(resultSet.getInt("age"));
                a.setSpecies(resultSet.getString("species"));
                a.setAdoption_status(resultSet.getString("adoption_status"));
                a.setDescription(resultSet.getString("description"));
                a.setImage(resultSet.getBytes("image"));

                animals.add(a);
            }
            displayAnimals(animals);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayAnimals(List<animal> animals) {
        // Clear the existing content in animalPane
        animalPane.getChildren().clear();

        // Define spacing between each animal
        double spacingBetweenAnimals = 20.0;

        // Iterate through the list of animals and add them to the animalPane
        for (animal a : animals) {
            ImageView animalImageView = new ImageView(new Image(new ByteArrayInputStream(a.getImage())));
            animalImageView.setFitHeight(150);
            animalImageView.setFitWidth(200);

            Label idLabel = new Label("ID: " + a.getId());
            Label nameLabel = new Label("Name: " + a.getName());
            Label ageLabel = new Label("Age: " + a.getAge());
            Label speciesLabel = new Label("Species: " + a.getSpecies());
            Label adoptionStatusLabel = new Label("Adoption Status: " + a.getAdoption_status());
            Label descriptionLabel = new Label("Description: " + a.getDescription());

            Button adoptButton = new Button("Adopt");
            adoptButton.setOnAction(event -> handleAdopt(a));

            // Set layout properties for each component
            VBox animalInfo = new VBox(animalImageView, idLabel, nameLabel, ageLabel, speciesLabel, adoptionStatusLabel, descriptionLabel, adoptButton);
            animalInfo.setSpacing(10);

            // Add the VBox containing the components to the animalPane
            animalPane.getChildren().add(animalInfo);

            // Add spacing between each animal
            animalPane.setMargin(animalInfo, new Insets(spacingBetweenAnimals, 0, 0, 0));
        }
    }

    private void handleAdopt(animal a) {
        openDemandForm(a.getId());
    }

    private void openDemandForm(int animalId) {
        try {
            // Load the demandeform.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/demandeform.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            demandeform controller = loader.getController();

            // Set the animal ID in the demandeform controller
            controller.setAnimalId(animalId);

            // Create a new stage for the demand form
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
