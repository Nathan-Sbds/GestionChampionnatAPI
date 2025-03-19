package com.ipi.gestionchampionnatapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant une journée (matchday) dans un championnat.
 */
@Entity
@Table(name = "journees")
public class Journee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le numéro de la journée est obligatoire")
    private Integer numero;

    private LocalDate date = LocalDate.now();

    // Relation Many-to-One avec Championnat
    @ManyToOne
    @JoinColumn(name = "championnat_id", nullable = false)
    private Championnat championnat;

    // Relation One-to-Many avec Resultat
    @OneToMany(mappedBy = "journee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Resultat> resultats = new ArrayList<>();

    // Constructeurs, getters et setters

    public Journee() {
    }

    public Journee(Integer numero, Championnat championnat) {
        this.numero = numero;
        this.championnat = championnat;
        this.date = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Championnat getChampionnat() {
        return championnat;
    }

    public void setChampionnat(Championnat championnat) {
        this.championnat = championnat;
    }

    public List<Resultat> getResultats() {
        return resultats;
    }

    public void setResultats(List<Resultat> resultats) {
        this.resultats = resultats;
    }
}
