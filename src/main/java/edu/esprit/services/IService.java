package edu.esprit.services;
import edu.esprit.entities.Reclamation;
import java.util.Set;
import java.sql.SQLException;
import java.util.List;
public interface IService <T> {
    public void ajouter(T r) throws SQLException;
    public void modifier(T r) throws SQLException;
    public void supprimer(int id_reclamation) throws SQLException;
    public T getOneById(int id_reclamation) throws SQLException;

    public Set<T> getAll() throws SQLException;

}
