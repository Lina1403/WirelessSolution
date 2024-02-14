package services;

import entities.parking;
import entities.voiture;

import java.util.Set;

public interface IService<V> {
    void ajouter(V v);
    void modifier(V v);

        Set<V> getAll();

}
