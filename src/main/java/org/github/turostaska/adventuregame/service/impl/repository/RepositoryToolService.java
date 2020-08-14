package org.github.turostaska.adventuregame.service.impl.repository;

import org.github.turostaska.adventuregame.domain.NonUsableTool;
import org.github.turostaska.adventuregame.domain.Tool;
import org.github.turostaska.adventuregame.domain.UsableTool;
import org.github.turostaska.adventuregame.repository.INonUsableToolRepository;
import org.github.turostaska.adventuregame.repository.IUsableToolRepository;
import org.github.turostaska.adventuregame.service.IToolService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RepositoryToolService implements IToolService {
    @Autowired private IUsableToolRepository usableToolRepository;
    @Autowired private INonUsableToolRepository nonUsableToolRepository;

    @Override
    public UsableTool addOrUpdate(UsableTool tool) {
        return usableToolRepository.save(tool);
    }

    @Override
    public NonUsableTool addOrUpdate(NonUsableTool tool) {
        return nonUsableToolRepository.save(tool);
    }

    @Override
    public void delete(UsableTool tool) {
        usableToolRepository.delete(tool);
    }

    @Override
    public void delete(NonUsableTool tool) {
        nonUsableToolRepository.delete(tool);
    }

    @Override
    public Optional<UsableTool> getUsableToolById(Long ID) {
        return usableToolRepository.findById(ID);
    }

    @Override
    public Optional<NonUsableTool> getNonUsableToolById(Long ID) {
        return nonUsableToolRepository.findById(ID);
    }

    @Override
    public Optional<UsableTool> getUsableToolByName(String name) {
        return usableToolRepository.findByName(name);
    }

    @Override
    public Optional<NonUsableTool> getNonUsableToolByName(String name) {
        return nonUsableToolRepository.findByName(name);
    }

    @Override
    public List<UsableTool> getAllUsableTools() {
        return usableToolRepository.findAll();
    }

    @Override
    public List<NonUsableTool> getAllNonUsableTools() {
        return nonUsableToolRepository.findAll();
    }

    @Override
    public List<Tool> getAllTools() {
        return Stream.concat(getAllUsableTools().stream(), getAllNonUsableTools().stream())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUsableToolById(Long id) {
        usableToolRepository.deleteById(id);
    }

    @Override
    public void deleteNonUsableToolById(Long id) {
        nonUsableToolRepository.deleteById(id);
    }
}
