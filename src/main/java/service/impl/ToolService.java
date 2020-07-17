package service.impl;

import dao.IToolDao;
import domain.Tool;
import service.IToolService;

import java.util.Optional;

public class ToolService implements IToolService {
    private IToolDao dao;

    public ToolService(IToolDao dao) {
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
