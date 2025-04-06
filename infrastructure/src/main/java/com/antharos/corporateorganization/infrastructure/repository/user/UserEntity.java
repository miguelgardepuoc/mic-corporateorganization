package com.antharos.corporateorganization.infrastructure.repository.user;

import com.antharos.corporateorganization.domain.user.Role;
import com.antharos.corporateorganization.domain.user.Status;
import com.antharos.corporateorganization.infrastructure.repository.department.DepartmentEntity;
import com.antharos.corporateorganization.infrastructure.repository.jobtitle.JobTitleEntity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "\"user\"")
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

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(nullable = false, length = 20)
  private String telephoneNumber;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private Long employeeNumber;

  @ManyToOne
  @JoinColumn(name = "department_id", nullable = false)
  private DepartmentEntity department;

  @Column(nullable = false, unique = true)
  private String corporateEmail;

  @Column(nullable = false)
  private BigDecimal salary;

  @Temporal(TemporalType.DATE)
  @Column(nullable = false)
  private LocalDate hiringDate;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne
  @JoinColumn(name = "job_title_id", nullable = false)
  private JobTitleEntity jobTitle;

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
