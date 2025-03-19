package com.ipi.gestionchampionnatapi.repository;
import com.ipi.gestionchampionnatapi.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour l'entité Equipe.
 */
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    // Recherche des équipes appartenant à un championnat (relation ManyToMany)
    List<Equipe> findByChampionnatsId(Long championnatId);
}
