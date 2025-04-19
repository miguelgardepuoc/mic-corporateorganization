package com.antharos.corporateorganization.application.queries.jobtitle;

import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindJobTitlesQueryHandler {
  private final JobTitleRepository jobTitleRepository;

  public List<JobTitle> handle() {
    return this.jobTitleRepository.findAll();
  }
}
