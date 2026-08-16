package com.example.demo.Service;

import com.example.demo.Model.Conjoint;
import com.example.demo.Repository.ConjointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConjointService {
    @Autowired
    private ConjointRepository conjointRepository;

    public List<Conjoint> getTheConjoint() {return conjointRepository.findAll();}

    //recuperation conjoint par num id
    public Conjoint getConjointById(String numPension){
        Optional<Conjoint> conjoint = conjointRepository.findById(numPension);
        return conjoint.orElse(null);
    }

    // enregistrement et ou mdification
    public Conjoint saveConjoint(Conjoint conjoint) {
        return conjointRepository.save(conjoint);
    }

    //suppression conjoint
    public void deleteConjoint(String numPension) {
        conjointRepository.deleteById(numPension);
    }
}
