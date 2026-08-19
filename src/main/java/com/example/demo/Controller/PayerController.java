package com.example.demo.Controller;

import com.example.demo.Model.Payer;
import com.example.demo.Service.PayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payer")
public class PayerController {
    @Autowired
    private PayerService payerService;

    //GET
    @GetMapping
    public List<Payer> getALlPayers(){
        return payerService.getAllPayers();
    }

    //GET By ID
    @GetMapping("/{id}")
    public Payer getPayerById(@PathVariable Integer id){
        return payerService.getPayerById(id);
    }

    //POST
    @PostMapping
    public Payer createPayer(@RequestBody Payer payer){
        return payerService.savePayer(payer);
    }

    //PUT
    @PutMapping("/{id}")
    public Payer ModifyPaie(
            @PathVariable("id") Long id,
            @RequestBody Payer payer){
        return payerService.updatePaie(id, payer);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public void deletePayer(@PathVariable Integer id) {
        payerService.deletePayer(id);
    }

}
