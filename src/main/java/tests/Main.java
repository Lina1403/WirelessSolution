package tests;

import entities.Voiture;
import services.ServiceVoiture;

import java.sql.SQLException;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        ServiceVoiture serviceVoiture = new ServiceVoiture();

        try {
            // Création d'une nouvelle voiture
            Voiture nouvelleVoiture = new Voiture();
            nouvelleVoiture.setNom("lina Test");
            nouvelleVoiture.setEmail("test@test.com");
            nouvelleVoiture.setNum_serie("12345");
            nouvelleVoiture.setMarque("Test");
            nouvelleVoiture.setCouleur("Rouge");
            nouvelleVoiture.setNumPlace(4);

            // Ajout de la voiture à la base de données
            serviceVoiture.ajouter(nouvelleVoiture);
            System.out.println("Voiture ajoutée : " + nouvelleVoiture);

            // Modification de la voiture
            nouvelleVoiture.setMarque("Nouvelle marque");
            serviceVoiture.modifier(nouvelleVoiture);
            System.out.println("Voiture modifiée : " + nouvelleVoiture);

            // Récupération de la voiture par son identifiant
            Voiture voitureRecuperee = serviceVoiture.getOneById(nouvelleVoiture.getId_voiture());
            System.out.println("Voiture récupérée : " + voitureRecuperee);

            // Suppression de la voiture
            serviceVoiture.supprimer(nouvelleVoiture.getId_voiture());
            System.out.println("Voiture supprimée : " + nouvelleVoiture);

            // Affichage de toutes les voitures
            Set<Voiture> voitures = serviceVoiture.getAll();
            for (Voiture v : voitures) {
                System.out.println(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
