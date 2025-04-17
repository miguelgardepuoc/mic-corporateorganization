package com.antharos.corporateorganization.domain.jobtitle;

import java.util.List;
import java.util.Optional;

public interface JobTitleRepository {
  List<JobTitle> findAll();

  Optional<JobTitle> findBy(JobTitleId jobTitleId);
}
