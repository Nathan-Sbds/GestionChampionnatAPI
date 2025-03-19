package com.ipi.gestionchampionnatapi.controller;
import com.ipi.gestionchampionnatapi.model.Utilisateur;
import com.ipi.gestionchampionnatapi.repository.UtilisateurRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des utilisateurs.
 */
@RestController
@RequestMapping("/api/utilisateurs")
@Validated
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Récupérer la liste des utilisateurs
    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    // Récupérer un utilisateur par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        return utilisateur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer un utilisateur par son adresse email
    @GetMapping("/email/{email}")
    public ResponseEntity<Utilisateur> getUtilisateurByEmail(@PathVariable String email) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(email);
        return utilisateur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un nouvel utilisateur (mot de passe crypté)
    @PostMapping
    public Utilisateur createUtilisateur(@Valid @RequestBody Utilisateur utilisateur) {
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }

    // Mettre à jour un utilisateur existant
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id,
                                                         @Valid @RequestBody Utilisateur updatedUtilisateur) {
        return utilisateurRepository.findById(id).map(utilisateur -> {
            utilisateur.setNom(updatedUtilisateur.getNom());
            utilisateur.setPrenom(updatedUtilisateur.getPrenom());
            utilisateur.setEmail(updatedUtilisateur.getEmail());
            // Cryptage du mot de passe avant mise à jour
            utilisateur.setPassword(passwordEncoder.encode(updatedUtilisateur.getPassword()));
            Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
            return ResponseEntity.ok(savedUtilisateur);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUtilisateur(@PathVariable Long id) {
        return utilisateurRepository.findById(id).map(utilisateur -> {
            utilisateurRepository.delete(utilisateur);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
