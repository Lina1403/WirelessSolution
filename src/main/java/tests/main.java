package tests;

import entities.Espace;
import entities.Event;
import services.ServiceEspace;
import services.ServiceEvent;

import java.util.Date;
import java.util.Set;

public class main {

    public static void main(String[] args) {
       ServiceEvent serviceEvent = new ServiceEvent();
/// test event

        // Ajouter un nouvel événement
        Event event1 = new Event(1,"kais ", "email@example.com", "ademm", new Date(), 10, "nouveau", "Description de l'événement");
        serviceEvent.ajouter(event1);

     // Récupérer l'événement en fonction de son nom
     Event retrievedEvent = serviceEvent.getOneByName("name");

     // Afficher tous les événements
        Set<Event> events = serviceEvent.getAll();
        for (Event e : events) {
            System.out.println(e);
        }


     // Vérifier si l'événement a été trouvé
     if (retrievedEvent != null) {
      // Modifier l'événement
      retrievedEvent.setName("testestest");
      retrievedEvent.setNbrPersonne(40);
      serviceEvent.modifier(retrievedEvent);
     } else {
      System.out.println("L'événement n'a pas été trouvé.");
     }

        // Supprimer un événement
     String nomEvenement = "sarra";
     serviceEvent.supprimer(nomEvenement);








  /// test espace
        ServiceEspace SE = new ServiceEspace();


        Espace e1 = new Espace("salon","salon@gmail","occupé",5,"aaa") ;
        Espace e2 = new Espace("aa","salon@gmail","occupé",5,"aaa") ;
        Espace e3 = new Espace("bbb","salon@gmail","occupé",5,"aaa") ;
        Espace e4 = new Espace("ccc","salon@gmail","occupé",5,"aaa") ;

        /// ajout
       SE.ajouter(e1);
        SE.ajouter(e2);
        SE.ajouter(e3);
        SE.ajouter(e4);


        // Récupérer un événement en fonction de son ID
        Espace retrievedEspace= SE.getOneByName("name");
        System.out.println("L'espace avec nom : " + retrievedEspace);

        // Afficher tous les événements
        Set<Espace> espaces = SE.getAll();
        for (Espace e : espaces) {
            System.out.println(e.toString());
        }


        //modification
        retrievedEspace.setName("LINA");
        retrievedEspace.setDescription("Nouveau titre");
        retrievedEspace.setEtat("libre");
        SE.modifier(retrievedEspace);

// supprimer
      String nomEspace = "LINA";
SE.supprimer(nomEspace);



    }
}
