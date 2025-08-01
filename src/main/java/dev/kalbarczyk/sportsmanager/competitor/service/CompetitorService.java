package dev.kalbarczyk.sportsmanager.competitor.service;

import dev.kalbarczyk.sportsmanager.common.exception.CrudException;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.repository.CompetitorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CompetitorService {
    private final CompetitorRepository competitorRepository;

    public CompetitorService(final CompetitorRepository competitorRepository) {
        this.competitorRepository = competitorRepository;
    }

    public List<Competitor> findAll() {
        log.info("Fetching all competitors");
        return competitorRepository.findAll();
    }

    public Competitor findById(final Long id) {
        log.info("Fetching competitor by ID: {}", id);
        return competitorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Competitor not found with ID: {}", id);
                    return new CrudException.NotFound("Competitor not found with id: " + id);
                });
    }

    public Competitor save(final Competitor competitor) {
        log.info("Saving new competitor: {} {}", competitor.getName(), competitor.getSurname());
        return competitorRepository.save(competitor);
    }

    public Competitor update(final Long id, final Competitor competitor) {
        log.info("Updating competitor with ID: {}", id);
        if (!competitorRepository.existsById(id)) {
            log.warn("Cannot update. Competitor not found with ID: {}", id);
            throw new CrudException.NotFound("Competitor not found with id: " + id);
        }
        competitor.setId(id);
        return competitorRepository.save(competitor);
    }

    public void delete(final Long id) {
        log.info("Deleting competitor with ID: {}", id);
        if (!competitorRepository.existsById(id)) {
            log.warn("Cannot delete. Competitor not found with ID: {}", id);
            throw new CrudException.NotFound("Competitor not found with id: " + id);
        }
        competitorRepository.deleteById(id);
    }
}
