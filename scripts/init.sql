CREATE TABLE user (
  id UUID PRIMARY KEY NOT NULL,
  dni VARCHAR(50) NOT NULL,
  name VARCHAR(255) NOT NULL,
  surname VARCHAR(255) NOT NULL,
  role_id UUID NOT NULL,
  phone_number VARCHAR(20) NOT NULL,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  employee_number BIGINT NOT NULL,
  department_id UUID NOT NULL,
  corporate_email VARCHAR(255) NOT NULL UNIQUE,
  salary BIGINT NOT NULL CHECK (salary > 0),
  hired_at DATE NOT NULL,
  status_id UUID NOT NULL,
  job_title_id UUID NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE status (
  id UUID PRIMARY KEY NOT NULL,
  description VARCHAR(255) NOT NULL,
  created_by VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE department (
  id UUID PRIMARY KEY NOT NULL,
  description VARCHAR(255) NOT NULL,
  department_head_id UUID,
  created_by VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT department_department_head_id_fk FOREIGN KEY (department_head_id) 
    REFERENCES user (id) ON DELETE SET NULL
);

CREATE TABLE role (
  id UUID PRIMARY KEY NOT NULL,
  description VARCHAR(255) NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE job_title (
  id UUID NOT NULL PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- Foreign Key Constraints
ALTER TABLE user ADD CONSTRAINT user_department_id_fk FOREIGN KEY (department_id) REFERENCES department (id);
ALTER TABLE user ADD CONSTRAINT user_role_id_fk FOREIGN KEY (role_id) REFERENCES role (id);
ALTER TABLE user ADD CONSTRAINT user_status_id_fk FOREIGN KEY (status_id) REFERENCES status (id);
ALTER TABLE user ADD CONSTRAINT user_job_title_id_fk FOREIGN KEY (job_title_id) REFERENCES job_title(id);
