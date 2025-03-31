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
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name = "department_head_id")
  private UserEntity departmentHead;

  @Column(nullable = false)
  private String createdBy;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date createdAt;

  private String lastModifiedBy;

  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedAt;
}
