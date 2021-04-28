package pl.markowski.kinoteatr.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.markowski.kinoteatr.model.Spectacle;
import pl.markowski.kinoteatr.repo.SpectacleRepo;

import java.util.Optional;

public class SpectacleService {

    private SpectacleRepo spectacleRepo;

    @Autowired
    public SpectacleService(SpectacleRepo spectacleRepo) {
        this.spectacleRepo = spectacleRepo;
    }

    public Iterable<Spectacle> findAll() {
        return spectacleRepo.findAll();
    }

    public Optional<Spectacle> findById(Long id) {
        return spectacleRepo.findById(id);
    }

    public Spectacle save(Spectacle spectacle) {
        return spectacleRepo.save(spectacle);
    }

    public void deleteById(Long id) {
        spectacleRepo.deleteById(id);
    }

}
