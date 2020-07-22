package org.github.turostaska.service.impl.collection;

import org.github.turostaska.dao.IToolDao;
import org.github.turostaska.domain.NonUsableTool;
import org.github.turostaska.domain.UsableTool;
import org.github.turostaska.service.IToolService;

import java.util.Optional;

public class CollectionToolService implements IToolService {
    private IToolDao dao;

    public CollectionToolService(IToolDao dao) {
        this.dao = dao;
    }

    @Override
    public void addOrUpdate(UsableTool tool) {
        if (dao.getById(tool.getID()).isEmpty())
            dao.create(tool);
        else
            dao.update(tool);
    }

    @Override
    public void addOrUpdate(NonUsableTool tool) {
        if (dao.getById(tool.getID()).isEmpty())
            dao.create(tool);
        else
            dao.update(tool);
    }

    @Override
    public void delete(UsableTool tool) {
        dao.delete(tool);
    }

    @Override
    public void delete(NonUsableTool tool) {
        dao.delete(tool);
    }

    @Override
    public Optional<UsableTool> getUsableToolById(Long ID) {
        if( dao.getById(ID).isPresent() && dao.getById(ID).get() instanceof UsableTool )
            return Optional.of((UsableTool) dao.getById(ID).get());

        return Optional.empty();
    }

    @Override
    public Optional<NonUsableTool> getNonUsableToolById(Long ID) {
        if( dao.getById(ID).isPresent() && dao.getById(ID).get() instanceof NonUsableTool )
            return Optional.of((NonUsableTool) dao.getById(ID).get());

        return Optional.empty();
    }

    @Override
    public Optional<UsableTool> getUsableToolByName(String name) {
        if( dao.getByName(name).isPresent() && dao.getByName(name).get() instanceof UsableTool )
            return Optional.of((UsableTool) dao.getByName(name).get());

        return Optional.empty();
    }

    @Override
    public Optional<NonUsableTool> getNonUsableToolByName(String name) {
        if( dao.getByName(name).isPresent() && dao.getByName(name).get() instanceof NonUsableTool )
            return Optional.of((NonUsableTool) dao.getByName(name).get());

        return Optional.empty();
    }

}
