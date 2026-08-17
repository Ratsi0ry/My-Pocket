package com.example.demo.Service;

import com.example.demo.Model.Payer;
import com.example.demo.Repository.PayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayerService {
    @Autowired
    private PayerRepository payerRepository;

    public List<Payer> getAllPayers(){return payerRepository.findAll();}

    public Payer getPayerById(Integer id){
        return payerRepository.findById(Long.valueOf(id)).orElse(null);
    }

    public Payer savePayer(Payer payer){
        return payerRepository.save(payer);
    }

    public void deletePayer(Integer id){
        payerRepository.deleteById(Long.valueOf(id));
    }

}
