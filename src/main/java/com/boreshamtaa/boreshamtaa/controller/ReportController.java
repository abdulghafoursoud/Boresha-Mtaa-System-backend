package com.boreshamtaa.boreshamtaa.controller;
// import java.util.HashMap;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boreshamtaa.boreshamtaa.model.Report;
import com.boreshamtaa.boreshamtaa.service.ReportService;
// import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    
 private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

 // update status

 @PutMapping("/status/{id}")
    public ResponseEntity<Report> updateReportStatus(
        @PathVariable Long id,
        @RequestBody Map<String, String> updates
    ) {
        Report updatedReport = reportService.updateReportStatus(id, updates.get("status"));
        if (updatedReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReport);
    }


// fetch using ward
@GetMapping("/ward")
    public ResponseEntity<List<Report>> getEventsByWard(@RequestParam String ward) {
        List<Report> reports = reportService.getEventsByWard(ward);
        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reports);
    }

// INSERT INTO DATABASE WITH IMAGE FILE
   @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<Report> uploadCitizen(
        @RequestPart(value = "eventphoto", required = false) MultipartFile eventphoto,
        @RequestPart("description") String description,
        @RequestPart("status") String status,
        @RequestPart("zanId") String zanId,
        @RequestPart("ward") String ward,
        @RequestPart("location") String location,
        @RequestPart("dateSubmitted") String dateSubmitted) throws IOException {

    Report report = new Report();
    report.setDescription(description);
    report.setStatus(status);
    report.setZanId(zanId);
    report.setWard(ward);
    report.setLocation(location);
    report.setDateSubmitted(LocalDate.parse(dateSubmitted)); // Expect format yyyy-MM-dd

    if (eventphoto != null && !eventphoto.isEmpty()) {
        report.seteventphoto(eventphoto.getBytes());
    }

    Report saveReport = reportService.saveReport(report);
    return ResponseEntity.ok(saveReport);
}

//count all reports
 @GetMapping("/count")
    public Map<String, Long> countAllReports() {
        long count = reportService.countAllReports();
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return response;
    }
    //COUNT ALL REPORT SUBMITTED using ward
@GetMapping("/count-by-ward")
public Map<String, Long> countReportsByWard(@RequestParam String ward) {
    long count = reportService.countReportsByWard(ward);
    return Collections.singletonMap("count", count);
}

//COUNT ALL REPORT SUBMITTED using zanid
 @GetMapping("/count_report/{zanId}")
    public ResponseEntity<Long> getReportCount(@PathVariable String zanId) {
        long count = reportService.getReportCountByZanId(zanId);
        return ResponseEntity.ok(count);
    }
    //  FETCH REPORT USING ZANID
@GetMapping("/get_by_zanid/{zanId}")
    public List<Report> getReportsByZanId(@PathVariable String zanId) {
        return reportService.getReportsByZanId(zanId);
    }

@GetMapping
public List<Report> getAllReports() {
    List<Report> reports = reportService.getAllReports();
    return reports; 
}

@GetMapping("/{id}")
public ResponseEntity<Report> getCitizenById(@PathVariable Long id) {
    return reportService.getReportById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

    
    @PutMapping("/{id}")
public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report updatedReport) {
    return reportService.getReportById(id)
        .map(existingReport -> {
            existingReport.setDescription(updatedReport.getDescription());
            existingReport.setStatus(updatedReport.getStatus());
            existingReport.setDateSubmitted(updatedReport.getDateSubmitted());
            existingReport.setZanId(updatedReport.getZanId());
            existingReport.setWard(updatedReport.getWard());
            existingReport.setLocation(updatedReport.getLocation());
            existingReport.seteventphoto(updatedReport.geteventphoto());

            Report savedReport = reportService.saveReport(existingReport);
            return ResponseEntity.ok(savedReport);
        })
        .orElse(ResponseEntity.notFound().build());
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCitizen(@PathVariable Long id) {
        if (reportService.existsById(id)) {
            reportService.deleteReport(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
