package com.boreshamtaa.boreshamtaa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.boreshamtaa.boreshamtaa.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    
    //count reports using zanid
    long countByZanId(String zanId);
    // fetch reports using zanid
    List<Report> findByZanId(String zanId);
    
    // fetch by ward 
    List<Report> findByWardIgnoreCase(String ward);
    //count using ward
    long countByWard(String ward);

    @Modifying
@Transactional
void deleteByZanId(String zanId);

}
