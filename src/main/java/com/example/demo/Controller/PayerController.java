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

    @GetMapping
    public List<Payer> getALlPayers(){
        return payerService.getAllPayers();
    }

    @GetMapping("/{id}")
    public Payer getPayerById(@PathVariable Integer id){
        return payerService.getPayerById(id);
    }

    @PostMapping
    public Payer createPayer(@RequestBody Payer payer){
        return payerService.savePayer(payer);
    }

    @DeleteMapping("/{id}")
    public void deletePayer(@PathVariable Integer id) {
        payerService.deletePayer(id);
    }

}
