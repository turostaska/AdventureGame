package org.github.turostaska.dao.impl.list;

import org.github.turostaska.dao.IToolDao;
import org.github.turostaska.domain.NonUsableTool;
import org.github.turostaska.domain.Tool;
import org.github.turostaska.domain.UsableTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectionToolDao implements IToolDao {
    private List<Tool> tools = new ArrayList<>();

    @Override
    public void delete(Tool tool) {
        tools.remove(tool);
    }

    @Override
    public void create(Tool tool) {
        if (tool == null)
            throw new IllegalArgumentException("Player can't be null.");

        if (!tools.contains(tool))
            tools.add(tool);
    }

    @Override
    public List<Tool> getAll() {
        return tools;
    }

    @Override
    public <T extends Tool> List<T> getByType(Type type) {
        ArrayList<T> toolsOfType = new ArrayList<>();

        switch (type) {
            case USABLE:
                for (Tool tool : tools)
                    if (tool instanceof UsableTool)
                        toolsOfType.add((T) tool);
                 break;
            case NONUSABLE:
                for (Tool tool : tools)
                    if (tool instanceof NonUsableTool)
                        toolsOfType.add((T) tool);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return toolsOfType;
    }

    @Override
    public Optional<Tool> getById(int ID) {
        for (Tool t : tools) {
            if (t.getID() == ID)
                return Optional.of(t);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Tool> getByName(String name) {
        for (Tool t : tools) {
            if (t.getName().equals(name))
                return Optional.of(t);
        }
        return Optional.empty();
    }

    @Override
    public void update(Tool tool) {
        for (int i = 0; i < tools.size(); ++i) {
            if (tools.get(i).getID() == tool.getID()) {
                tools.set(i, tool);
                return;
            }
        }
    }
}
