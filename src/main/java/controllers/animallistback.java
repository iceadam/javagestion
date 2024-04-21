package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import landforlife.tn.models.animal;
import landforlife.tn.services.animalservice;
import landforlife.tn.util.MaConnection;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class animallistback {

    @FXML
    private TableView<animal> animalTable;
    @FXML
    private TableColumn<animal, Integer> idColumn;
    @FXML
    private TableColumn<animal, String> nameColumn;
    @FXML
    private TableColumn<animal, Integer> ageColumn;
    @FXML
    private TableColumn<animal, String> speciesColumn;
    @FXML
    private TableColumn<animal, String> adoption_statusColumn;
    @FXML
    private TableColumn<animal, String> descriptionColumn;
    @FXML
    private TableColumn<animal, Void> imageColumn;
    @FXML
    private TableColumn<animal, Void> UpdateColumn;
    @FXML
    private TableColumn<animal, Void> DeleteColumn;

    // Connection object
    private final Connection connection = MaConnection.getInstance().getCnx();

    // Animal service
    private final animalservice animalservice = new animalservice();

    @FXML
    private void initialize() {
        // Initialize columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        speciesColumn.setCellValueFactory(new PropertyValueFactory<>("species"));
        adoption_statusColumn.setCellValueFactory(new PropertyValueFactory<>("adoption_status"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        UpdateColumn.setCellFactory(cell -> new UpdateButtonCell(this::handleUpdateAction));
        DeleteColumn.setCellFactory(cell -> new DeleteButtonCell(this::handleDeleteAction));
        imageColumn.setCellFactory(param -> new TableCell<animal, Void>() {
            private final Button viewImageButton = new Button("View Image");

            {
                viewImageButton.setOnAction(event -> {
                    animal selectedAnimal = getTableView().getItems().get(getIndex());
                    openImageDialog(selectedAnimal);
                });
            }
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewImageButton);
                }
            }

        });

        // Load animals from database
        loadAnimals();
    }

    private void loadAnimals() {
        List<animal> animals = new ArrayList<>();
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

                // Retrieve image data from the BLOB column
                Blob blob = resultSet.getBlob("image");
                if (blob != null) {
                    byte[] imageData = blob.getBytes(1, (int) blob.length());
                    a.setImage(imageData); // Set the image data as a byte array
                }
            }
            animalTable.getItems().setAll(animals);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openImageDialog(animal animal) {
        try {
            // Load the ImageDialog.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ImageDialog.fxml"));
            Parent root = loader.load();

            // Get the controller associated with the ImageDialog.fxml
            ImageDialog controller = loader.getController();

            // Extract image data from the animal object
            byte[] imageData = animal.getImage();
              // Set the image data in the controller
            controller.setImage(imageData);
             // Create a new stage for the ImageDialog.fxml
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Image Preview");
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handleUpdateAction(animal animal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addanimal.fxml"));
            Parent root = loader.load();
            addanimal controller = loader.getController();
            controller.setAnimal(animal);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Update Animal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteAction(animal animal) {
        try {
            String query = "DELETE FROM animal WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, animal.getId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Animal deleted successfully.");
                loadAnimals();
            } else {
                System.out.println("Failed to delete animal.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static class UpdateButtonCell extends TableCell<animal, Void> {
        private final Button updateButton = new Button("Update");

        UpdateButtonCell(Consumer<animal> updateAction) {
            updateButton.setOnAction(event -> {
                animal animal = getTableView().getItems().get(getIndex());
                updateAction.accept(animal);
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(updateButton);
            }
        }
    }

    private static class DeleteButtonCell extends TableCell<animal, Void> {
        private final Button deleteButton = new Button("Delete");

        DeleteButtonCell(Consumer<animal> deleteAction) {
            deleteButton.setOnAction(event -> {
                animal animal = getTableView().getItems().get(getIndex());
                deleteAction.accept(animal);
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(deleteButton);
            }
        }
    }
}
