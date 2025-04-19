package com.antharos.corporateorganization.domain.user;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.user.repository.UserRepository;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails {

  private final UserId id;

  private Dni dni;

  private Name name;

  private Surname surname;

  private Role role;

  private TelephoneNumber telephoneNumber;

  private String username;

  private String password;

  private Long employeeNumber;

  private Department department;

  private String corporateEmail;

  private Salary salary;

  private HiringDate hiringDate;

  private Status status;

  private JobTitle jobTitle;

  private String createdBy;

  private Date createdAt;

  private String lastModifiedBy;

  private Date lastModifiedAt;

  @Builder
  public User(
      UserId id,
      Long employeeNumber,
      Dni dni,
      String username,
      String password,
      Name name,
      Surname surname,
      TelephoneNumber telephoneNumber,
      String corporateEmail,
      Status status,
      Department department,
      Salary salary,
      HiringDate hiringDate,
      Role role,
      JobTitle jobTitle,
      String createdBy,
      Date createdAt,
      String lastModifiedBy,
      Date lastModifiedAt) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.employeeNumber = employeeNumber;
    this.dni = dni;
    this.name = name;
    this.surname = surname;
    this.telephoneNumber = telephoneNumber;
    this.corporateEmail = corporateEmail;
    this.department = department;
    this.salary = salary;
    this.hiringDate = hiringDate;
    this.role = role;
    this.status = status;
    this.jobTitle = jobTitle;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
    this.lastModifiedBy = lastModifiedBy;
    this.lastModifiedAt = lastModifiedAt;
  }

  @Builder
  public User(
      UserId id,
      Long employeeNumber,
      String username,
      Dni dni,
      Name name,
      Surname surname,
      TelephoneNumber telephoneNumber,
      Department department,
      Salary salary,
      HiringDate hiringDate,
      Role role,
      JobTitle jobTitle,
      String password,
      String corporateEmail,
      Status status,
      String createdBy,
      Date createdAt,
      String lastModifiedBy,
      Date lastModifiedAt) {
    this.id = id;
    this.employeeNumber = employeeNumber;
    this.username = username;
    this.dni = dni;
    this.name = name;
    this.surname = surname;
    this.telephoneNumber = telephoneNumber;
    this.department = department;
    this.salary = salary;
    this.hiringDate = hiringDate;
    this.role = role;
    this.jobTitle = jobTitle;
    this.password = password;
    this.corporateEmail = corporateEmail;
    this.status = status;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
    this.lastModifiedBy = lastModifiedBy;
    this.lastModifiedAt = lastModifiedAt;
  }

  public User(UserId id) {
    this.id = id;
  }

  public static User create(
      UserId userId,
      Long employeeNumber,
      Dni dni,
      Name name,
      Surname surname,
      TelephoneNumber telephoneNumber,
      Salary salary,
      Department department,
      HiringDate hiringDate,
      Role role,
      JobTitle jobTitle,
      String createdBy,
      UserRepository userRepository) {

    User u =
        new User(
            userId,
            employeeNumber,
            dni,
            null,
            null,
            name,
            surname,
            telephoneNumber,
            null,
            Status.ACTIVE,
            department,
            salary,
            hiringDate,
            role,
            jobTitle,
            createdBy,
            null,
            null,
            null);

    u.username = u.generateUsername(userRepository);
    u.corporateEmail = u.username + "@antharos.com";
    u.status = Status.ACTIVE;

    return u;
  }

  private String generateUsername(UserRepository userRepository) {
    String nameInitial = this.name.value().trim().replaceAll("\\s+", "").substring(0, 1);
    String cleanSurname = this.surname.value().trim().replaceAll("\\s+", "");
    String baseUsername = (nameInitial + cleanSurname).toLowerCase();

    if (userRepository.usernameExists(baseUsername)) {
      Random random = new Random();
      baseUsername = baseUsername + random.nextInt(10000);
    }

    return baseUsername;
  }

  public void signup(String password) {
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
