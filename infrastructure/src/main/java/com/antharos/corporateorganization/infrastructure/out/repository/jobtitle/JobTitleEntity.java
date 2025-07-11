package com.antharos.corporateorganization.infrastructure.out.repository.jobtitle;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "job_title")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobTitleEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String photoUrl;

  @Column(nullable = false)
  private String createdBy;

  @Column(nullable = false, updatable = false)
  private LocalDate createdAt;

  private String lastModifiedBy;

  private LocalDate lastModifiedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDate.now();
  }

  @PreUpdate
  protected void onUpdate() {
    lastModifiedAt = LocalDate.now();
  }
}
