package tests;

import entities.Appartement;
import entities.Facture;
import services.ServiceFacture;

import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

public class MainCrudFacture {
    public static void main(String[] args) {
        ServiceFacture serviceFacture = new ServiceFacture();
        Facture factureExistante = new Facture();
        factureExistante.setIdFacture(28); // ID de la facture existante
        factureExistante.setNumFacture(123456);
        factureExistante.setDate(new Date());
        factureExistante.setType(Facture.Type.Eau);
        factureExistante.setMontant(100.0f);
        factureExistante.setDescriptionFacture("Facture d'eau");
        Appartement appartement = new Appartement(100); // Appartement existant
        factureExistante.setAppartement(appartement);

        try {
            // Modifier la facture existante
            serviceFacture.modifier(factureExistante);

            // Vérifier si la facture a été mise à jour correctement en récupérant la facture modifiée
            Facture factureModifiee = serviceFacture.getOneById(factureExistante.getIdFacture());

        } catch (SQLException e) {
            System.out.println("An exception occurred while modifying the facture: " + e.getMessage());
        }

       /* try {
            // Créer un objet Facture avec des données appropriées
            Facture facture = new Facture();
            facture.setNumFacture(123);
            facture.setType(Facture.Type.Electricite);
            facture.setMontant(100.0f);
            facture.setDescriptionFacture("Facture d'électricité");
            facture.setDate(new Date());

            // Créer un objet Appartement associé à la facture
            Appartement appartement = new Appartement();
            appartement.setNumAppartement(12); // Numéro d'appartement existant dans votre base de données
            // Vous pouvez définir d'autres propriétés de l'appartement si nécessaire

            // Associer l'appartement à la facture
            facture.setAppartement(appartement);

            // Appeler la méthode ajouter() pour insérer la facture dans la base de données
            serviceFacture.ajouter(facture);
        } catch (
                SQLException e) {
            e.printStackTrace();
            // Gérer l'exception, par exemple en affichant un message d'erreur
            System.err.println("Erreur lors de l'ajout de la facture dans la base de données.");
        }


        try {
            // Appeler la méthode getAll() pour récupérer toutes les factures avec les informations sur les appartements associés
            Set<Facture> factures = serviceFacture.getAll();

            // Parcourir les factures et afficher les détails de chaque facture
            for (Facture facture : factures) {
                System.out.println("Numéro de facture: " + facture.getNumFacture());
                System.out.println("Date: " + facture.getDate());
                System.out.println("Type: " + facture.getType());
                System.out.println("Montant: " + facture.getMontant());
                System.out.println("Description: " + facture.getDescriptionFacture());
                System.out.println("Numéro d'appartement associé: " + facture.getAppartement().getNumAppartement());
                System.out.println("-------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception, par exemple en affichant un message d'erreur
            System.err.println("Erreur lors de la récupération des factures depuis la base de données.");
        }
           */
    }


}
