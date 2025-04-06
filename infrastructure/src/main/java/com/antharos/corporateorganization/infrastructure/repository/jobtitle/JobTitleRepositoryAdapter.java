package com.antharos.corporateorganization.infrastructure.repository.jobtitle;

import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleId;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JobTitleRepositoryAdapter implements JobTitleRepository {

  private final JobTitleJpaRepository userJpaRepository;
  private final JobTitleEntityMapper mapper;

  @Override
  public Optional<JobTitle> findBy(JobTitleId userId) {
    return this.userJpaRepository
        .findById(UUID.fromString(userId.getValueAsString()))
        .map(this.mapper::toDomain);
  }

}
