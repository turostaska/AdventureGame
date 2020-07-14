package dao;

import domain.Technique;
import java.util.List;
import java.util.Optional;

public interface ITechniqueDao {
    List<Technique> getAll();
    Optional<Technique> getById(int ID);
    Optional<Technique> getByName(String name);
    void update(Technique technique);
    void delete(Technique technique);
    void create(Technique technique);
}
