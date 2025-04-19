package com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.jobtitle;

import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobTitleMapper {
  JobTitleResponse toJobTitleResponse(JobTitle jobTitle);

  List<JobTitleResponse> toJobTitleResponse(List<JobTitle> jobTitles);
}
