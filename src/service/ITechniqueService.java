package service;

import domain.Technique;
import java.util.Optional;

public interface ITechniqueService {
    void addOrUpdate(Technique technique);
    void delete(Technique technique);
    Optional<Technique> getById(int ID);
    Optional<Technique> getByName(String name);
}
