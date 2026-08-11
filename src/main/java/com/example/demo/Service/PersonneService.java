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

    //renvoyer touts
    public List<Personne> getAllPersonne(){
        return personneRepository.findAll();
    }

    //renvoyer l'IM
    public Personne getPersonneById(String im){
        Optional<Personne> personne = personneRepository.findById(im);
        return personne.orElse(null);
    }

    //enregistrer || modifier
























































































































































































}
