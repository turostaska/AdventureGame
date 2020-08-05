package org.github.turostaska.service.impl.collection;

import org.github.turostaska.dao.ITechniqueDao;
import org.github.turostaska.domain.Technique;
import org.github.turostaska.service.ITechniqueService;

import java.util.List;
import java.util.Optional;

public class CollectionTechniqueService implements ITechniqueService {
    private ITechniqueDao dao;

    public CollectionTechniqueService(ITechniqueDao dao) {
        this.dao = dao;
    }

    @Override
    public void addOrUpdate(Technique technique) {
        if (dao.getById(technique.getId()).isEmpty())
            dao.create(technique);
        else
            dao.update(technique);
    }

    @Override
    public void delete(Technique technique) {
        dao.delete(technique);
    }

    @Override
    public Optional<Technique> getById(Long ID) {
        return dao.getById(ID);
    }

    @Override
    public Optional<Technique> getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public List<Technique> getAll() {
        return dao.getAll();
    }

    @Override
    public void deleteById(Long id) {
        dao.getById(id).ifPresent(dao::delete);
    }
}
