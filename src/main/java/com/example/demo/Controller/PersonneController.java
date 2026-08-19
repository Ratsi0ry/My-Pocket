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

    //GET
    @GetMapping
    public List<Personne> getAllPersonnes(){
        return personneService.getAllPersonnes();
    };

    //GET BY im
    @GetMapping("/{im}")
    public Personne getPersonneById(@PathVariable String im){
        return personneService.getPersonneById(im);
    }

    //POST
    @PostMapping
    public Personne createPersonne(@RequestBody Personne personne){
        return personneService.savePersonne(personne);
    }

    //PUT
    @PutMapping("/{im}")
    public Personne modifyPersonne(
            @PathVariable("im") String im,
            @RequestBody Personne personne){
        return personneService.updatePersonne(im, personne);
    }

    //DELETE
    @DeleteMapping("/{im}")
    public void deletePersonne(@PathVariable String im){
        personneService.deletePersonne(im);
    }
}
