package com.antharos.corporateorganization.infrastructure.out.repository.department;

import com.antharos.corporateorganization.infrastructure.out.repository.employee.EmployeeEntity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  private EmployeeEntity departmentHead;

  @Column(name = "is_active", nullable = false)
  private boolean isActive;

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
