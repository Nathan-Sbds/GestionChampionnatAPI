package com.ipi.gestionchampionnatapi.repository;
import com.ipi.gestionchampionnatapi.model.Journee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour l'entité Journee.
 */
public interface JourneeRepository extends JpaRepository<Journee, Long> {
    List<Journee> findByChampionnatId(Long championnatId);
}
