package com.antharos.corporateorganization.application.queries.jobtitle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleId;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FindJobTitlesQueryHandlerUnitTest {

  private JobTitleRepository jobTitleRepository;
  private FindJobTitlesQueryHandler queryHandler;

  @BeforeEach
  void setUp() {
    this.jobTitleRepository = mock(JobTitleRepository.class);
    this.queryHandler = new FindJobTitlesQueryHandler(this.jobTitleRepository);
  }

  @Test
  void whenHandleIsCalled_thenReturnsAllJobTitles() {
    JobTitle jobTitle1 = new JobTitle(JobTitleId.of(UUID.randomUUID().toString()));
    JobTitle jobTitle2 = new JobTitle(JobTitleId.of(UUID.randomUUID().toString()));
    List<JobTitle> mockJobTitles = Arrays.asList(jobTitle1, jobTitle2);

    when(this.jobTitleRepository.findAll()).thenReturn(mockJobTitles);

    List<JobTitle> result = this.queryHandler.handle();

    assertEquals(2, result.size());
    assertTrue(result.contains(jobTitle1));
    assertTrue(result.contains(jobTitle2));

    verify(this.jobTitleRepository, times(1)).findAll();
  }

  @Test
  void whenNoJobTitlesExist_thenReturnsEmptyList() {
    when(this.jobTitleRepository.findAll()).thenReturn(List.of());

    List<JobTitle> result = this.queryHandler.handle();

    assertTrue(result.isEmpty());

    verify(this.jobTitleRepository, times(1)).findAll();
  }
}
