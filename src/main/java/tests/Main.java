package tests;

import entities.Parking;
import entities.Voiture;
import services.ServiceParking;
import services.ServiceVoiture;
import java.sql.SQLException;
import java.util.Set;

public class Main {


    public static void main(String[] args) {
      ServiceParking serviceParking = new ServiceParking();

        ServiceVoiture sv=new ServiceVoiture();

        try {

            // Création d'un nouveau parking
            Parking nouveauParking = new Parking("PARKING adem", 2, "sous-sol", 1);
            serviceParking.ajouter(nouveauParking);

          //  System.out.println("Parking ajouté : " + nouveauParking);
/*
                 // Modification du parking ajouté
            nouveauParking.setNom("PARKING ");
            nouveauParking.setCapacite(200);
            nouveauParking.setType("couverte");
            nouveauParking.setNombreActuelles(30);

            serviceParking.modifier(nouveauParking);
            System.out.println("Parking modifié !");

            // Récupération du parking modifié pour vérification
            Parking parkingModifie = serviceParking.getOneById(nouveauParking.getIdParking());
            System.out.println("Parking après modification : " + parkingModifie);

             // SUPPRIMER PARKING
            int idParkingASupprimer = nouveauParking.getIdParking();
            serviceParking.supprimer(8);
            System.out.println("Parking supprimé !");
*/
            // AFFICHER LISTE PARKING
            Set<Parking> parkings = serviceParking.getAll();
            System.out.println("Liste des parkings :");
            for (Parking p : parkings) {
                System.out.println(p);
            }




        // Création d'un nouveau parking pour associer une voiture
        //Parking nouveauParking = new Parking("PARKING adem", 2, "sous-sol", 10);
      // serviceParking.ajouter(nouveauParking);


 /*
     //AJOUTER VOITURE

        // Vérifiez si le parking existe déjà
        Parking parking = serviceParking.getOneById(10); // Remplacez idParking par l'identifiant du parking
        if (parking == null) {
            System.out.println("Le parking n'existe pas"); }
        // Création d'une nouvelle voiture associée à un parking existant
        Voiture nouvelleVoiture = new Voiture(20,
                "lina",
                "adem",
                "adem",
                "ABC123",
                parking);
        serviceVoiture.ajouter(nouvelleVoiture);
        System.out.println("Voiture ajoutée : " + nouvelleVoiture);
*/

/*
     //  MODIFIER VOITURE

        // Obtention d'une voiture existante par son identifiant
        int idVoitureAModifier = 7; // Remplacez ceci par l'identifiant de la voiture que vous souhaitez modifier
        Voiture voitureAModifier = serviceVoiture.getOneById(idVoitureAModifier);

        // Vérification si la voiture existe
        if (voitureAModifier != null) {
            // Affichage de la voiture avant modification
            System.out.println("Voiture avant modification : " + voitureAModifier);

            // Modification de la voiture
            voitureAModifier.setMarque("mercedes");
            voitureAModifier.setModel("classe g");
            voitureAModifier.setCouleur("noir");
            voitureAModifier.setMatricule("g1");


            // Appel de la méthode modifier pour mettre à jour la voiture dans la base de données
            serviceVoiture.modifier(voitureAModifier);
            System.out.println("Voiture modifiée : " + voitureAModifier);
        } else {
            System.out.println("La voiture avec l'identifiant " + idVoitureAModifier + " n'existe pas.");
        }
*/
/*
   // SUPPRIMER VOITURE

        // Obtention d'une voiture existante par son identifiant
        int idVoitureASupprimer = 6; // Remplacez ceci par l'identifiant de la voiture que vous souhaitez supprimer
        Voiture voitureASupprimer = serviceVoiture.getOneById(idVoitureASupprimer);

        // Vérification si la voiture existe
        if (voitureASupprimer != null) {


            // Appel de la méthode supprimer pour supprimer la voiture de la base de données
            serviceVoiture.supprimer(idVoitureASupprimer);
            System.out.println("Voiture supprimée avec succès.");
        } else {
            System.out.println("La voiture avec l'identifiant " + idVoitureASupprimer + " n'existe pas.");
        }

*/
/*
        // AFFICHER LISTE VOITURE
        // Obtention de toutes les voitures
        Set<Voiture> voitures = serviceVoiture.getAll();

        // Affichage de toutes les voitures
        System.out.println("Liste des voitures :");
        for (Voiture v : voitures) {
            System.out.println(v);
        }
*/
    } catch (SQLException e) {
        System.out.println("Erreur lors de l'opération sur les voitures : " + e.getMessage());
    }
}

}

