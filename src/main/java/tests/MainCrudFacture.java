package tests;

import entities.Appartement;
import entities.Facture;
import services.ServiceFacture;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class MainCrudFacture {
    public static void main(String[] args) {
        testEnergyConsumption();
        // Ajoutez d'autres méthodes de test ici si nécessaire
    }

    public static void testEnergyConsumption() {
        ServiceFacture serviceFacture = new ServiceFacture();

        // Supposons que les dates de début et de fin soient le 1er janvier 2024 et le 1er février 2024
        Date startDate = new Date(2024, 0, 1); // 0 représente janvier
        Date endDate = new Date(2024, 12, 1); // 1 représente février

        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());


        try {
            // Test de la consommation d'énergie par étage
            System.out.println("Energy consumption by floor:");
            Map<String, Float> resultByFloor = serviceFacture.getEnergyConsumption("etage", sqlStartDate, sqlEndDate);
            System.out.println(resultByFloor);

            // Test de la consommation d'énergie totale
            System.out.println("Total energy consumption:");
            Map<String, Float> resultTotal = serviceFacture.getEnergyConsumption("all", sqlStartDate, sqlEndDate);
            System.out.println(resultTotal);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

