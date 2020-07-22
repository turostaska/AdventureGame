package org.github.turostaska.service.impl.repository;

import org.github.turostaska.domain.NonUsableTool;
import org.github.turostaska.domain.UsableTool;
import org.github.turostaska.repository.INonUsableToolRepository;
import org.github.turostaska.repository.IUsableToolRepository;
import org.github.turostaska.service.IToolService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RepositoryToolService implements IToolService {
    @Autowired private IUsableToolRepository usableToolRepository;
    @Autowired private INonUsableToolRepository nonUsableToolRepository;

    @Override
    public void addOrUpdate(UsableTool tool) {
        usableToolRepository.save(tool);
    }

    @Override
    public void addOrUpdate(NonUsableTool tool) {
        nonUsableToolRepository.save(tool);
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
}
