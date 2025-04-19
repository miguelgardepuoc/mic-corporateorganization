package com.antharos.corporateorganization.infrastructure.apirest;

import com.antharos.corporateorganization.application.find.FindJobTitlesQuery;
import com.antharos.corporateorganization.application.find.FindJobTitlesQueryHandler;
import com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.jobtitle.JobTitleMapper;
import com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.jobtitle.JobTitleResponse;
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

  @GetMapping
  public ResponseEntity<List<JobTitleResponse>> findJobTitles() {
    return ResponseEntity.ok(
        this.jobTitleMapper.toJobTitleResponse(
            this.findJobTitlesQueryHandler.handle(FindJobTitlesQuery.of()).stream().toList()));
  }
}
