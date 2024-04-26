package landforlife.tn.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.Base64;
import java.util.List;

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
    private Connection connection = MaConnection.getInstance().getCnx();

    @FXML
    private void initialize() {
        // Initialize columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        speciesColumn.setCellValueFactory(new PropertyValueFactory<>("species"));
        adoption_statusColumn.setCellValueFactory(new PropertyValueFactory<>("adoption_status"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        imageColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    animal selectedAnimal = getTableView().getItems().get(getIndex());
                    byte[] imageData = selectedAnimal.getImage();
                    if (imageData != null) {
                        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                        Image image = new Image(bis);
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(100); // Set desired width
                        imageView.setPreserveRatio(true);
                        setGraphic(imageView);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });
        UpdateColumn.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> {
                    animal selectedAnimal = getTableView().getItems().get(getIndex());
                    handleUpdateAction(selectedAnimal);
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
        });
        DeleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    animal selectedAnimal = getTableView().getItems().get(getIndex());
                    handleDeleteAction(selectedAnimal);
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
                a.setImage(resultSet.getBytes("image"));

                animals.add(a);
            }
            animalTable.getItems().setAll(animals);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleUpdateAction(animal animal) {
        if (animal != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addanimal.fxml"));
            Parent root;
            try {
                root = loader.load();
                addanimal controller = loader.getController();

                // Encode image byte array to Base64 string
                String encodedImage = Base64.getEncoder().encodeToString(animal.getImage());

                // Set the animal and encoded image for update in the addanimal controller
                controller.setAnimal(animal, encodedImage);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No animal selected for updating.");
        }
    }




    private void handleDeleteAction(animal animal) {
        if (animal != null) {
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
        } else {
            System.out.println("No animal selected for deletion.");
        }
    }
}
