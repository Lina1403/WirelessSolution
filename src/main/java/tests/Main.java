/*
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
            nouvelleVoiture.setMarque("Test");
            nouvelleVoiture.setCouleur("Rouge");
            nouvelleVoiture.setMatricule("12345");
            nouvelleVoiture.setNomParking("Test Parking");

            // Ajout de la voiture à la base de données
            serviceVoiture.ajouter(nouvelleVoiture);
            System.out.println("Voiture ajoutée : " + nouvelleVoiture);

            // Modification de la voiture
            nouvelleVoiture.setMarque("Nouvelle marque");
            serviceVoiture.modifier(nouvelleVoiture);
            System.out.println("Voiture modifiée : " + nouvelleVoiture);

            // Récupération de la voiture par son identifiant
            Voiture voitureRecuperee = serviceVoiture.getOneById(nouvelleVoiture.getIdVoiture());
            System.out.println("Voiture récupérée : " + voitureRecuperee);

            // Suppression de la voiture
            serviceVoiture.supprimer(nouvelleVoiture.getIdVoiture());
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

 */
package tests;

import entities.Parking;
import services.ServiceParking;

import java.sql.SQLException;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        ServiceParking serviceParking = new ServiceParking();

        try {

            // Création d'un nouveau parking
            Parking nouveauParking = new Parking();
            nouveauParking.setNom(" Parking 3");
            nouveauParking.setCapacite(100);
            nouveauParking.setType("pleine air");
            nouveauParking.setNombreActuelles(0);

            // Ajout du parking à la base de données
            serviceParking.ajouter(nouveauParking);
            System.out.println("Parking ajouté : " + nouveauParking);


    /*
            // Modification du parking

            Parking parkingModifie = serviceParking.getOneById(parking.getIdParking());
            parkingModifie.setNom("Nouveau Parking");
            parkingModifie.setCapacite(100);
            parkingModifie.setType("pleine air");
            parkingModifie.setNombreActuelles(30);
            serviceParking.modifier(parkingModifie);
            System.out.println("Parking modifié : " + parkingModifie);

            // Récupération du parking par son identifiant pour vérifier la modification
            Parking parkingApresModification = serviceParking.getOneById(parking.getIdParking());
            System.out.println("Parking après modification : " + parkingApresModification);
       */
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*    // Récupération du parking par son identifiant
            Parking parkingRecupere = serviceParking.getOneById(nouveauParking.getIdParking());
            System.out.println("Parking récupéré : " + parkingRecupere);

            // Suppression du parking
            serviceParking.supprimer(nouveauParking.getIdParking());
            System.out.println("Parking supprimé : " + nouveauParking);
*/
            // Affichage de tous les parkings
        Set<Parking> parkings = null;
        try {
            parkings = serviceParking.getAll();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        for (Parking p : parkings) {
                System.out.println(p);
            }

        }
    }



