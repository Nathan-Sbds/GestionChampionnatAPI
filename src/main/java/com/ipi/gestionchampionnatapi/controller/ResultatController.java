package com.ipi.gestionchampionnatapi.controller;
import com.ipi.gestionchampionnatapi.model.Journee;
import com.ipi.gestionchampionnatapi.model.Resultat;
import com.ipi.gestionchampionnatapi.repository.JourneeRepository;
import com.ipi.gestionchampionnatapi.repository.ResultatRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des résultats.
 */
@RestController
@RequestMapping("/api")
@Validated
public class ResultatController {

    @Autowired
    private ResultatRepository resultatRepository;

    @Autowired
    private JourneeRepository journeeRepository;

    // Récupérer la liste de tous les résultats
    @GetMapping("/resultats")
    public List<Resultat> getAllResultats() {
        return resultatRepository.findAll();
    }

    // Récupérer les résultats d'un championnat (via la journée)
    @GetMapping("/championnats/{championnatId}/resultats")
    public ResponseEntity<List<Resultat>> getResultatsByChampionnatId(@PathVariable Long championnatId) {
        List<Resultat> resultats = resultatRepository.findByJourneeChampionnatId(championnatId);
        return ResponseEntity.ok(resultats);
    }

    // Récupérer les résultats d'une journée donnée
    @GetMapping("/journees/{journeeId}/resultats")
    public ResponseEntity<List<Resultat>> getResultatsByJourneeId(@PathVariable Long journeeId) {
        List<Resultat> resultats = resultatRepository.findByJourneeId(journeeId);
        return ResponseEntity.ok(resultats);
    }

    // Récupérer un résultat par son ID
    @GetMapping("/resultats/{id}")
    public ResponseEntity<Resultat> getResultatById(@PathVariable Long id) {
        Optional<Resultat> resultat = resultatRepository.findById(id);
        return resultat.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un résultat pour une journée donnée
    @PostMapping("/journees/{journeeId}/resultats")
    public ResponseEntity<Resultat> createResultat(@PathVariable Long journeeId,
                                                   @Valid @RequestBody Resultat resultatRequest) {
        Optional<Journee> journeeOptional = journeeRepository.findById(journeeId);
        if (!journeeOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Journee journee = journeeOptional.get();
        resultatRequest.setJournee(journee);
        Resultat resultat = resultatRepository.save(resultatRequest);
        return ResponseEntity.ok(resultat);
    }

    // Mettre à jour un résultat existant
    @PutMapping("/resultats/{id}")
    public ResponseEntity<Resultat> updateResultat(@PathVariable Long id, @Valid @RequestBody Resultat updatedResultat) {
        return resultatRepository.findById(id).map(resultat -> {
            resultat.setScoreDomicile(updatedResultat.getScoreDomicile());
            resultat.setScoreVisiteur(updatedResultat.getScoreVisiteur());
            // Mise à jour éventuelle des équipes
            resultat.setEquipeDomicile(updatedResultat.getEquipeDomicile());
            resultat.setEquipeVisiteur(updatedResultat.getEquipeVisiteur());
            Resultat savedResultat = resultatRepository.save(resultat);
            return ResponseEntity.ok(savedResultat);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un résultat
    @DeleteMapping("/resultats/{id}")
    public ResponseEntity<Object> deleteResultat(@PathVariable Long id) {
        return resultatRepository.findById(id).map(resultat -> {
            resultatRepository.delete(resultat);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
