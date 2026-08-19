package com.example.demo.Service;

import com.example.demo.Model.Personne;
import com.example.demo.Repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonneService {
    @Autowired
    private PersonneRepository personneRepository;

    public List<Personne> getAllPersonnes() {
        return personneRepository.findAll();
    }

    public Personne getPersonneById(String im) {
        Optional<Personne> personne = personneRepository.findById(im);
        return personne.orElse(null);
    }

    public Personne savePersonne(Personne personne) {
        return personneRepository.save(personne);
    }

    public void deletePersonne(String im) {
        personneRepository.deleteById(im);
    }

    public Personne updatePersonne(String im, Personne personne) {
        Personne existingPersonne = personneRepository.findById(im).orElse(null);

        if(existingPersonne == null){
            return null;
        }

        existingPersonne.setNom(personne.getNom());
        existingPersonne.setPrenom(personne.getPrenom());
        existingPersonne.setContact(personne.getContact());
        existingPersonne.setDateNais(personne.getDateNais());
        existingPersonne.setStatut(personne.getStatut());
        existingPersonne.setSituation(personne.getSituation());

        //si le conjoint est mort
        if(!personne.getStatut()){
            int montant = existingPersonne.getTarif().getMontant();
            int montantConjoint = montant * 40 / 100;

            if(existingPersonne.getConjoint() != null){
                existingPersonne.getConjoint().setMontant(montantConjoint);
                existingPersonne.getConjoint().setStatutConjoint(true);
            }
        }

        return personneRepository.save(existingPersonne);
    }
}
