package com.antharos.corporateorganization.infrastructure.repository;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

  @Id private UUID id;

  @Column(nullable = false, length = 50)
  private String dni;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String surname;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private RoleEntity role;

  @Column(nullable = false, length = 20)
  private String phoneNumber;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private Long employeeNumber;

  @ManyToOne
  @JoinColumn(name = "department_id", nullable = false)
  private DepartmentEntity departmentEntity;

  @Column(nullable = false, unique = true)
  private String corporateEmail;

  @Column(nullable = false)
  private Long salary;

  @Temporal(TemporalType.DATE)
  @Column(nullable = false)
  private Date hiredAt;

  @ManyToOne
  @JoinColumn(name = "status_id", nullable = false)
  private StatusEntity status;

  @ManyToOne
  @JoinColumn(name = "job_title_id", nullable = false)
  private JobTitleEntity jobTitleEntity;

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
