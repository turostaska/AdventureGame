package org.github.turostaska.service.impl.repository;

import org.github.turostaska.domain.Technique;
import org.github.turostaska.repository.ITechniqueRepository;
import org.github.turostaska.service.ITechniqueService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RepositoryTechniqueService implements ITechniqueService {
    @Autowired private ITechniqueRepository techniqueRepository;

    @Override
    public void addOrUpdate(Technique technique) {
        techniqueRepository.save(technique);
    }

    @Override
    public void delete(Technique technique) {
        techniqueRepository.delete(technique);
    }

    @Override
    public Optional<Technique> getById(Long ID) {
        return techniqueRepository.findById(ID);
    }

    @Override
    public Optional<Technique> getByName(String name) {
        return techniqueRepository.findByName(name);
    }
}
