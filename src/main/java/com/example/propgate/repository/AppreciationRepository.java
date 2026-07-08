package com.example.propgate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.propgate.entity.Appreciation;

@Repository
public interface AppreciationRepository extends JpaRepository<Appreciation, Long> {
}
