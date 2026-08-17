package com.example.demo.Controller;

import com.example.demo.Model.Personne;
import com.example.demo.Service.PersonneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personne")
public class PersonneController {
    ///
    @Autowired
    private PersonneService personneService;

    //all person
    @GetMapping
    public List<Personne> getAllPersonnes(){
        return personneService.getAllPersonnes();
    };

    // get by id
    @GetMapping("/{im}")
    public Personne getPersonneById(@PathVariable String im){
        return personneService.getPersonneById(im);
    }

    //ajouter nouveau personne
    @PostMapping
    public Personne createPersonne(@RequestBody Personne personne){
        return personneService.savePersonne(personne);
    }

    //delete
    @DeleteMapping("/{im}")
    public void deletePersonne(@PathVariable String im){
        personneService.deletePersonne(im);
    }
}
