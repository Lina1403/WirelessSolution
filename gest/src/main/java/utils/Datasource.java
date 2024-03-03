package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Datasource {

    private String url = "jdbc:mysql://localhost:3306/gestuser";
    private String user = "root";
    private String passwd = "";

    private Connection cnx;

    private static Datasource instance;

    private Datasource(){
        try {
            cnx = DriverManager.getConnection(url,user,passwd);
            System.out.println("Connected to DB !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Datasource getInstance(){
        if(instance == null){
            instance = new Datasource();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

}
