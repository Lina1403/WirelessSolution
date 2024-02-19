package tests;

import entities.Parking;
import entities.Voiture;
import services.ServiceParking;
import services.ServiceVoiture;
import java.sql.SQLException;
import java.util.Set;

public class Main {

            public static void main(String[] args) throws SQLException {
            /*    ServiceVoiture serviceVoiture = new ServiceVoiture();
                try {
                    // Test de la méthode getOneById(int id)
                    int idToSearch = 166; // Remplacez 1 par l'ID de la voiture que vous souhaitez récupérer
                    Voiture voitureById = serviceVoiture.getOneById(idToSearch);
                    System.out.println("Voiture trouvée par ID : " + voitureById);

                    // Test de la méthode getAll()
                    Set<Voiture> toutesLesVoitures = serviceVoiture.getAll();
                    System.out.println("Liste de toutes les voitures : ");
                    for (Voiture voiture : toutesLesVoitures) {
                        System.out.println(voiture);
                    }
                } catch (SQLException e) {
                    System.out.println("Une erreur s'est produite lors de l'accès à la base de données : " + e.getMessage());
                }


                   // Tester la méthode ajouter
                serviceVoiture.ajouter(voiture);
                System.out.println("Voiture ajoutée : " + voiture);

                // Tester la méthode modifier
                // Créer un objet Voiture avec les valeurs à mettre à jour
                Voiture voiture = new Voiture();
                voiture.setId_voiture(167); // Remplacez 1 par l'ID de la voiture que vous souhaitez mettre à jour
                voiture.setNom("Nouveau nom");
                voiture.setEmail("nouveau@email.com");
                voiture.setNum_serie("Nouveau numéro de série");
                voiture.setMarque("Nouvelle marque");
                voiture.setCouleur("Nouvelle couleur");

// Assurez-vous que la voiture a un objet Parking associé si nécessaire
                Parking parking = new Parking();
                parking.setNumPlace(10); // Remplacez 10 par le numéro de place du parking que vous souhaitez associer
                voiture.setParking(parking);

// Appelez la méthode modifier avec cet objet Voiture
                try {
                    serviceVoiture.modifier(voiture); // Remplacez votreObjetService par l'objet de votre service
                    System.out.println("Modification de la voiture réussie !");
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la modification de la voiture : " + e.getMessage());
                }

               // Tester la méthode getOneById
                Voiture voitureFromDb = serviceVoiture.getOneById(voiture.getId_voiture());
                System.out.println("Voiture récupérée de la base de données : " + voitureFromDb);

                // Tester la méthode getAll
                Set<Voiture> allVoitures = serviceVoiture.getAll();
                System.out.println("Toutes les voitures : " + allVoitures);

                // Tester la méthode supprimer
                serviceVoiture.supprimer(voiture.getId_voiture());
                System.out.println("Voiture supprimée.");

                serviceVoiture.supprimer(167);
                */

                //Ajouter Parking
                ServiceParking SP = new ServiceParking();
                Parking p = new Parking();
                p.setIdParking(2);
                p.setNumPlace(10000);
                p.setPlace("ALI");
                p.setCapacite(1000);
                SP.modifier(p);
                System.out.println(p);

                try {
                    // Test de la méthode getOneById(int id)
                    int idToSearch = 2; // Remplacez 1 par l'ID de la voiture que vous souhaitez récupérer
                    Parking p1 = SP.getOneById(idToSearch);
                    System.out.println("Parking trouvée par ID : " + p1);

                    // Test de la méthode getAll()
                    Set<Parking> listParking = SP.getAll();
                    System.out.println("Liste de toutes les voitures : ");
                    for (Parking p2 : listParking) {
                        System.out.println(p2);
                    }
                } catch (SQLException e) {
                    System.out.println("Une erreur s'est produite lors de l'accès à la base de données : " + e.getMessage());
                }
                SP.supprimer(2);

    }
}




