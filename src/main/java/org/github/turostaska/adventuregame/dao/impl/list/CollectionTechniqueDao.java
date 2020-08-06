package org.github.turostaska.adventuregame.dao.impl.list;

import org.github.turostaska.adventuregame.dao.ITechniqueDao;
import org.github.turostaska.adventuregame.domain.Technique;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionTechniqueDao implements ITechniqueDao {
    private List<Technique> techniques = new ArrayList<>();

    @Override
    public List<Technique> getAll() { return techniques; }

    @Override
    public Optional<Technique> getById(Long ID) {
        for (Technique t : techniques) {
            if (t.getId().equals(ID))
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
            if (techniques.get(i).getId().equals(technique.getId())) {
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
