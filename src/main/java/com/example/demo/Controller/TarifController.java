package com.example.demo.Controller;

import com.example.demo.Model.Tarif;
import com.example.demo.Service.TarifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifs")
public class TarifController {
    @Autowired
    private TarifService tarifService;

    //GET by numtarif
    @GetMapping("/{numtarif}")
    public Tarif getTarifById(@PathVariable("numtarif") String numTarif){
        return tarifService.getTarifById(numTarif);
    }

    //GET
    @GetMapping
    public List<Tarif> getAllTartifs(){
        return tarifService.getAllTarifs();
    };

    //POST
    @PostMapping
    public Tarif createTarif(@RequestBody Tarif tarif){
        return tarifService.saveTarif(tarif);
    }

    //PUT
    @PutMapping("/{numtarif}")
    public Tarif upddateTarif(
            @PathVariable("numtarif") String numTarif,
            @RequestBody Tarif tarif){
        return tarifService.updateTarif(numTarif, tarif);
    }

    //DELETE
    @DeleteMapping("/{numtarif}")
    public void deleteTarif(@PathVariable("numtarif") String numTarif) {
        tarifService.deleteTarif(numTarif);
    }

}
