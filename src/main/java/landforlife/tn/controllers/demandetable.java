package landforlife.tn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import landforlife.tn.models.demande;
import landforlife.tn.services.demandeservice;

import java.util.List;

public class demandetable {

    @FXML
    private TableView<demande> demandeTable;

    @FXML
    private TableColumn<demande, Integer> idColumn;

    @FXML
    private TableColumn<demande, Integer> animal_idColumn;

    @FXML
    private TableColumn<demande, Integer> user_idColumn;

    @FXML
    private TableColumn<demande, String> sujetColumn;

    @FXML
    private TableColumn<demande, String> detailsColumn;

    private final demandeservice demandeService = new demandeservice();

    @FXML
    private void initialize() {
        // Initialize TableView columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        animal_idColumn.setCellValueFactory(new PropertyValueFactory<>("animal_id"));
        user_idColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        sujetColumn.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));

        // Load data into TableView
        loadData();
    }

    private void loadData() {
        // Retrieve demandes from the service
        List<demande> demandes = demandeService.getAll();

        // Add demandes to TableView
        demandeTable.getItems().addAll(demandes);
    }
}
