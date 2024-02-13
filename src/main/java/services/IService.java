package services;

import entities.voiture;

import java.util.Set;

public interface IService<V> {
    void ajouter(V newVoiture);
    void modifier(V updatedVoiture);
    Set<V> getAll();
}
