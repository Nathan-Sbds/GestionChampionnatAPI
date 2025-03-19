package com.ipi.gestionchampionnatapi.controller;
import com.ipi.gestionchampionnatapi.model.Championnat;
import com.ipi.gestionchampionnatapi.repository.ChampionnatRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des championnats.
 */
@RestController
@RequestMapping("/api/championnats")
@Validated
public class ChampionnatController {

    @Autowired
    private ChampionnatRepository championnatRepository;

    // Récupérer la liste des championnats
    @GetMapping
    public List<Championnat> getAllChampionnats() {
        return championnatRepository.findAll();
    }

    // Récupérer un championnat par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Championnat> getChampionnatById(@PathVariable Long id) {
        Optional<Championnat> championnat = championnatRepository.findById(id);
        return championnat.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un nouveau championnat
    @PostMapping
    public Championnat createChampionnat(@Valid @RequestBody Championnat championnat) {
        return championnatRepository.save(championnat);
    }

    // Mettre à jour un championnat existant
    @PutMapping("/{id}")
    public ResponseEntity<Championnat> updateChampionnat(@PathVariable Long id,
                                                         @Valid @RequestBody Championnat updatedChampionnat) {
        return championnatRepository.findById(id).map(championnat -> {
            championnat.setNom(updatedChampionnat.getNom());
            Championnat savedChampionnat = championnatRepository.save(championnat);
            return ResponseEntity.ok(savedChampionnat);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un championnat
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteChampionnat(@PathVariable Long id) {
        return championnatRepository.findById(id).map(championnat -> {
            championnatRepository.delete(championnat);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
