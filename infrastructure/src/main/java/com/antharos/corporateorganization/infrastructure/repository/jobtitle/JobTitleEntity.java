package com.antharos.corporateorganization.infrastructure.repository.jobtitle;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import java.util.Date;
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

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date createdAt;

  private String lastModifiedBy;

  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    lastModifiedAt = new Date();
  }
}
