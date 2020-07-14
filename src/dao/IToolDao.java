package dao;

import domain.Tool;

import java.util.List;
import java.util.Optional;

public interface IToolDao {
    List<Tool> getAll();
    Optional<Tool> getById(int ID);
    Optional<Tool> getByName(String name);
    void update(Tool tool);
    void delete(Tool tool);
    void create(Tool tool);
}
