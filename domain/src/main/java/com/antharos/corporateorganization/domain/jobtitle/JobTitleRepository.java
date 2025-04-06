package com.antharos.corporateorganization.domain.jobtitle;

import java.util.Optional;

public interface JobTitleRepository {
  Optional<JobTitle> findBy(JobTitleId jobTitleId);

}
