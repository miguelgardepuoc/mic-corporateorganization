package com.antharos.corporateorganization.infrastructure.out.repository.jobtitle;

import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleId;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JobTitleRepositoryAdapter implements JobTitleRepository {

  private final JobTitleJpaRepository jobTitleJpaRepository;
  private final JobTitleEntityMapper mapper;

  @Override
  public List<JobTitle> findAll() {
    return this.jobTitleJpaRepository.findAll().stream().map(this.mapper::toDomain).toList();
  }

  @Override
  public Optional<JobTitle> findBy(JobTitleId userId) {
    return this.jobTitleJpaRepository
        .findById(UUID.fromString(userId.getValueAsString()))
        .map(this.mapper::toDomain);
  }
}
