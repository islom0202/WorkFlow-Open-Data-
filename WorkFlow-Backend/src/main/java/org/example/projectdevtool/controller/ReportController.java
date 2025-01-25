package org.example.projectdevtool.controller;

import lombok.AllArgsConstructor;
import org.example.projectdevtool.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/download/{projectId}")
    public ResponseEntity<?> getReport(@PathVariable("projectId") Long projectId){
        byte[] pdfContent = reportService.getReportById(projectId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }


    @GetMapping("/get/{projectId}")
    public ResponseEntity<?> findReport(@PathVariable("projectId") Long projectId){
        return ResponseEntity.ok(reportService.findReport(projectId));
    }
}
