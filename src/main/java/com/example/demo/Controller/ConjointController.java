package com.example.demo.Controller;

import com.example.demo.Model.Conjoint;
import com.example.demo.Service.ConjointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conjoint")
public class ConjointController {
    @Autowired
    private ConjointService conjointService;

    @GetMapping
    public List<Conjoint> getTheConjoint(){
        return conjointService.getTheConjoint();
    }

    @GetMapping("/{numPension}")
    public Conjoint getConjointById(@PathVariable String numPension){
        return conjointService.getConjointById(numPension);
    }

    @PostMapping
    public Conjoint lierConjoint(@RequestBody Conjoint conjoint) {
        return conjointService.saveConjoint(conjoint);
    }

    @DeleteMapping("/{numPension}")
    public void deleteConjoint(@PathVariable String numPension) {
        conjointService.deleteConjoint(numPension);
    }
}
