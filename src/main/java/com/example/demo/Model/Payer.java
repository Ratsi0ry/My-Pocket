package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payer")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Payer {
    @Id
    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;

    @ManyToOne
    @JoinColumn(name="fk_numTarif", referencedColumnName = "numTarif")
    private Tarif tarif;

    @ManyToOne
    @JoinColumn(name = "fk_im", referencedColumnName = "im")
    private Personne personne;
}
