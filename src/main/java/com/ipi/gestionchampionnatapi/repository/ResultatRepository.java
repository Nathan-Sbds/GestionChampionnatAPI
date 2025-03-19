package com.ipi.gestionchampionnatapi.repository;
import com.ipi.gestionchampionnatapi.model.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour l'entité Resultat.
 */
public interface ResultatRepository extends JpaRepository<Resultat, Long> {
    // Recherche des résultats via la relation Journee -> Championnat
    List<Resultat> findByJourneeChampionnatId(Long championnatId);

    // Recherche des résultats pour une journée donnée
    List<Resultat> findByJourneeId(Long journeeId);
}
