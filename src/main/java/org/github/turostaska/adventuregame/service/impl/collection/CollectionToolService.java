package org.github.turostaska.adventuregame.service.impl.collection;

import jdk.jshell.spi.ExecutionControl;
import org.github.turostaska.adventuregame.dao.IToolDao;
import org.github.turostaska.adventuregame.domain.NonUsableTool;
import org.github.turostaska.adventuregame.domain.Tool;
import org.github.turostaska.adventuregame.domain.UsableTool;
import org.github.turostaska.adventuregame.service.IToolService;

import java.util.List;
import java.util.Optional;

public class CollectionToolService implements IToolService {
    private IToolDao dao;

    public CollectionToolService(IToolDao dao) {
        this.dao = dao;
    }

    @Override
    public UsableTool addOrUpdate(UsableTool tool) {
        if (dao.getById(tool.getId()).isEmpty())
            dao.create(tool);
        else
            dao.update(tool);

        return getUsableToolById(tool.getId()).orElseThrow();
    }

    @Override
    public NonUsableTool addOrUpdate(NonUsableTool tool) {
        if (dao.getById(tool.getId()).isEmpty())
            dao.create(tool);
        else
            dao.update(tool);

        return getNonUsableToolById(tool.getId()).orElseThrow();
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

    @Override
    public List<UsableTool> getAllUsableTools() {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<NonUsableTool> getAllNonUsableTools() {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Tool> getAllTools() {
        return dao.getAll();
    }

    @Override
    public void deleteUsableToolById(Long id) {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNonUsableToolById(Long id) {
        try {
            throw new ExecutionControl.NotImplementedException("lusta voltam");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }

}
