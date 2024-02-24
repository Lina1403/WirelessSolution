package services;

import entities.Espace;
import entities.Event;
import utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceEvent implements IService<Event> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Event event) throws SQLException {
        // Vérifier si l'espace est occupé dans cette date
        if (isEspaceOccupied(event.getDate())) {
            throw new IllegalArgumentException("L'espace est déjà occupé à cette date.");
        }

        String req = "INSERT INTO `event` (`title`, `date`, `nbrPersonne`, `description`, `idEspace`, `heure`) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, event.getTitle());
            pstmt.setTimestamp(2, new Timestamp(event.getDate().getTime()));
            pstmt.setInt(3, event.getNbrPersonne());
            pstmt.setString(4, event.getDescription());
            pstmt.setInt(5, event.getEspace().getIdEspace());

            // Vérifier si l'heure n'est pas nulle avant de l'ajouter à la requête
            if (event.getHeure() != null) {
                pstmt.setTime(6, new Time(event.getHeure().getTime()));
            } else {
                pstmt.setNull(6, Types.TIME); // Ajouter une valeur null pour l'heure
            }

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    event.setIdEvent(rs.getInt(1));
                }
                System.out.println("Event added !");
            } else {
                System.out.println("Failed to insert Event.");
            }
        }
    }

    private boolean isEspaceOccupied(java.util.Date date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM event WHERE date = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setTimestamp(1, new Timestamp(date.getTime()));
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    public Espace getEspaceByName(String name) throws SQLException {
        String req = "SELECT * FROM espace WHERE name = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Espace espace = new Espace();
                    espace.setIdEspace(rs.getInt("idEspace"));
                    espace.setName(rs.getString("name"));
                    // Ajoutez ici d'autres attributs de l'espace si nécessaire
                    return espace;
                }
            }
        }
        return null; // Retourne null si aucun espace avec ce nom n'est trouvé
    }


    public Set<String> getAllEspacesNames() throws SQLException {
        Set<String> espacesNames = new HashSet<>();
        String req = "SELECT name FROM espace";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                espacesNames.add(rs.getString("name"));
            }
        }

        return espacesNames;
    }





    @Override
    public Set<Event> getAll() throws SQLException {
        Set<Event> events = new HashSet<>();
        String req = "SELECT e.*, espace.name AS name " +
                "FROM event e " +
                "INNER JOIN espace ON e.idEspace = espace.idEspace";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Event event = new Event();
                event.setIdEvent(rs.getInt("idEvent"));
                event.setTitle(rs.getString("title"));
                event.setDate(rs.getDate("date")); // Utilisation directe de rs.getDate()
                event.setNbrPersonne(rs.getInt("nbrPersonne"));
                event.setDescription(rs.getString("description"));
                event.setHeure(rs.getTime("heure"));

                // Création de l'objet Espace et assignation au nouvel événement
                Espace espace = new Espace();
                espace.setIdEspace(rs.getInt("idEspace"));
                espace.setName(rs.getString("name"));
                event.setEspace(espace);

                events.add(event);
            }
        }

        return events;
    }


    @Override
    public void modifier(Event event) throws SQLException {
        // Vous pouvez implémenter la modification de l'événement selon vos besoins ici
        // Assurez-vous de vérifier la contrainte de date pour l'occupation de l'espace
        // Exemple de mise à jour d'un événement :
        String req = "UPDATE event SET title = ?, date = ?, nbrPersonne = ?, description = ?, heure = ? WHERE idEvent = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setString(1, event.getTitle());
            pstmt.setDate(2, new java.sql.Date(event.getDate().getTime()));
            pstmt.setInt(3, event.getNbrPersonne());
            pstmt.setString(4, event.getDescription());
            pstmt.setTime(5, new Time(event.getHeure().getTime()));
            pstmt.setInt(6, event.getIdEvent());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Event updated successfully.");
            } else {
                System.out.println("Failed to update event.");
            }
        }
    }

    @Override
    public void supprimer(int idEvent) throws SQLException {
        String req = "DELETE FROM event WHERE idEvent = ?";
        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, idEvent);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Event deleted !");
            } else {
                System.out.println("No event found with ID: " + idEvent);
            }
        } catch (SQLException e) {
            throw new SQLException("Error deleting event", e);
        }
    }

    @Override
    public Event getOneById(int id) throws SQLException {
        Event event = null;
        String req = "SELECT e.*, espace.name AS espace_name " +
                "FROM event e " +
                "INNER JOIN espace ON e.idEspace = espace.idEspace " +
                "WHERE e.idEvent = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    event = new Event();
                    event.setIdEvent(rs.getInt("idEvent"));
                    event.setTitle(rs.getString("title"));
                    event.setDate(rs.getDate("date"));
                    event.setNbrPersonne(rs.getInt("nbrPersonne"));
                    event.setDescription(rs.getString("description"));
                    event.setHeure(rs.getTime("heure"));

                    // Création de l'objet Espace et assignation à l'événement
                    Espace espace = new Espace();
                    espace.setIdEspace(rs.getInt("idEspace"));
                    espace.setName(rs.getString("espace_name"));
                    event.setEspace(espace);
                }
            }
        }

        return event;
    }
}
