package com.antharos.corporateorganization.infrastructure.repository.jobtitle;

import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobTitleEntityMapper {
  default JobTitle toDomain(final JobTitleEntity entity) {

    return new JobTitle(
        entity.getId(),
        entity.getDescription(),
        entity.getCreatedBy(),
        entity.getCreatedAt(),
        entity.getLastModifiedBy(),
        entity.getLastModifiedAt());
  }

  default JobTitleEntity toEntity(final JobTitle domain) {
    final JobTitleEntity entity = new JobTitleEntity();

    entity.setId(domain.getId());
    entity.setCreatedBy(domain.getCreatedBy());
    entity.setCreatedAt(domain.getCreatedAt());
    entity.setLastModifiedBy(domain.getLastModifiedBy());
    entity.setLastModifiedAt(domain.getLastModifiedAt());

    return entity;
  }
}
