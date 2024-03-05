package service;
import java.sql.SQLException;
import java.util.Set;
import entities.User;
import java.util.List;

public interface IService <T> {
    public void ajouter(T r) throws SQLException;
    public void modifier(T r) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public T getOneById(int id) throws SQLException;
    public List<T> getAll() throws SQLException;
}
