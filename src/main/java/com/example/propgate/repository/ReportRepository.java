package com.example.propgate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.propgate.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
