package edu.esprit.tests;
import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.services.ServiceReponse;
import java.util.Date;
import java.util.Set;
import java.util.Scanner;
import edu.esprit.utils.DataSource;

public class Main {
    public static void main(String[] args) {

    }
}
/*public class Main {
    public static void main(String[] args) {
        ServiceReclamation serviceReclamation = new ServiceReclamation();
        ServiceReponse serviceReponse = new ServiceReponse();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            afficherMenu();

            int choix = scanner.nextInt();
            scanner.nextLine(); // Pour consommer le saut de ligne

            switch (choix) {
                case 1:
                    ajouterReclamation(serviceReclamation, scanner);
                    break;
                case 2:
                    modifierReclamation(serviceReclamation, scanner);
                    break;
                case 3:
                    supprimerReclamation(serviceReclamation, scanner);
                    break;
                case 4:
                    afficherReclamations(serviceReclamation);
                    break;
                case 5:
                    ajouterReponse(serviceReponse, scanner);
                    break;
                case 6:
                    modifierReponse(serviceReponse, scanner);
                    break;
                case 7:
                    supprimerReponse(serviceReponse, scanner);
                    break;
                case 8:
                    afficherReponses(serviceReponse);
                    break;
                case 9:
                    System.out.println("Au revoir !");
                    System.exit(0);
                default:
                    System.out.println("Option invalide. Veuillez choisir une option valide.");
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Ajouter une réclamation");
        System.out.println("2. Modifier une réclamation par nom");
        System.out.println("3. Supprimer une réclamation par nom");
        System.out.println("4. Afficher toutes les réclamations");
        System.out.println("5. Ajouter une réponse à une réclamation");
        System.out.println("6. Modifier une réponse à une réclamation");
        System.out.println("7. Supprimer une réponse à une réclamation");
        System.out.println("8. Afficher toutes les réponses");
        System.out.println("9. Quitter");
        System.out.print("Choisissez une option : ");
    }

    private static void ajouterReclamation(ServiceReclamation serviceReclamation, Scanner scanner) {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Numéro de téléphone : ");
        String num_tel = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Objet : ");
        String objet = scanner.nextLine();
        System.out.print("Description : ");
        String description = scanner.nextLine();

        Reclamation nouvelleReclamation = new Reclamation(nom, prenom, num_tel, email, new Date(), objet, description);
        serviceReclamation.ajouter(nouvelleReclamation);
    }

    private static void modifierReclamation(ServiceReclamation serviceReclamation, Scanner scanner) {
        System.out.print("Entrez le nom de la personne pour laquelle vous souhaitez modifier la réclamation : ");
        String nom = scanner.nextLine();

        Reclamation reclamationAModifier = serviceReclamation.getReclamationByNom(nom);
        if (reclamationAModifier != null) {
            System.out.println("Réclamation trouvée : " + reclamationAModifier);

            // Demander à l'utilisateur les nouvelles informations pour la réclamation
            System.out.print("Nouveau nom : ");
            String nouveauNom = scanner.nextLine();
            System.out.print("Nouveau prénom : ");
            String nouveauPrenom = scanner.nextLine();
            System.out.print("Nouveau numéro de téléphone : ");
            String nouveauNumTel = scanner.nextLine();
            System.out.print("Nouvel email : ");
            String nouvelEmail = scanner.nextLine();
            System.out.print("Nouvel objet : ");
            String nouvelObjet = scanner.nextLine();
            System.out.print("Nouvelle description : ");
            String nouvelleDescription = scanner.nextLine();

            // Mettre à jour la réclamation avec les nouvelles informations
            Reclamation nouvelleReclamation = new Reclamation(nouveauNom, nouveauPrenom, nouveauNumTel, nouvelEmail, new Date(), nouvelObjet, nouvelleDescription);
            serviceReclamation.modifier(nouvelleReclamation, nom);
        } else {
            System.out.println("Aucune réclamation trouvée pour le nom " + nom);
        }
    }

    private static void supprimerReclamation(ServiceReclamation serviceReclamation, Scanner scanner) {
        System.out.print("Entrez le nom de la personne pour supprimer ses réclamations : ");
        String nom = scanner.nextLine();
        serviceReclamation.supprimer(nom);
    }

    private static void afficherReclamations(ServiceReclamation serviceReclamation) {
        Set<Reclamation> reclamations = serviceReclamation.getAll();
        for (Reclamation reclamation : reclamations) {
            System.out.println(reclamation);
        }
    }

    private static void ajouterReponse(ServiceReponse serviceReponse, Scanner scanner) {
        System.out.print("Entrez le nom de la personne pour laquelle vous souhaitez ajouter une réponse : ");
        String nom = scanner.nextLine();

        Reclamation reclamation = serviceReponse.getReclamationByNom(nom);
        if (reclamation != null) {
            System.out.print("Contenu de la réponse : ");
            String contenu = scanner.nextLine();

            Reponse nouvelleReponse = new Reponse(reclamation.getId_reclamation(), contenu, new Date());
            serviceReponse.ajouter(nouvelleReponse);
        } else {
            System.out.println("Aucune réclamation trouvée pour le nom " + nom);
        }
    }

    private static void modifierReponse(ServiceReponse serviceReponse, Scanner scanner) {
        System.out.print("Entrez le nom de la personne pour laquelle vous souhaitez modifier la réponse : ");
        String nom = scanner.nextLine();

        Reponse reponseAModifier = serviceReponse.getOneById(serviceReponse.getReclamationByNom(nom).getId_reclamation());
        if (reponseAModifier != null) {
            System.out.println("Réponse trouvée : " + reponseAModifier);

            // Demander à l'utilisateur le nouveau contenu pour la réponse
            System.out.print("Nouveau contenu de la réponse : ");
            String nouveauContenu = scanner.nextLine();

            // Mettre à jour la réponse avec le nouveau contenu
            reponseAModifier.setContenu(nouveauContenu);
            serviceReponse.modifier(reponseAModifier, nom);
        } else {
            System.out.println("Aucune réponse trouvée pour la réclamation " + nom);
        }
    }

    private static void supprimerReponse(ServiceReponse serviceReponse, Scanner scanner) {
        System.out.print("Entrez le nom de la personne pour laquelle vous souhaitez supprimer la réponse : ");
        String nom = scanner.nextLine();
        serviceReponse.supprimer(nom);
    }

    private static void afficherReponses(ServiceReponse serviceReponse) {
        Set<Reponse> reponses = serviceReponse.getAll();
        for (Reponse reponse : reponses) {
            System.out.println(reponse);
        }
    }
}*/




