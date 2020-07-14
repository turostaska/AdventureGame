package service.impl;

import dao.ITechniqueDao;
import domain.Technique;
import service.ITechniqueService;

import java.util.Optional;

public class TechniqueService implements ITechniqueService {
    private ITechniqueDao dao;

    public TechniqueService(ITechniqueDao dao) {
        this.dao = dao;
    }

    @Override
    public void addOrUpdate(Technique technique) {
        if (dao.getById(technique.getID()).equals(Optional.empty()))
            dao.create(technique);
        else
            dao.update(technique);
    }

    @Override
    public void delete(Technique technique) {
        dao.delete(technique);
    }

    @Override
    public Optional<Technique> getById(int ID) {
        return dao.getById(ID);
    }

    @Override
    public Optional<Technique> getByName(String name) {
        return dao.getByName(name);
    }
}
