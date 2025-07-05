package com.boreshamtaa.boreshamtaa.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import com.boreshamtaa.boreshamtaa.model.Citizen;
import com.boreshamtaa.boreshamtaa.model.Report;
import com.boreshamtaa.boreshamtaa.repository.ReportRepository;



@Service
public class ReportService {

    private final ReportRepository reportRepository;

     @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
    

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }
// count all event using zanid
public long getReportCountByZanId(String zanId) {
        return reportRepository.countByZanId(zanId);
    }

// fetch reports details by zanid

public List<Report> getReportsByZanId(String zanId) {
        return reportRepository.findByZanId(zanId);
    }
// fetch by ward
 public List<Report> getEventsByWard(String ward) {
        return reportRepository.findByWardIgnoreCase(ward);
    }

    //update status
 public Report updateReportStatus(Long id, String newStatus) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (!optionalReport.isPresent()) {
            return null;
        }
        Report report = optionalReport.get();
        report.setStatus(newStatus);
        return reportRepository.save(report);
    }

//count by ward
public long countReportsByWard(String ward) {
        return reportRepository.countByWard(ward);
    }
    
public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
 //count all reports
public long countAllReports() {
        return reportRepository.count();
    }
    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return reportRepository.existsById(id);
    }
    
}
