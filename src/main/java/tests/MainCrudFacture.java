package tests;

import entities.Appartement;
import entities.Facture;
import entities.User;
import services.ServiceAppartemment;
import services.ServiceFacture;
import services.ServiceUser;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class MainCrudFacture {
    public static void main(String[] args) throws SQLException {
        ServiceAppartemment sa = new ServiceAppartemment();
        ServiceUser su = new ServiceUser();
        // Example usage:
        ServiceFacture serviceFacture = new ServiceFacture();

       /* User resident = new User("John", "John",95374008,"ali@ali.com","ali123456", User.Role.RESIDENT);
        Appartement p = new Appartement(12,"John","100m2",2, Appartement.statutAppartement.Libre);
        sa.ajouter(p);
        Set<Appartement> appartements = sa.getAppartementsByResidentWithForeignKey(resident);
        System.out.println(appartements); */

// Replace with your desired logic to handle the retrieved appartements



    }
}

