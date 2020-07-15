package dao.impl;

import dao.ITechniqueDao;
import domain.Player;
import domain.Technique;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionTechniqueDao implements ITechniqueDao {
    private List<Technique> techniques = new ArrayList<>();

    @Override
    public List<Technique> getAll() { return techniques; }

    @Override
    public Optional<Technique> getById(int ID) {
        for (Technique t : techniques) {
            if (t.getID() == ID)
                return Optional.of(t);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Technique> getByName(String name) {
        for (Technique t : techniques) {
            if (t.getName().equals(name))
                return Optional.of(t);
        }
        return Optional.empty();
    }

    @Override
    public void update(Technique technique) {
        for (int i = 0; i < techniques.size(); ++i) {
            if (techniques.get(i).getID() == technique.getID()) {
                techniques.set(i, technique);
                return;
            }
        }
    }

    @Override
    public void delete(Technique technique) {
        techniques.remove(technique);
    }

    @Override
    public void create(Technique technique) {
        if (technique == null)
            throw new IllegalArgumentException("Player can't be null.");

        if (!techniques.contains(technique))
            techniques.add(technique);
    }
}
