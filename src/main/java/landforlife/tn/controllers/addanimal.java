package landforlife.tn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import landforlife.tn.models.animal;
import landforlife.tn.util.MaConnection;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

public class addanimal {

    private animal animal;

    public javafx.scene.image.ImageView ImageView;
    @FXML
    private TextField adoption_statusF;

    @FXML
    private TextField ageF;

    @FXML
    private Button chooseImageButton;

    @FXML
    private TextField descriptionF;

    @FXML
    private TextField nameF;

    @FXML
    private TextField speciesF;

    @FXML
    private Button submitB;


    private File selectedImageFile;

    private Connection connection;

    private boolean isUpdating = false;

    public addanimal() {
        // Default constructor
    }

    public void setAnimal(animal animal, String encodedImage) {
        this.animal = animal;
        nameF.setText(animal.getName());
        ageF.setText(String.valueOf(animal.getAge()));
        speciesF.setText(animal.getSpecies());
        adoption_statusF.setText(animal.getAdoption_status());
        descriptionF.setText(animal.getDescription());

        if (encodedImage != null && !encodedImage.isEmpty()) {
            byte[] decodedImage = Base64.getDecoder().decode(encodedImage);
            Image image = new Image(new ByteArrayInputStream(decodedImage));
            ImageView.setImage(image);
        }

        isUpdating = true;
    }


    @FXML
    private void initialize() {
        chooseImageButton.setOnAction(event -> handleChooseImage());
        submitB.setOnAction(event -> handleSubmit());

        // Initialize database connection
        connection = MaConnection.getInstance().getCnx();
    }

    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            ImageView.setImage(new Image(file.toURI().toString()));
            selectedImageFile = file;
        }
    }

    private void handleSubmit() {
        // Retrieve values from text fields
        String name = nameF.getText();
        String ageText = ageF.getText();
        String species = speciesF.getText();
        String adoptionStatus = adoption_statusF.getText();
        String description = descriptionF.getText();

        // Validate input
        if (!validateName(name) || !validateAge(ageText) || !validateSpecies(species) || !validateAdoptionStatus(adoptionStatus) || !validateDescription(description)) {
            System.out.println("Please correct the input fields.");
            return;
        }

        // Check if an image is selected
        if (selectedImageFile == null) {
            System.out.println("Please select an image.");
            return;
        }

        // Save data to database
        try {
            byte[] imageBytes = Files.readAllBytes(selectedImageFile.toPath());

            if (isUpdating) {
                // Update existing animal
                String query = "UPDATE animal SET name=?, age=?, species=?, adoption_status=?, Description=?, image=? WHERE id=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, name);
                statement.setInt(2, Integer.parseInt(ageText));
                statement.setString(3, species);
                statement.setString(4, adoptionStatus);
                statement.setString(5, description);
                statement.setBytes(6, imageBytes); // Save image data
                statement.setInt(7, animal.getId());
                statement.executeUpdate();
                System.out.println("Animal data updated in the database.");
            } else {
                // Insert new animal
                String query = "INSERT INTO animal (name, age, species, adoption_status, Description, image) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, name);
                statement.setInt(2, Integer.parseInt(ageText));
                statement.setString(3, species);
                statement.setString(4, adoptionStatus);
                statement.setString(5, description);
                statement.setBytes(6, imageBytes); // Save image data
                statement.executeUpdate();
                System.out.println("New animal data saved to the database.");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validateName(String name) {
        return !name.isEmpty();
    }

    private boolean validateAge(String ageText) {
        try {
            int age = Integer.parseInt(ageText);
            return age >= 0; // Assuming age must be non-negative
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateSpecies(String species) {
        return !species.isEmpty();
    }

    private boolean validateAdoptionStatus(String adoptionStatus) {
        return !adoptionStatus.isEmpty();
    }

    private boolean validateDescription(String description) {
        return !description.isEmpty();
    }

}