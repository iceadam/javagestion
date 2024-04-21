package landforlife.tn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnection {

    //DB PARAM
    static final String URL ="jdbc:mysql://localhost:3306/esprit";
    static final String USER ="root";
    static final String PASSWORD ="";

    //var
    private Connection cnx;
    //1
    static MaConnection instance;

    //const
    //2
    public MaConnection(){
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return null;
    }

    public Connection getCnx() {
        return cnx;
    }

    //3
    public static MaConnection getInstance() {
        if(instance == null)
            instance = new MaConnection();

        return instance;
    }



}
