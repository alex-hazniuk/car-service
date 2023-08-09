package org.example.repository;

import lombok.AllArgsConstructor;
import org.example.model.Repairer;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RepairerRepositoryImpl implements RepairerRepository {

    private final List<Repairer> repairers;

    @Override
    public void add(Repairer repairer) {
        repairers.add(repairer);
    }

    @Override
    public Optional<Repairer> findById(int id) {
        return repairers.stream()
                .filter(repairer -> repairer.getId() == id)
                .findAny();
    }

    @Override
    public List<Repairer> getAll() {
        return repairers;
    }

    @Override
    public Optional<Repairer> findByName(String name) {
        return repairers.stream()
                .filter(repairer -> repairer.getName().equals(name))
                .findFirst();
    }


}
