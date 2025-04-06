package com.antharos.corporateorganization.infrastructure.repository.jobtitle;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTitleJpaRepository extends JpaRepository<JobTitleEntity, UUID> {}
