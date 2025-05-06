package com.antharos.corporateorganization.infrastructure.in.controller;

import com.antharos.corporateorganization.application.queries.jobtitle.FindJobTitlesQueryHandler;
import com.antharos.corporateorganization.infrastructure.in.dto.jobtitle.JobTitleMapper;
import com.antharos.corporateorganization.infrastructure.in.dto.jobtitle.JobTitleResponse;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job-titles")
@RequiredArgsConstructor
public class JobTitleController {
  private final FindJobTitlesQueryHandler findJobTitlesQueryHandler;
  private final JobTitleMapper jobTitleMapper;

  @PermitAll
  @GetMapping
  public ResponseEntity<List<JobTitleResponse>> findJobTitles() {
    return ResponseEntity.ok(
        this.jobTitleMapper.toJobTitleResponse(
            this.findJobTitlesQueryHandler.handle().stream().toList()));
  }
}
