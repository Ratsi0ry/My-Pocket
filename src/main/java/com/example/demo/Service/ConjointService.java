package com.example.demo.Service;

import com.example.demo.Model.Conjoint;
import com.example.demo.Repository.ConjointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConjointService {

    @Autowired
    private ConjointRepository conjointRepository;

    public List<Conjoint> getTheConjoint() {
        return conjointRepository.findAll();
    }

    public Conjoint getConjointById(Long id) {
        return conjointRepository.findById(id).orElse(null);
    }

    public Conjoint getConjointByNumPension(String numPension) {
        return conjointRepository.findByNumPension(numPension).orElse(null);
    }

    public Conjoint saveConjoint(Conjoint conjoint) {
        return conjointRepository.save(conjoint);
    }

    public void deleteConjoint(Long id) {
        conjointRepository.deleteById(id);
    }

    public Conjoint updateInfo(String numPension, Conjoint conjoint) {
        Conjoint existingConjoint =
                conjointRepository.findByNumPension(numPension).orElse(null);

        if (existingConjoint == null) {
            return null;
        }

        existingConjoint.setNumPension(conjoint.getNumPension());
        existingConjoint.setNomConjoint(conjoint.getNomConjoint());
        existingConjoint.setPrenomConjoint(conjoint.getPrenomConjoint());
        existingConjoint.setMontant(conjoint.getMontant());
        existingConjoint.setStatutConjoint(conjoint.getStatutConjoint());
        existingConjoint.setPersonne(conjoint.getPersonne());

        return conjointRepository.save(existingConjoint);
    }

    public void deleteConjointByNumPension(String numPension) {
        Conjoint conjoint =
                conjointRepository.findByNumPension(numPension).orElse(null);

        if (conjoint != null) {
            conjointRepository.delete(conjoint);
        }
    }
}
