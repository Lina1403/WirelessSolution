
package tests;

import entities.Parking;
import entities.Voiture;
import services.ServiceParking;
import services.ServiceVoiture;

import java.util.Set;

public class Main {
    public static void main(String[] args) {

        //ajout voiture

        // Create a new instance of ServiceVoiture
        ServiceVoiture serviceVoiture = new ServiceVoiture();

        // Create a new instance of the Car class with test values
        int id_voiture = 1;
        String nom = "Voiture de test";
        String email = "test@mail.com";
        String num_serie = "123456789";
        String marque = "TestMarque";
        String couleur = "Noir";

        Voiture newVoiture = new Voiture(id_voiture, nom, email, num_serie, marque, couleur);

        // Add the car to the database
        serviceVoiture.ajouter(newVoiture);

        // Modify the car with id 1 with new values
        String newNom = "adem";
        String newEmail = "linaa@mail.com";
        String newNum_serie = "987654321";
        String newMarque = "NouvelleMarque";
        String newCouleur = "Blanc";

        Voiture voitureModif = new Voiture(1, newNom, newEmail, newNum_serie, newMarque, newCouleur);

        //modifier voiture
        serviceVoiture.modifier(voitureModif);

        // Retrieve the modified car using the getCarById method
        Voiture voitureRecup = serviceVoiture.getvoitureById(1);
        if (voitureRecup != null) {
            // Verify if the car has been modified successfully
            System.out.println(voitureRecup.getId_voiture() + ", "
                    + voitureRecup.getNom() + ", "
                    + voitureRecup.getEmail() + ", "
                    + voitureRecup.getNum_serie() + ", "
                    + voitureRecup.getMarque() + ", "
                    + voitureRecup.getCouleur());
        } else {
            System.out.println("Car with ID 1 not found");
        }

        //ajout parking

        // Create a new instance of ServiceParking
        ServiceParking serviceParking = new ServiceParking();

        // Create a new instance of the parking class with test values
        int id_parking = 4;
        String place = "Test Parking";
        int nbr_place = 50;
        int capacite_parking = 200;

        Parking newParking = new Parking(id_parking, place, nbr_place, capacite_parking);

        // Add the parking to the database
        serviceParking.ajouter(newParking);

        /// Modify the parking with id 1 with new values
        int idModif = 2 ;
        String newPlace = "New Parking";
        int newNbr_place = 60;
        int newCapacite_parking = 200;

        // Create an instance of ServiceParking
        ServiceParking ServiceParking = new ServiceParking();

        // Create a modified parking object
        Parking  ParkingModif = new Parking (idModif, newPlace, newNbr_place, newCapacite_parking );

        // Call the modifier method
        serviceParking .modifier(ParkingModif);

        // Retrieve the modified Parking  using the getParking ById method
        Parking  ParkingRecup = serviceParking .getParkingById(1);
        if (ParkingRecup != null) {
            // Verify if the Parking  has been modified successfully
            System.out.println(ParkingRecup.getId_parking () + ", "
                    + ParkingRecup.getPlace() + ", "
                    + ParkingRecup.getNbr_place() + ", "
                    + ParkingRecup.getCapacite_parking ());
        } else {
            System.out.println("Parking with ID 1 not found");
        }

        // Afficher tous les parkings

        Set<Parking> parkings = serviceParking.getAll();
        for (Parking p : parkings) {
            System.out.println(p);
        }

        // Supprimer un parking
        int id = 1;
        serviceParking.supprimer(id);

    }


}
