package com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.employee;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(source = "jobTitle", target = "jobTitleId")
    @Mapping(source = "department", target = "departmentId")
    EmployeeResponse toEmployeeResponse(User user);

    List<EmployeeResponse> toEmployeeResponse(List<User> users);

    default UUID map(UserId id) {
        return id == null ? null : UUID.fromString(id.getValueAsString());
    }

    default String map(Dni dni) {
        return dni == null ? null :dni.getValueAsString();
    }

    default String map(Name name) {
        return name == null ? null : name.value();
    }

    default String map(Surname surname) {
        return surname == null ? null : surname.value();
    }

    default String map(TelephoneNumber telephoneNumber) {
        return telephoneNumber == null ? null : telephoneNumber.value();
    }

    default String map(Department department) {
        return department == null || department.getId() == null ? null : department.getId().getValueAsString();
    }

    default LocalDate map(HiringDate hiringDate) {
        return hiringDate == null ? null : hiringDate.value();
    }

    default String map(JobTitle jobTitle) {
        return jobTitle == null || jobTitle.getId() == null ? null : jobTitle.getId().toString();
    }

    default BigDecimal map(Salary salary) {
        return salary == null ? null : salary.amount();
    }
}
