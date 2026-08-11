package com.example.demo.Service;

import com.example.demo.Model.Tarif;
import com.example.demo.Repository.TarifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifService {
    @Autowired
    private TarifRepository tarifRepository;

    //recuperation tarif
    public List<Tarif> getAllTarifs(){
        return tarifRepository.findAll();
    }

    //enregistrer un tarif
    public Tarif saveTarif(Tarif tarif){
        return tarifRepository.save(tarif);
    }


}
