package org.github.turostaska.service.impl.collection;

import org.github.turostaska.dao.IToolDao;
import org.github.turostaska.domain.Tool;
import org.github.turostaska.service.IToolService;

import java.util.Optional;

public class CollectionToolService implements IToolService {
    private IToolDao dao;

    public CollectionToolService(IToolDao dao) {
        this.dao = dao;
    }

    @Override
    public void addOrUpdate(Tool tool) {
        if (dao.getById(tool.getID()).isEmpty())
            dao.create(tool);
        else
            dao.update(tool);
    }

    @Override
    public void delete(Tool tool) {
        dao.delete(tool);
    }

    @Override
    public Optional<Tool> getById(int ID) {
        return dao.getById(ID);
    }

    @Override
    public Optional<Tool> getByName(String name) {
        return dao.getByName(name);
    }


}
