package landforlife.tn.services;

import landforlife.tn.models.demande;
import landforlife.tn.util.MaConnection;
import landforlife.tn.interfaces.Dinterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class demandeservice implements Dinterface<demande> {
    // Connection object
    private Connection cnx = MaConnection.getInstance().getCnx();

    @Override
    public void add(demande Demande) {
        try {
            String query = "INSERT INTO demande(animal_id, user_id, sujet, details) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, Demande.getAnimal_id());
            statement.setInt(2, Demande.getUser_id());
            statement.setString(3, Demande.getSujet());
            statement.setString(4, Demande.getDetails());
            statement.executeUpdate();
            System.out.println("Demande Added Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(demande Demande) {
        try {
            String query = "UPDATE demande SET animal_id=?, user_id=?, sujet=?, details=? WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, Demande.getAnimal_id());
            statement.setInt(2, Demande.getUser_id());
            statement.setString(3, Demande.getSujet());
            statement.setString(4, Demande.getDetails());
            statement.setInt(5, Demande.getId());
            statement.executeUpdate();
            System.out.println("Demande Updated Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(demande Demande) {
        try {
            String query = "DELETE FROM demande WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, Demande.getId());
            statement.executeUpdate();
            System.out.println("Demande Deleted Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<demande> getAll() {
        List<demande> demandes = new ArrayList<>();
        try {
            String query = "SELECT * FROM demande";
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                demande Demande = new demande();
                Demande.setId(resultSet.getInt("id"));
                Demande.setAnimal_id(resultSet.getInt("animal_id"));
                Demande.setUser_id(resultSet.getInt("user_id"));
                Demande.setSujet(resultSet.getString("sujet"));
                Demande.setDetails(resultSet.getString("details"));
                demandes.add(Demande);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return demandes;
    }

    @Override
    public demande getOne(int id) {
        demande Demande = null;
        try {
            String query = "SELECT * FROM demande WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Demande = new demande();
                Demande.setId(resultSet.getInt("id"));
                Demande.setAnimal_id(resultSet.getInt("animal_id"));
                Demande.setUser_id(resultSet.getInt("user_id"));
                Demande.setSujet(resultSet.getString("sujet"));
                Demande.setDetails(resultSet.getString("details"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Demande;
    }
}
