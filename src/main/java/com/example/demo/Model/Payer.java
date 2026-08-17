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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_paiement", nullable = false)
    private LocalDateTime datePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_numtarif", referencedColumnName = "numtarif")
    private Tarif tarif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_im", referencedColumnName = "im")
    private Personne personne;
}