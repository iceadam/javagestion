package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import landforlife.tn.models.animal;
import landforlife.tn.services.animalservice;
import landforlife.tn.util.MaConnection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class animallistfront {
    @FXML
    public Label idLabel;
    @FXML
    public Label nameLabel;
    @FXML
    public Label ageLabel;
    @FXML
    public Label speciesLabel;
    @FXML
    public Label adoption_statusLabel;
    @FXML
    public Label descriptionLabel;
    @FXML
    private VBox animalContainer;

    private Connection connection = MaConnection.getInstance().getCnx();

    private animalservice animalService = new animalservice();

    @FXML
    private void initialize() {
        loadAnimals();
    }

    private void loadAnimals() {
        try {
            String query = "SELECT * FROM animal";
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

                addAnimalEntry(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addAnimalEntry(animal animal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/animallistfront.fxml"));
            AnchorPane animalEntry = loader.load();

            // Get UI elements from the loaded animal entry
            ImageView imageView = (ImageView) animalEntry.lookup("#imageView");
            Label idLabel = (Label) animalEntry.lookup("#idLabel");
            Label nameLabel = (Label) animalEntry.lookup("#nameLabel");
            Label ageLabel = (Label) animalEntry.lookup("#ageLabel");
            Label speciesLabel = (Label) animalEntry.lookup("#speciesLabel");
            Label adoptionStatusLabel = (Label) animalEntry.lookup("#adoptionStatusLabel");
            Label descriptionLabel = (Label) animalEntry.lookup("#descriptionLabel");

            // Set animal data to corresponding UI elements
            imageView.setImage(new Image(new ByteArrayInputStream(animal.getImage())));
            idLabel.setText(Integer.toString(animal.getId()));
            nameLabel.setText(animal.getName());
            ageLabel.setText(Integer.toString(animal.getAge()));
            speciesLabel.setText(animal.getSpecies());
            adoptionStatusLabel.setText(animal.getAdoption_status());
            descriptionLabel.setText(animal.getDescription());

            // Add the animal entry to the container
            animalContainer.getChildren().add(animalEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
