
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
    "photo_url" varchar NOT NULL,
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

INSERT INTO "job_title" ("id", "description", "photo_url", "created_by", "created_at", "last_modified_by", "last_modified_at")
VALUES
    ('d8f90d3f-b6a9-4c45-9f4f-951f1d1b9571', 'Principal Software Engineer', 'https://imgur.com/FR86lav.jpeg', 'admin', CURRENT_DATE, null, null),
    ('bf3e2b6b-d2e9-409f-97b3-b36ec0b1a90e', 'Junior Backend Developer', 'https://imgur.com/SM1PIwS.jpeg', 'admin', CURRENT_DATE, null, null),
    ('c7924409-7eaf-4022-b0b3-9b8a78a0bb7d', 'Data Engineer', 'https://imgur.com/Owryxit.jpeg', 'admin', CURRENT_DATE, null, null),
    ('098bd9b4-8b57-4a18-bbde-9e6d1d678cfa', 'Junior Frontend Developer', 'https://imgur.com/x4DytSx.jpeg', 'admin', CURRENT_DATE, null, null),
    ('dbf1a11c-f7f5-4b9f-98d4-e83c72ab1c57', 'Product Manager', 'https://i.imgur.com/oisJGsF.jpeg', 'admin', CURRENT_DATE, null, null),
    ('f6180285-9f66-493b-b629-0892118c6d75', 'Database Software Engineer', 'https://imgur.com/tcJoZqX.jpeg', 'admin', CURRENT_DATE, null, null),
    ('3c01ef99-9c73-4bac-a27f-24dc5089df16', 'Platform Engineer', 'https://imgur.com/hpsyVvQ.jpeg', 'admin', CURRENT_DATE, null, null),
    ('23f8785e-efce-48fc-9dcd-0141ea777fed', 'Senior Machine Learning Engineer', 'https://imgur.com/5YxqZpG.jpeg', 'admin', CURRENT_DATE, null, null),
    ('e513050a-a90a-4d3c-b820-113b9e098e52', 'Tech Lead', 'https://imgur.com/OyA5NC4.jpeg', 'admin', CURRENT_DATE, null, null);