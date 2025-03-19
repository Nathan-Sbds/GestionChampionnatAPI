package com.ipi.gestionchampionnatapi.controller;
import com.ipi.gestionchampionnatapi.model.Championnat;
import com.ipi.gestionchampionnatapi.model.Journee;
import com.ipi.gestionchampionnatapi.repository.ChampionnatRepository;
import com.ipi.gestionchampionnatapi.repository.JourneeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des journées.
 */
@RestController
@RequestMapping("/api")
@Validated
public class JourneeController {

    @Autowired
    private JourneeRepository journeeRepository;

    @Autowired
    private ChampionnatRepository championnatRepository;

    // Récupérer la liste de toutes les journées
    @GetMapping("/journees")
    public List<Journee> getAllJournees() {
        return journeeRepository.findAll();
    }

    // Récupérer les journées d'un championnat donné
    @GetMapping("/championnats/{championnatId}/journees")
    public ResponseEntity<List<Journee>> getJourneesByChampionnatId(@PathVariable Long championnatId) {
        List<Journee> journees = journeeRepository.findByChampionnatId(championnatId);
        return ResponseEntity.ok(journees);
    }

    // Récupérer une journée par son ID
    @GetMapping("/journees/{id}")
    public ResponseEntity<Journee> getJourneeById(@PathVariable Long id) {
        Optional<Journee> journee = journeeRepository.findById(id);
        return journee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer une journée pour un championnat
    @PostMapping("/championnats/{championnatId}/journees")
    public ResponseEntity<Journee> createJournee(@PathVariable Long championnatId,
                                                 @Valid @RequestBody Journee journeeRequest) {
        Optional<Championnat> championnatOptional = championnatRepository.findById(championnatId);
        if (!championnatOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Championnat championnat = championnatOptional.get();
        journeeRequest.setChampionnat(championnat);
        Journee journee = journeeRepository.save(journeeRequest);
        return ResponseEntity.ok(journee);
    }

    // Mettre à jour une journée existante
    @PutMapping("/journees/{id}")
    public ResponseEntity<Journee> updateJournee(@PathVariable Long id, @Valid @RequestBody Journee updatedJournee) {
        return journeeRepository.findById(id).map(journee -> {
            journee.setNumero(updatedJournee.getNumero());
            journee.setDate(updatedJournee.getDate());
            Journee savedJournee = journeeRepository.save(journee);
            return ResponseEntity.ok(savedJournee);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une journée
    @DeleteMapping("/journees/{id}")
    public ResponseEntity<Object> deleteJournee(@PathVariable Long id) {
        return journeeRepository.findById(id).map(journee -> {
            journeeRepository.delete(journee);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
