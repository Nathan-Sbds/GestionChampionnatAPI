package com.ipi.gestionchampionnatapi.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entité représentant un championnat.
 */
@Entity
@Table(name = "championnats")
public class Championnat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du championnat est obligatoire")
    @NotEmpty(message = "Le nom du championnat est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;

    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation = LocalDate.now();

    // Relation Many-to-Many avec Equipe
    @ManyToMany
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinTable(
            name = "championnat_equipe",
            joinColumns = @JoinColumn(name = "championnat_id"),
            inverseJoinColumns = @JoinColumn(name = "equipe_id")
    )
    private Set<Equipe> equipes = new HashSet<>();

    // Relation One-to-Many avec Journee
    @OneToMany(mappedBy = "championnat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Journee> journees = new ArrayList<>();

    @NotNull(message = "Le champ date de début du championnat est obligatoire")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Le champ date de fin du championnat est obligatoire")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Min(value = 0, message = "Le champ points de victoire du championnat ne peut pas être négatif")
    private int wonPoint;

    private int lostPoint;

    private int drawPoint;

    // Constructeurs, getters et setters

    public Championnat() {
    }

    public Championnat(String nom, LocalDate startDate, LocalDate endDate, int wonPoint, int lostPoint, int drawPoint) {
        this.nom = nom;
        this.dateCreation = LocalDate.now();
        this.startDate = startDate;
        this.endDate = endDate;
        this.wonPoint = wonPoint;
        this.lostPoint = lostPoint;
        this.drawPoint = drawPoint;
    }

    public Championnat(String nom, LocalDate startDate, LocalDate endDate) {
        this(nom, startDate, endDate, 3, 0, 1); // Default values: 3 for win, 0 for loss, 1 for draw
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Set<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(Set<Equipe> equipes) {
        this.equipes = equipes;
    }

    public List<Journee> getJournees() {
        return journees;
    }

    public void setJournees(List<Journee> journees) {
        this.journees = journees;
    }

    /**
     * Ajoute une équipe au championnat.
     * @param equipe L'équipe à ajouter.
     */
    public void addEquipe(Equipe equipe) {
        this.equipes.add(equipe);
        equipe.getChampionnats().add(this);
    }

    /**
     * Supprime une équipe du championnat.
     * @param equipe L'équipe à supprimer.
     */
    public void removeEquipe(Equipe equipe) {
        this.equipes.remove(equipe);
        equipe.getChampionnats().remove(this);
    }
    public int getDrawPoint() {
        return drawPoint;
    }
    public void setDrawPoint(int drawPoint) {
        this.drawPoint = drawPoint;
    }
    public int getLostPoint() {
        return lostPoint;
    }
    public void setLostPoint(int lostPoint) {
        this.lostPoint = lostPoint;
    }
    public int getWonPoint() {
        return wonPoint;
    }
    public void setWonPoint(int wonPoint) {
        this.wonPoint = wonPoint;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
