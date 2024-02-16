package services;

import entities.Espace;
import entities.Event;

import java.util.Set;

public interface IService <E>{
     void ajouter(E e);
     void modifier(E e) ;
     void supprimer(String E) ;
     E getOneByName(String E) ;


    public Set<E> getAll();
}
