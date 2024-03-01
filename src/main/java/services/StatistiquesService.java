package services;

import entities.Appartement;
import entities.Facture;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StatistiquesService {

    public static float calculerConsommationEnergie(List<Appartement> appartements, Date dateDebut, Date dateFin) {
        // Filtrer les factures d'énergie dans la plage de dates spécifiée
        List<Facture> facturesEnergie = appartements.stream()
                .flatMap(appartement -> appartement.getFactures().stream())
                .filter(facture -> facture.getType() == Facture.Type.Electricite ||
                        facture.getType() == Facture.Type.Gaz)
                .filter(facture -> facture.getDate().after(dateDebut) && facture.getDate().before(dateFin))
                .collect(Collectors.toList());

        // Calculer la somme des montants des factures d'énergie
        float consommationEnergie = 0;
        for (Facture facture : facturesEnergie) {
            consommationEnergie += facture.getMontant();
        }

        return consommationEnergie;
    }

    // Ajoutez d'autres méthodes pour d'autres critères de statistiques si nécessaire
}
