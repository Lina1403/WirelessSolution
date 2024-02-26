package tests;
import entities.Event;
import entities.Espace;
import services.ServiceEspace;
import services.ServiceEvent;

import java.sql.Time;
import java.util.Date;

import java.sql.SQLException;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        ServiceEspace serviceEspace = new ServiceEspace();
/*
        // Ajout d'un nouvel espace
        Espace nouvelEspace = new Espace();
        nouvelEspace.setName("Salle de lina");
        nouvelEspace.setEtat(Espace.Etat.RESERVE);
        nouvelEspace.setCapacite(20);
        nouvelEspace.setDescription("Salle de réunion équipée avec projecteur et tableau blanc.");
    serviceEspace.ajouter(nouvelEspace);

        // Affichage de tous les espaces
        try {
            Set<Espace> espaces = serviceEspace.getAll();
            System.out.println("Liste des espaces :");
            for (Espace espace : espaces) {
                System.out.println(espace);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des espaces : " + e.getMessage());
        }

        // Modification de l'état de l'espace ajouté
        nouvelEspace.setEtat(Espace.Etat.LIBRE);
        try {
            serviceEspace.modifier(nouvelEspace);
            System.out.println("État de l'espace modifié !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'état de l'espace : " + e.getMessage());
        }

       // Suppression de l'espace ajouté
        try {
            serviceEspace.supprimer(nouvelEspace.getIdEspace());
            System.out.println("Espace supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'espace : " + e.getMessage());
        }
    */
        ServiceEvent Event = new ServiceEvent();



        try {
            // Obtient une connexion à la base de données

            // Crée une instance du service ServiceEvent
            ServiceEvent serviceEvent = new ServiceEvent();

            // Crée un nouvel espace
            Espace espace = new Espace();
        espace.setIdEspace(35);

            // Crée un nouvel événement

// Crée un nouvel événement
            Event event = new Event();
            event.setTitle("lina");
            Date date = java.sql.Date.valueOf("2023-05-26");
            event.setDate(date);
            event.setNbrPersonne(20);
            event.setDescription("A birthday party");
            event.setEspace(espace); // Attribue l'espace à l'événement
            Time heure = Time.valueOf("14:00:00"); // Exemple : 14 heures, 30 minutes et 0 secondes
            // Ajoute l'événement à la base de données
            serviceEvent.ajouter(event);

            // Affiche tous les événements
            Set<Event> events = serviceEvent.getAll();
            for (Event e : events) {
                System.out.println(e);
            }
/*
            // Supprime un événement par son ID
            int eventIdToDelete = 25; // ID de l'événement à supprimer
            serviceEvent.supprimer(eventIdToDelete);
*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
