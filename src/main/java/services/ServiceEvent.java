package services;
import utils.DataSource;
import entities.Event;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceEvent implements IService<Event> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Event e) {
        Connection cnx = DataSource.getInstance().getCnx();
        try {
            PreparedStatement PS = cnx.prepareStatement("INSERT INTO event(name,email,title,date,nbrPersonne,statutEvent,description) VALUES (?,?,?,?,?,?,?)");
            PS.setString(1, e.getName());
            PS.setString(2, e.getEmail());
            PS.setString(3, e.getTitle());
            PS.setDate(4, new java.sql.Date(e.getDate().getTime()));
            PS.setInt(5, e.getNbrPersonne());
            PS.setString(6, e.getStatutEvent());
            PS.setString(7, e.getDescription());
            PS.executeUpdate();
            System.out.println("Event ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public Event getOneByName(String name) {
        Event eventByName = null;

        try {
            PreparedStatement PS = cnx.prepareStatement("SELECT * FROM event WHERE name=?");
            PS.setString(1, name);
            ResultSet res = PS.executeQuery();

            if (res.next()) {
                int idEvent = res.getInt("idEvent");
                String email = res.getString("email");
                String title = res.getString("title");
                Date date = res.getDate("date");
                int nbrPersonne = res.getInt("nbrPersonne");
                String statutEvent = res.getString("statutEvent");
                String description = res.getString("description");
                eventByName = new Event(name, email, title, date, nbrPersonne, statutEvent, description);
                eventByName.setIdEvent(idEvent);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return eventByName;
    }

    public Set<Event> getAll() {
        Set<Event> events = new HashSet<>();

        String req = "SELECT * FROM event";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int idEvent = res.getInt("idEvent");
                String name = res.getString("name");
                String email = res.getString("email");
                String title = res.getString("title");
                Date date = res.getDate("date");
                int nbrPersonne = res.getInt("nbrPersonne");
                String statutEvent = res.getString("statutEvent");
                String description = res.getString("description");
                Event e = new Event( title, name, email, date, nbrPersonne, statutEvent, description);
                events.add(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return events;
    }



    @Override

    public void modifier(Event e) {
        try {
            PreparedStatement PS = cnx.prepareStatement("UPDATE event SET name=?, email=?, title=?, date=?, nbrPersonne=?, statutEvent=?, description=? WHERE idEvent=?");

            PS.setString(1, e.getName());
            PS.setString(2, e.getEmail());
            PS.setString(3, e.getTitle());
            if (e.getDate() != null) { // Vérifiez si la date est null avant de l'utiliser
                PS.setDate(4, new java.sql.Date(e.getDate().getTime()));
            } else {
                PS.setNull(4, java.sql.Types.DATE); // Si la date est null, utiliser la méthode setNull() pour l'ajouter à la base de données
            }
            PS.setInt(5, e.getNbrPersonne());
            PS.setString(6, e.getStatutEvent());
            PS.setString(7, e.getDescription());

            Integer eventId = e.getIdEvent();
            if (eventId != null) { // Vérifier si l'objet e a un identifiant
                PS.setInt(8, eventId.intValue()); // Convertir l'Integer en int
            } else {
                System.out.println("L'objet Event ne possède pas d'identifiant.");
                return; // Sortir de la méthode si l'objet n'a pas d'identifiant
            }

            PS.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(String name) {
        try {
            PreparedStatement PS = cnx.prepareStatement("DELETE FROM event WHERE name=?");
            PS.setString(1, name);
            PS.executeUpdate();
            System.out.println("Événement supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



}
