package com.antharos.corporateorganization.infrastructure.repository;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private String description;

  private String createdBy;

  @Column(updatable = false)
  private Date createdAt = new Date();

  private String lastModifiedBy;

  private Date lastModifiedAt;
}
