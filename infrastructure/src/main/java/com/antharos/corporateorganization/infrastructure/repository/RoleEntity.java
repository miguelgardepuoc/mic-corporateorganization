package com.antharos.corporateorganization.infrastructure.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String createdBy;

  @Column(nullable = false, updatable = false)
  private Date createdAt;

  private String lastModifiedBy;

  private Date lastModifiedAt;
}
