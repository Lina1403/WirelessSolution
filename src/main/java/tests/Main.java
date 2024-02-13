package tests;

import entities.voiture;
import services.ServiceVoiture;

public class Main {
    public static void main(String[] args) {
        // Create a new instance of ServiceCar
        ServiceVoiture serviceVoiture = new ServiceVoiture();
/*
        // Create a new instance of the Car class with test values
        int id_voiture = 1;
        String nom = "Voiture de test";
        String email = "test@mail.com";
        String num_serie = "123456789";
        String marque = "TestMarque";
        String couleur = "Noir";

        voiture newVoiture = new voiture(id_voiture, nom, email, num_serie, marque, couleur);

        // Add the car to the database
        serviceVoiture.ajouter(newVoiture);
*/
        // Modify the car with id 1 with new values
        String newNom = " adem";
        String newEmail = "linaa@mail.com";
        String newNum_serie = "987654321";
        String newMarque = "NouvelleMarque";
        String newCouleur = "Blanc";

        voiture voitureModif = new voiture(1, newNom, newEmail, newNum_serie, newMarque, newCouleur);

        serviceVoiture.modifier(voitureModif);

        // Retrieve the modified car using the getCarById method
        voiture voitureRecup = serviceVoiture.getvoitureById(1);
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
    }
}
