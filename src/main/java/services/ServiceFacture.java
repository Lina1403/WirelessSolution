package services;

import entities.Appartement;
import entities.Facture;
import utils.DataSource;

import javax.management.Query;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;


public class ServiceFacture implements IService<Facture>{
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Facture p) throws SQLException {
        if (p.getAppartement() == null) {
            throw new IllegalArgumentException("Appartement object is null in the provided Facture object");
        }
        if (p == null) {
            throw new IllegalArgumentException("Facture object is null");
        }

        if (p.getDate() == null) {
            throw new IllegalArgumentException("Date is null in the provided Facture object");
        }

        // Récupérer l'idAppartement correspondant au numAppartement de la facture
        int idAppartement = getIdAppartementByNumAppartement(p.getAppartement().getNumAppartement());

        if (idAppartement == -1) {
            throw new IllegalArgumentException("Appartement with numAppartement " + p.getAppartement().getNumAppartement() + " does not exist.");
        }

        String sql = "INSERT INTO facture (numFacture, type, montant, descriptionFacture, date, idAppartement) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, p.getNumFacture());
            statement.setString(2, p.getType().toString());
            statement.setFloat(3, p.getMontant());
            statement.setString(4, p.getDescriptionFacture());
            statement.setDate(5, new java.sql.Date(p.getDate().getTime()));
            // Ne pas spécifier idAppartement ici, il sera déduit de la relation de clé étrangère
            statement.setInt(6, idAppartement); // Cela peut être commenté ou supprimé

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                while (rs.next()) {
                    p.setIdFacture(rs.getInt(1));
                }
                System.out.println("Facture added successfully.");
            } else {
                System.out.println("Failed to insert Facture.");
            }
        }
    }
    private int getIdAppartementByNumAppartement(int numAppartement) throws SQLException {
        String sql = "SELECT idAppartement FROM appartement WHERE numAppartement = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, numAppartement);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idAppartement");
                }
            }
        }
        return -1; // Retourne -1 si aucun appartement correspondant n'est trouvé
    }


 

   @Override
   public void modifier(Facture p) {
       String req = "UPDATE `facture` SET " +
               "`numFacture`='" + p.getNumFacture() + "'," +
               "`date`='" + new java.sql.Date(p.getDate().getTime()) + "'," +
               "`type`='" + p.getType() + "'," +
               "`montant`='" + p.getMontant() + "'," +
               "`descriptionFacture`='" + p.getDescriptionFacture() + "' " +
               "WHERE `idFacture`=" + p.getIdFacture();

       try {
           Statement st = cnx.createStatement();
           st.executeUpdate(req);
           System.out.println("Facture updated !");
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }

    @Override
    public void supprimer(int idFacture) throws SQLException {
        String req = "DELETE FROM `facture` WHERE `idFacture`=?";

        try (PreparedStatement statement = cnx.prepareStatement(req)) {
            statement.setInt(1, idFacture);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Facture deleted !");
            } else {
                System.out.println("No facture found with ID: " + idFacture);
            }
        } catch (SQLException e) {
            // Gérer l'exception, par exemple en lançant une nouvelle exception ou en la journalisant
            throw new SQLException("Erreur lors de la suppression de la facture", e);
        }
    }
    @Override
    public Facture getOneById(int id) throws SQLException {
        Facture facture = null;
        String req = "SELECT * FROM `facture` WHERE `idFacture`=?";

        try {
            if (cnx == null) {
                throw new SQLException("Connection is null");
            }

            System.out.println("Executing SQL query: " + req);  // Ajout de cette ligne

            try (PreparedStatement statement = cnx.prepareStatement(req)) {
                statement.setInt(1, id);

                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        // Créez une instance de Facture en utilisant les données récupérées de la base de données
                        facture = new Facture();

                        facture.setIdFacture(id); // Set the ID
                        facture.setNumFacture(rs.getInt("numFacture"));
                        facture.setDate(rs.getDate("date"));

                        // Gestion de la conversion de la chaîne en Facture.Type
                        try {
                            Facture.Type typeFacture = Facture.Type.valueOf(rs.getString("type"));
                            facture.setType(typeFacture);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid type value in the database: " + e.getMessage());
                            // Gérer cette situation de manière appropriée (peut-être attribuer un type par défaut)
                        }

                        facture.setMontant(rs.getFloat("montant"));
                        facture.setDescriptionFacture(rs.getString("descriptionFacture"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in getOneById: " + e.getMessage());
            throw e; // Rethrow the exception for the calling method to handle
        }

        System.out.println("Retrieved Facture: " + facture);
        return facture;
    }


   /* @Override
    public Facture getOneById(int id) throws SQLException {
        Facture facture = null;
        String req = "SELECT * FROM `facture` WHERE `idFacture`=?";

        try (PreparedStatement statement = cnx.prepareStatement(req)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // Créez une instance de Facture en utilisant les données récupérées de la base de données
                    facture = new Facture();

                    facture.setIdFacture(id); // Set the ID
                    facture.setNumFacture(rs.getInt("numFacture"));
                    facture.setDate(rs.getDate("date"));
                    Facture.Type typeFacture = Facture.Type.valueOf(rs.getString("type"));
                    facture.setType(typeFacture);
                    facture.setMontant(rs.getFloat("montant"));
                    facture.setDescriptionFacture(rs.getString("descriptionFacture"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in getOneById: " + e.getMessage());
            throw e; // Rethrow the exception for the calling method to handle
        }

        System.out.println("Retrieved Facture: " + facture);
        return facture;
    } */




    @Override
    public Set<Facture> getAll() throws SQLException {
        Set<Facture> factures = new HashSet<>();
        String req = "SELECT f.*, a.numAppartement " +
                "FROM facture f " +
                "JOIN appartement a ON f.idAppartement = a.idAppartement";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                // Créez une instance de Facture pour chaque ligne de résultat
                Facture facture = new Facture();
                facture.setNumFacture(rs.getInt("numFacture"));
                facture.setDate(rs.getDate("date"));
                Facture.Type typeFacture = Facture.Type.valueOf(rs.getString("type"));
                facture.setType(typeFacture);
                facture.setMontant(rs.getFloat("montant"));
                facture.setDescriptionFacture(rs.getString("descriptionFacture"));

                // Créez une instance d'Appartement et définissez son numéro d'appartement
                Appartement appartement = new Appartement();
                appartement.setNumAppartement(rs.getInt("numAppartement"));

                // Associez l'appartement à la facture
                facture.setAppartement(appartement);

                // Ajoutez la facture à l'ensemble factures
                factures.add(facture);
            }
        }

        return factures;
    }
    public Set<Facture> rechercherFactures(int numFacture) throws SQLException {
        Set<Facture> factures = new HashSet<>();
        String req = "SELECT * FROM `facture` WHERE `numFacture` = ? ";

        try (PreparedStatement statement = cnx.prepareStatement(req)) {
            statement.setInt(1, numFacture);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Facture facture = new Facture();
                    facture.setIdFacture(rs.getInt("idFacture"));
                    facture.setNumFacture(rs.getInt("numFacture"));
                    facture.setDate(rs.getDate("date"));
                    facture.setType(Facture.Type.valueOf(rs.getString("type")));
                    facture.setMontant(rs.getFloat("montant"));
                    facture.setDescriptionFacture(rs.getString("descriptionFacture"));

                    factures.add(facture);
                }
            }
        }

        return factures;
    }
}




