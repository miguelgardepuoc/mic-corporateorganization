
CREATE TABLE "department" (
    "id" uuid NOT NULL,
    "description" varchar NOT NULL,
    "department_head_id" uuid,
    "created_by" varchar NOT NULL,
    "created_at" date NOT NULL,
    "last_modified_by" varchar,
    "last_modified_at" date,
    PRIMARY KEY ("id")
);

CREATE TABLE "job_title" (
    "id" uuid NOT NULL,
    "description" varchar NOT NULL,
    "created_by" varchar NOT NULL,
    "created_at" date NOT NULL,
    "last_modified_by" varchar,
    "last_modified_at" date,
    PRIMARY KEY ("id")
);

-- Crear el tipo ENUM para el campo "role"
CREATE TYPE role_enum AS ENUM ('DIRECCION_DE_LA_EMPRESA', 'RESPONSABLE_DE_DEPARTAMENTO', 'EMPLEADO');

CREATE TYPE status_enum AS ENUM ('ACTIVO', 'DE_BAJA', 'INACTIVO', 'DESPEDIDO');

CREATE TABLE "user" (
    "id" uuid NOT NULL,
    "dni" varchar NOT NULL,
    "name" varchar NOT NULL,
    "surname" varchar NOT NULL,
    "role" role_enum NOT NULL,
    "telephone_number" bigint NOT NULL,
    "username" varchar NOT NULL,
    "password" varchar NOT NULL,
    "employee_number" bigint NOT NULL,
    "department_id" uuid NOT NULL,
    "corporate_email" varchar NOT NULL,
    "salary" bigint NOT NULL,
    "hiring_date" date NOT NULL,
    "status" status_enum NOT NULL,
    "job_title_id" uuid NOT NULL,
    "created_by" varchar NOT NULL,
    "created_at" date NOT NULL,
    "last_modified_by" varchar,
    "last_modified_at" date,
    PRIMARY KEY ("id")
);

ALTER TABLE "department"
ADD CONSTRAINT "fk_department_department_head_id_user_id" FOREIGN KEY("department_head_id") REFERENCES "user"("id");

ALTER TABLE "user"
ADD CONSTRAINT "fk_user_department_id_department_id" FOREIGN KEY("department_id") REFERENCES "department"("id");

ALTER TABLE "user"
ADD CONSTRAINT "fk_user_job_title_id_job_title_id" FOREIGN KEY("job_title_id") REFERENCES "job_title"("id");

ALTER TABLE "user"
ADD CONSTRAINT "fk_user_role_id_role_id" FOREIGN KEY("role_id") REFERENCES "role"("id");

ALTER TABLE "user"
ADD CONSTRAINT "fk_user_status_id_status_id" FOREIGN KEY("status_id") REFERENCES "status"("id");

INSERT INTO "department" (
    "id",
    "description",
    "department_head_id",
    "created_by",
    "created_at",
    "last_modified_by",
    "last_modified_at"
) VALUES
('a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'IT Department', NULL, 'system', CURRENT_DATE, NULL, NULL),
('db978bbe-a875-4368-a58a-3a864d3af8ec', 'Human Resources', NULL, 'system', CURRENT_DATE, NULL, NULL),
('2bc45806-1fc2-47f5-a39a-2d60f399f47a', 'Finance', NULL, 'system', CURRENT_DATE, NULL, NULL),
('3bcc77b4-13c3-4f73-9627-0c27ab3ffe8d', 'Marketing', NULL, 'system', CURRENT_DATE, NULL, NULL);

INSERT INTO "job_title" ("id", "description", "created_by", "created_at", "last_modified_by", "last_modified_at")
VALUES
    ('d8f90d3f-b6a9-4c45-9f4f-951f1d1b9571', 'Software Engineer', 'admin', CURRENT_DATE, 'admin', CURRENT_DATE),
    ('bf3e2b6b-d2e9-409f-97b3-b36ec0b1a90e', 'Project Manager', 'admin', CURRENT_DATE, 'admin', CURRENT_DATE),
    ('c7924409-7eaf-4022-b0b3-9b8a78a0bb7d', 'HR Specialist', 'admin', CURRENT_DATE, 'admin', CURRENT_DATE),
    ('098bd9b4-8b57-4a18-bbde-9e6d1d678cfa', 'Data Scientist', 'admin', CURRENT_DATE, 'admin', CURRENT_DATE),
    ('dbf1a11c-f7f5-4b9f-98d4-e83c72ab1c57', 'UX Designer', 'admin', CURRENT_DATE, 'admin', CURRENT_DATE);
