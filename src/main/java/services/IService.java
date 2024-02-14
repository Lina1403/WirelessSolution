package services;

import java.util.Set;

public interface IService<V> {
    void ajouter(V v);
    void modifier(V v);

        void supprimer(int id);
       // void afficher(V v);

        Set<V> getAll();

}
