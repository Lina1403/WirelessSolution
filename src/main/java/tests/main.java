package tests;

import entities.Espace;
import entities.Event;
import services.ServiceEspace;
import services.ServiceEvent;
import java.sql.SQLException;
import java.util.*;

public class main {

    public static void main(String[] args) throws SQLException {
        ServiceEvent serviceEvent = new ServiceEvent();
        ServiceEspace serviceEspace = new ServiceEspace();

        Espace e = new Espace();


    // Création des événements
        Event e1 = new Event("Birthday", "lina@lina", "event", new Date(), 20, "aaaa", e);
        System.out.println(e1);
        // Ajout des événements à la base de données
        serviceEvent.ajouter(e1);

        // Affichage de confirmation
        System.out.println("Events added successfully!");


    // Création d'un nouvel espace
        Espace nouvelEspace = new Espace();
        nouvelEspace.setName("Salle de réunion");
        nouvelEspace.setEtat("Libre");
        nouvelEspace.setCapacite(20);
        nouvelEspace.setDescription("Salle de réunion équipée avec projecteur et tableau blanc.");
        nouvelEspace.setNumEspace(1); // Assurez-vous d'avoir un numéro d'espace valide

        // Ajout de l'espace à la base de données
        serviceEspace.ajouter(nouvelEspace);

        // Affichage de l'espace ajouté
     /*   try {
            System.out.println("Espace ajouté : " + serviceEspace.getOneById(nouvelEspace.getIdEspace()));
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la récupération de l'espace ajouté : " + e.getMessage());
        }
    */
        //test de getALL
        Set<Event> setEvents =new HashSet<>(serviceEvent.getAll());
        for (Event a : setEvents){
            System.out.println(a);
        }
    }
}









