package landforlife.tn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import landforlife.tn.models.demande;
import landforlife.tn.services.demandeservice;

public class demandeform {

    @FXML
    private TextField sujetField;

    @FXML
    private TextArea detailsArea;

    @FXML
    private Label sujetErrorLabel;

    @FXML
    private Label detailsErrorLabel;

    private final demandeservice demandeService = new demandeservice();

    @FXML
    private Label animalIdLabel;

    public void setAnimalId(int animalId) {
        animalIdLabel.setText(String.valueOf(animalId));
    }

    @FXML
    private void submitDemande() {
        // Reset error messages
        sujetErrorLabel.setText("");
        detailsErrorLabel.setText("");

        // Retrieve input values
        String sujet = sujetField.getText();
        String details = detailsArea.getText();

        // Validate input


        // Get the animal ID from the label
        int animalId = Integer.parseInt(animalIdLabel.getText());

        // Assuming you have the user_id available
        int user_id = 1; // Replace with the actual user_id

        // Create demande object
        demande demande = new demande();
        demande.setUser_id(user_id);
        demande.setAnimal_id(animalId);
        demande.setSujet(sujet);
        demande.setDetails(details);

        // Add demande to database
        demandeService.add(demande);

        // Optionally, you can close the form after submission
        sujetField.getScene().getWindow().hide();
    }


}
