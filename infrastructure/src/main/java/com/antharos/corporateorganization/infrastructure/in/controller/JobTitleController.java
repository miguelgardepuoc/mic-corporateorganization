package com.antharos.corporateorganization.infrastructure.in.controller;

import com.antharos.corporateorganization.application.queries.jobtitle.FindJobTitlesQueryHandler;
import com.antharos.corporateorganization.infrastructure.in.dto.jobtitle.JobTitleMapper;
import com.antharos.corporateorganization.infrastructure.in.dto.jobtitle.JobTitleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-titles")
@RequiredArgsConstructor
@Tag(name = "Job Title", description = "Operations related to job titles")
public class JobTitleController {

  private final FindJobTitlesQueryHandler findJobTitlesQueryHandler;
  private final JobTitleMapper jobTitleMapper;

  @PermitAll
  @GetMapping
  @Operation(
      summary = "List all job titles",
      description = "Retrieves a list of available job titles")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved list",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = JobTitleResponse.class)))
      })
  public ResponseEntity<List<JobTitleResponse>> findJobTitles() {
    return ResponseEntity.ok(
        this.jobTitleMapper.toJobTitleResponse(
            this.findJobTitlesQueryHandler.handle().stream().toList()));
  }
}
