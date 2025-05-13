package com.antharos.corporateorganization.application.queries.department;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FindDepartmentsQueryHandlerUnitTest {
  private DepartmentRepository departmentRepository;
  private FindDepartmentsQueryHandler queryHandler;

  @BeforeEach
  void setUp() {
    this.departmentRepository = mock(DepartmentRepository.class);
    this.queryHandler = new FindDepartmentsQueryHandler(this.departmentRepository);
  }

  @Test
  void whenHandleIsCalled_thenReturnsAllActiveDepartments() {
    Department dept1 = new Department(DepartmentId.of(UUID.randomUUID().toString()));
    Department dept2 = new Department(DepartmentId.of(UUID.randomUUID().toString()));
    List<Department> mockDepartments = Arrays.asList(dept1, dept2);

    when(this.departmentRepository.findAllActive()).thenReturn(mockDepartments);

    List<Department> result = this.queryHandler.handle();

    assertEquals(mockDepartments, result);
    verify(this.departmentRepository, times(1)).findAllActive();
  }
}
