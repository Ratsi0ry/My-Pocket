package com.example.demo.Repository;

import com.example.demo.Model.Conjoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConjointRepository extends JpaRepository<Conjoint, String> {
}
