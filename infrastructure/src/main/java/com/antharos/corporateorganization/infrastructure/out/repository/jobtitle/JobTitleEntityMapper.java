package com.antharos.corporateorganization.infrastructure.out.repository.jobtitle;

import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleId;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobTitleEntityMapper {
  default JobTitle toDomain(final JobTitleEntity entity) {

    return new JobTitle(
        JobTitleId.of(String.valueOf(entity.getId())),
        entity.getDescription(),
        entity.getPhotoUrl(),
        entity.getCreatedBy(),
        entity.getCreatedAt(),
        entity.getLastModifiedBy(),
        entity.getLastModifiedAt());
  }

  default JobTitleEntity toEntity(final JobTitle domain) {
    final JobTitleEntity entity = new JobTitleEntity();

    entity.setId(UUID.fromString(domain.getId().getValueAsString()));
    entity.setDescription(domain.getDescription());
    entity.setPhotoUrl(domain.getPhotoUrl());
    entity.setCreatedBy(domain.getCreatedBy());
    entity.setCreatedAt(domain.getCreatedAt());
    entity.setLastModifiedBy(domain.getLastModifiedBy());
    entity.setLastModifiedAt(domain.getLastModifiedAt());

    return entity;
  }
}
