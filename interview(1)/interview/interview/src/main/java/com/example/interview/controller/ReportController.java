package com.example.interview.controller;

import com.example.interview.model.Report;
import com.example.interview.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/report")
    public String reportForm(@RequestParam Long postId, Model model) {
        model.addAttribute("postId", postId);
        return "report_form";
    }

    @PostMapping("/report")
    public String reportSubmit(@RequestParam Long postId, @RequestParam String issue) {
        Report report = new Report();
        report.setPostId(postId);
        report.setIssue(issue);
        reportService.save(report);
        return "redirect:/posts";
    }
}
