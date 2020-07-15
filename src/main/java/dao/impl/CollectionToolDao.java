package dao.impl;

import dao.IToolDao;
import domain.Player;
import domain.Tool;

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
        if (!tools.contains(tool))
            tools.add(tool);
    }

    @Override
    public List<Tool> getAll() {
        return tools;
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
