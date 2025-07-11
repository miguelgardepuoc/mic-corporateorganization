package com.antharos.corporateorganization.infrastructure.out.repository.employee;

import com.antharos.corporateorganization.domain.employee.Role;
import com.antharos.corporateorganization.domain.employee.Status;
import com.antharos.corporateorganization.infrastructure.out.repository.department.DepartmentEntity;
import com.antharos.corporateorganization.infrastructure.out.repository.jobtitle.JobTitleEntity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Entity
@Table(name = "\"employee\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {

  @Id private UUID id;

  @Column(nullable = false, length = 50)
  private String dni;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String surname;

  @Column(nullable = false, columnDefinition = "role_enum")
  @Enumerated
  @JdbcType(PostgreSQLEnumJdbcType.class)
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
  @Enumerated
  @JdbcType(PostgreSQLEnumJdbcType.class)
  private Status status;

  @ManyToOne
  @JoinColumn(name = "job_title_id", nullable = false)
  private JobTitleEntity jobTitle;

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
