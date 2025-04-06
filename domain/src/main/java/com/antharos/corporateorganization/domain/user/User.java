package com.antharos.corporateorganization.domain.user;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.user.repository.UserRepository;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

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
      Dni dni,
      Name name,
      Surname surname,
      TelephoneNumber telephoneNumber,
      Department department,
      Salary salary,
      HiringDate hiringDate,
      Role role,
      JobTitle jobTitle,
      String createdBy) {
    this.id = id;
    this.dni = dni;
    this.name = name;
    this.surname = surname;
    this.telephoneNumber = telephoneNumber;
    this.department = department;
    this.salary = salary;
    this.hiringDate = hiringDate;
    this.role = role;
    this.jobTitle = jobTitle;
    this.createdBy = createdBy;
  }

  public User(UserId id) {
    this.id = id;
  }

  public static User create(
      UserId userId,
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
            dni,
            name,
            surname,
            telephoneNumber,
            department,
            salary,
            hiringDate,
            role,
            jobTitle,
            createdBy);

    u.generateUsername(userRepository);
    u.generateRandomPassword();
    u.generateUniqueEmployeeNumber(userRepository);
    u.corporateEmail = u.username + "@antharos.com";
    u.status = Status.ACTIVO;

    return u;
  }

  private void generateUsername(UserRepository userRepository) {
    String baseUsername = (this.name.value().charAt(0) + this.surname.value()).toLowerCase();

    if (userRepository.usernameExists(baseUsername)) {
      Random random = new Random();
      baseUsername = baseUsername + random.nextInt(10000);
    }

    this.username = baseUsername;
  }

  private void generateRandomPassword() {
    final String CHARACTERS =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?";

    SecureRandom random = new SecureRandom();
    StringBuilder password = new StringBuilder();

    for (int i = 0; i < 10; i++) {
      int index = random.nextInt(CHARACTERS.length());
      password.append(CHARACTERS.charAt(index));
    }

    this.password = password.toString();
  }

  private void generateUniqueEmployeeNumber(UserRepository userRepository) {
    final Random random = new Random();
    long employeeNumber;
    do {
      employeeNumber = 1000 + random.nextInt(9000);
    } while (userRepository.existsByEmployeeNumber(employeeNumber));
    this.employeeNumber = employeeNumber;
  }
}
