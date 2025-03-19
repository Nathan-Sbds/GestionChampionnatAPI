package com.ipi.gestionchampionnatapi.repository;

import com.ipi.gestionchampionnatapi.model.Championnat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour l'entité Championnat.
 */
public interface ChampionnatRepository extends JpaRepository<Championnat, Long> {
}
