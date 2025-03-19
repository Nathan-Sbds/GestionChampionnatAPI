package com.ipi.gestionchampionnatapi.controller;
import com.ipi.gestionchampionnatapi.model.Championnat;
import com.ipi.gestionchampionnatapi.model.Equipe;
import com.ipi.gestionchampionnatapi.repository.ChampionnatRepository;
import com.ipi.gestionchampionnatapi.repository.EquipeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des équipes.
 */
@RestController
@RequestMapping("/api")
@Validated
public class EquipeController {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private ChampionnatRepository championnatRepository;

    // Récupérer la liste de toutes les équipes
    @GetMapping("/equipes")
    public List<Equipe> getAllEquipes() {
        return equipeRepository.findAll();
    }

    // Récupérer une équipe par son ID
    @GetMapping("/equipes/{id}")
    public ResponseEntity<Equipe> getEquipeById(@PathVariable Long id) {
        Optional<Equipe> equipe = equipeRepository.findById(id);
        return equipe.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer les équipes d'un championnat donné
    @GetMapping("/championnats/{championnatId}/equipes")
    public ResponseEntity<List<Equipe>> getEquipesByChampionnatId(@PathVariable Long championnatId) {
        List<Equipe> equipes = equipeRepository.findByChampionnatsId(championnatId);
        return ResponseEntity.ok(equipes);
    }

    // Créer une nouvelle équipe
    @PostMapping("/equipes")
    public Equipe createEquipe(@Valid @RequestBody Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    // Ajouter une équipe à un championnat
    @PostMapping("/championnats/{championnatId}/equipes")
    public ResponseEntity<Equipe> addEquipeToChampionnat(@PathVariable Long championnatId,
                                                         @Valid @RequestBody Equipe equipeRequest) {
        Optional<Championnat> championnatOptional = championnatRepository.findById(championnatId);
        if (!championnatOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Championnat championnat = championnatOptional.get();
        Equipe equipe = equipeRepository.save(equipeRequest);
        championnat.addEquipe(equipe);
        championnatRepository.save(championnat);
        return ResponseEntity.ok(equipe);
    }

    // Mettre à jour une équipe existante
    @PutMapping("/equipes/{id}")
    public ResponseEntity<Equipe> updateEquipe(@PathVariable Long id, @Valid @RequestBody Equipe updatedEquipe) {
        return equipeRepository.findById(id).map(equipe -> {
            equipe.setNom(updatedEquipe.getNom());
            Equipe savedEquipe = equipeRepository.save(equipe);
            return ResponseEntity.ok(savedEquipe);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une équipe
    @DeleteMapping("/equipes/{id}")
    public ResponseEntity<Object> deleteEquipe(@PathVariable Long id) {
        return equipeRepository.findById(id).map(equipe -> {
            equipeRepository.delete(equipe);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
