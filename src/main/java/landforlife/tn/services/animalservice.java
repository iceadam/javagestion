package landforlife.tn.services;

import landforlife.tn.models.animal;
import landforlife.tn.util.MaConnection;
import landforlife.tn.interfaces.Ainterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class animalservice implements Ainterface<animal> {
    // Connection object
    private final Connection cnx = MaConnection.getInstance().getCnx();

    @Override
    public void add(animal Animal) {
        try {
            String query = "INSERT INTO animal(name, age, species, adoption_status, description, image) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, Animal.getName());
            statement.setInt(2, Animal.getAge());
            statement.setString(3, Animal.getSpecies());
            statement.setString(4, Animal.getAdoption_status());
            statement.setString(5, Animal.getDescription());
            statement.setBytes(6, Animal.getImage());
            statement.executeUpdate();
            System.out.println("Animal Added Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(animal Animal) {
        try {
            String query = "UPDATE animal SET name=?, age=?, species=?, adoption_status=?, description=?, image=? WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, Animal.getName());
            statement.setInt(2, Animal.getAge());
            statement.setString(3, Animal.getSpecies());
            statement.setString(4, Animal.getAdoption_status());
            statement.setString(5, Animal.getDescription());
            statement.setBytes(6, Animal.getImage());
            statement.setInt(7, Animal.getId());
            statement.executeUpdate();
            System.out.println("Animal Updated Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(animal Animal) {
        try {
            String query = "DELETE FROM animal WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, Animal.getId());
            statement.executeUpdate();
            System.out.println("Animal Deleted Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<animal> getAll() {
        List<animal> animals = new ArrayList<>();
        try {
            String query = "SELECT * FROM animal";
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                animal Animal = new animal();
                Animal.setId(resultSet.getInt("id"));
                Animal.setName(resultSet.getString("name"));
                Animal.setAge(resultSet.getInt("age"));
                Animal.setSpecies(resultSet.getString("species"));
                Animal.setAdoption_status(resultSet.getString("adoption_status"));
                Animal.setDescription(resultSet.getString("description"));
                Animal.setImage(resultSet.getBytes("image"));
                animals.add(Animal);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return animals;
    }

    @Override
    public animal getOne(int id) {
        animal Animal = null;
        try {
            String query = "SELECT * FROM animal WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Animal = new animal();
                Animal.setId(resultSet.getInt("id"));
                Animal.setName(resultSet.getString("name"));
                Animal.setAge(resultSet.getInt("age"));
                Animal.setSpecies(resultSet.getString("species"));
                Animal.setAdoption_status(resultSet.getString("adoption_status"));
                Animal.setDescription(resultSet.getString("description"));
                Animal.setImage(resultSet.getBytes("image"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Animal;
    }
}
