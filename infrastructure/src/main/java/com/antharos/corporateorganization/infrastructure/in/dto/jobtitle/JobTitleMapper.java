package com.antharos.corporateorganization.infrastructure.in.dto.jobtitle;

import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleId;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobTitleMapper {
  JobTitleResponse toJobTitleResponse(JobTitle jobTitle);

  List<JobTitleResponse> toJobTitleResponse(List<JobTitle> jobTitles);

  default String map(JobTitleId id) {
    return id == null ? null : id.getValueAsString();
  }
}
