package com.example.demo.Repository;

import com.example.demo.Model.Conjoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConjointRepository extends JpaRepository<Conjoint, Long> {
    Optional<Conjoint> findByNumPension(String numPension);
}
