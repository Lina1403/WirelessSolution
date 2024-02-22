package tests;

import entities.Espace;
import services.ServiceEspace;
import java.sql.SQLException;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        ServiceEspace serviceEspace = new ServiceEspace();

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
    }
}
