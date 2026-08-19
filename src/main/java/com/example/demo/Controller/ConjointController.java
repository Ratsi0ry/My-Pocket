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

    // GET all
    @GetMapping
    public List<Conjoint> getTheConjoint() {
        return conjointService.getTheConjoint();
    }

    // GET by numPension
    @GetMapping("/{numpension}")
    public Conjoint getConjointByNumPension(
            @PathVariable("numpension") String numPension) {
        return conjointService.getConjointByNumPension(numPension);
    }

    // POST
    @PostMapping
    public Conjoint lierConjoint(@RequestBody Conjoint conjoint) {
        return conjointService.saveConjoint(conjoint);
    }

    // PUT
    @PutMapping("/{numpension}")
    public Conjoint updateConjointInfo(
            @PathVariable("numpension") String numPension,
            @RequestBody Conjoint conjoint) {
        return conjointService.updateInfo(numPension, conjoint);
    }

    // DELETE by numPension
    @DeleteMapping("/{numpension}")
    public void deleteConjoint(
            @PathVariable("numpension") String numPension) {
        conjointService.deleteConjointByNumPension(numPension);
    }
}
