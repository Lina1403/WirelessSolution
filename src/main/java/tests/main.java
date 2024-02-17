package tests;

import entities.Espace;
import entities.Event;
import services.ServiceEspace;
import services.ServiceEvent;

import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

public class main {

    public static void main(String[] args) throws SQLException {
     ServiceEvent Sevent = new ServiceEvent();
     Event e1 = new Event("Birthday","lina@lina","AAAA",new Date(),15,"BBBB","aaaaaaa",1);
        Event e2 = new Event("hhhhhh","lina@lina","AAAA",new Date(),15,"BBBB","aaaaaaa",1);
        Event e3 = new Event("ppppp","lina@lina","AAAA",new Date(),15,"BBBB","aaaaaaa",3);
        Event e4 = new Event("fffff","lina@lina","AAAA",new Date(),15,"BBBB","aaaaaaa",5);
        Event e5 = new Event("aaaaaa","lina@lina","AAAA",new Date(),15,"BBBB","aaaaaaa",8);
       /* Sevent.ajouter(e1);
        Sevent.ajouter(e2);
        Sevent.ajouter(e3);
        Sevent.ajouter(e4);
        Sevent.ajouter(e5);
        */
        e1.setIdEvent(1);
        e1.setName("Nouveau nom");
        e1.setEmail("nouvellemail@gmail.com");
        e1.setTitle("Nouveau titre");
        e1.setDate(new Date()); // Fixez la nouvelle date de l'événement
        e1.setNbrPersonne(20);
        e1.setStatutEvent("Actif");
        e1.setDescription("Nouvelle description");

        Sevent.modifier(e1);
    }


}
