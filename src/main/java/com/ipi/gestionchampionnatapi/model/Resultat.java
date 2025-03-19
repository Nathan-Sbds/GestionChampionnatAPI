package com.ipi.gestionchampionnatapi.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Entité représentant le résultat d'un match.
 */
@Entity
@Table(name = "resultats")
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relation Many-to-One avec Journee
    @ManyToOne
    @JoinColumn(name = "journee_id", nullable = false)
    private Journee journee;

    // Equipe domicile (Many-to-One)
    @ManyToOne
    @JoinColumn(name = "equipe_domicile_id", nullable = false)
    private Equipe equipeDomicile;

    // Equipe visiteur (Many-to-One)
    @ManyToOne
    @JoinColumn(name = "equipe_visiteur_id", nullable = false)
    private Equipe equipeVisiteur;

    @NotNull(message = "Le score de l'équipe domicile est obligatoire")
    private Integer scoreDomicile;

    @NotNull(message = "Le score de l'équipe visiteur est obligatoire")
    private Integer scoreVisiteur;

    // Constructeurs, getters et setters

    public Resultat() {
    }

    public Resultat(Journee journee, Equipe equipeDomicile, Equipe equipeVisiteur, Integer scoreDomicile, Integer scoreVisiteur) {
        this.journee = journee;
        this.equipeDomicile = equipeDomicile;
        this.equipeVisiteur = equipeVisiteur;
        this.scoreDomicile = scoreDomicile;
        this.scoreVisiteur = scoreVisiteur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Journee getJournee() {
        return journee;
    }

    public void setJournee(Journee journee) {
        this.journee = journee;
    }

    public Equipe getEquipeDomicile() {
        return equipeDomicile;
    }

    public void setEquipeDomicile(Equipe equipeDomicile) {
        this.equipeDomicile = equipeDomicile;
    }

    public Equipe getEquipeVisiteur() {
        return equipeVisiteur;
    }

    public void setEquipeVisiteur(Equipe equipeVisiteur) {
        this.equipeVisiteur = equipeVisiteur;
    }

    public Integer getScoreDomicile() {
        return scoreDomicile;
    }

    public void setScoreDomicile(Integer scoreDomicile) {
        this.scoreDomicile = scoreDomicile;
    }

    public Integer getScoreVisiteur() {
        return scoreVisiteur;
    }

    public void setScoreVisiteur(Integer scoreVisiteur) {
        this.scoreVisiteur = scoreVisiteur;
    }
}
