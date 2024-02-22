package services;
import entities.Espace;
import utils.DataSource;
import entities.Event;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceEvent implements IService<Event> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Event event) {

        String req = "INSERT INTO `event` (`name`, `email`, `title`, `date`, `nbrPersonne`, `description`) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, event.getName());
            pstmt.setString(2, event.getEmail());
            pstmt.setString(3, event.getTitle());
            pstmt.setDate(4, new java.sql.Date(event.getDate().getTime()));
            pstmt.setInt(5, event.getNbrPersonne());
            pstmt.setString(6, event.getDescription());


            pstmt.executeUpdate();
            System.out.println("Event added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }







    @Override
    public Set<Event> getAll() {
        Set<Event> events = new HashSet<>();
        String req = "SELECT * FROM `event`";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Event event = new Event();
                event.setIdEvent(rs.getInt("idEvent"));
                event.setName(rs.getString("name"));
                event.setEmail(rs.getString("email"));
                event.setTitle(rs.getString("title"));
                event.setDate(rs.getDate("date"));
                event.setNbrPersonne(rs.getInt("nbrPersonne"));
                event.setDescription(rs.getString("description"));

                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }






    @Override
    public void modifier(Event event) {
        String req = "UPDATE `event` SET " +
                "`name`='" + event.getName() + "'," +
                "`email`='" + event.getEmail() + "'," +
                "`title`='" + event.getTitle() + "'," +
                "`date`='" + new java.sql.Date(event.getDate().getTime()) + "'," + // Correction de l'ajout de la date
                "`nbrPersonne`='" + event.getNbrPersonne() + "'," +
                "`description`='" + event.getDescription() + "'," +
                "WHERE `idEvent`=" + event.getIdEvent();
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Event updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void supprimer(int idEvent) {
        String req = "DELETE FROM event WHERE idEvent = ?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, idEvent);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Event deleted !");
            } else {
                System.out.println("No event found with ID: " + idEvent);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Event getOneById(int id) throws SQLException {
        Event eventById = null;

        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM event WHERE idEvent=?");
        ps.setInt(1, id);
        ResultSet res = ps.executeQuery();

        if (res.next()) {
            eventById = new Event();
            eventById.setIdEvent(res.getInt("idEvent"));
            eventById.setName(res.getString("name"));
            eventById.setEmail(res.getString("email"));
            eventById.setTitle(res.getString("title"));
            eventById.setDate(res.getDate("date"));
            eventById.setNbrPersonne(res.getInt("nbrPersonne"));
            eventById.setDescription(res.getString("description"));
            // Assurez-vous d'ajouter l'attribut numEspace si vous le souhaitez
        }

        return eventById;
    }


}



