package org.github.turostaska.adventuregame.service.impl.repository;

import org.github.turostaska.adventuregame.domain.Technique;
import org.github.turostaska.adventuregame.repository.ITechniqueRepository;
import org.github.turostaska.adventuregame.service.ITechniqueService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RepositoryTechniqueService implements ITechniqueService {
    @Autowired private ITechniqueRepository techniqueRepository;

    @Override
    public Technique addOrUpdate(Technique technique) {
        return techniqueRepository.save(technique);
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

    @Override
    public List<Technique> getAll() {
        return techniqueRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        techniqueRepository.deleteById(id);
    }
}
