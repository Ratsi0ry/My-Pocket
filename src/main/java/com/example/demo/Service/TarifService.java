package com.example.demo.Service;

import com.example.demo.Model.Tarif;
import com.example.demo.Repository.TarifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifService {
    @Autowired
    private TarifRepository tarifRepository;

    public List<Tarif> getAllTarifs(){
        return tarifRepository.findAll();
    }

    public Tarif saveTarif(Tarif tarif){
        return tarifRepository.save(tarif);
    }

    public Tarif getTarifById(String numTarif) {
        Optional<Tarif> tarif = tarifRepository.findById(numTarif);
        return tarif.orElse(null);
    }

    public Tarif updateTarif(String numTarif, Tarif tarif) {
        Tarif existingTarif = tarifRepository.findById(numTarif).orElse(null);

        if(existingTarif == null){
            return null;
        }

        existingTarif.setDiplome(tarif.getDiplome());
        existingTarif.setCategorie(tarif.getCategorie());
        existingTarif.setMontant(tarif.getMontant());

        return tarifRepository.save(existingTarif);
    }

    public void deleteTarif(String numTarif) {
        tarifRepository.deleteById(numTarif);
    }
}
