
CREATE TABLE "department" (
    "id" uuid NOT NULL,
    "description" varchar NOT NULL,
    "department_head_id" uuid,
    "is_active" boolean NOT NULL,
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

CREATE TYPE role_enum AS ENUM ('COMPANY_MANAGEMENT', 'DEPARTMENT_HEAD', 'EMPLOYEE');

CREATE TYPE status_enum AS ENUM ('ACTIVE', 'ON_LEAVE', 'TERMINATED', 'INACTIVE');

CREATE TABLE "employee" (
    "id" uuid NOT NULL,
    "dni" varchar NOT NULL,
    "name" varchar NOT NULL,
    "surname" varchar NOT NULL,
    "role" role_enum NOT NULL,
    "telephone_number" varchar NOT NULL,
    "username" varchar NOT NULL,
    "password" varchar,
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
ADD CONSTRAINT "fk_department_department_head_id_employee_id" FOREIGN KEY("department_head_id") REFERENCES "employee"("id");

ALTER TABLE "employee"
ADD CONSTRAINT "fk_employee_department_id_department_id" FOREIGN KEY("department_id") REFERENCES "department"("id");

ALTER TABLE "employee"
ADD CONSTRAINT "fk_employee_job_title_id_job_title_id" FOREIGN KEY("job_title_id") REFERENCES "job_title"("id");

INSERT INTO "department" (
    "id",
    "description",
    "department_head_id",
    "is_active",
    "created_by",
    "created_at",
    "last_modified_by",
    "last_modified_at"
) VALUES
('a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'Tecnología', NULL, true, 'system', CURRENT_DATE, NULL, NULL),
('db978bbe-a875-4368-a58a-3a864d3af8ec', 'Recursos Humanos', NULL, true,'system', CURRENT_DATE, NULL, NULL),
('2bc45806-1fc2-47f5-a39a-2d60f399f47a', 'Finanzas', NULL, true,'system', CURRENT_DATE, NULL, NULL),
('3bcc77b4-13c3-4f73-9627-0c27ab3ffe8d', 'Marketing', NULL, true,'system', CURRENT_DATE, NULL, NULL);

INSERT INTO "job_title" ("id", "description", "photo_url", "created_by", "created_at", "last_modified_by", "last_modified_at")
VALUES
    ('d8f90d3f-b6a9-4c45-9f4f-951f1d1b9571', 'Principal Software Engineer', 'https://imgur.com/x6DIWN5.jpeg', 'admin', CURRENT_DATE, null, null),
    ('bf3e2b6b-d2e9-409f-97b3-b36ec0b1a90e', 'Junior Backend Developer', 'https://imgur.com/yyF52QG.jpeg', 'admin', CURRENT_DATE, null, null),
    ('c7924409-7eaf-4022-b0b3-9b8a78a0bb7d', 'Data Engineer', 'https://i.imgur.com/AidnsCc.jpeg', 'admin', CURRENT_DATE, null, null),
    ('098bd9b4-8b57-4a18-bbde-9e6d1d678cfa', 'Junior Frontend Developer', 'https://imgur.com/BUzzjT4.jpeg', 'admin', CURRENT_DATE, null, null),
    ('dbf1a11c-f7f5-4b9f-98d4-e83c72ab1c57', 'Product Manager', 'https://imgur.com/JUSkJqR.jpeg', 'admin', CURRENT_DATE, null, null),
    ('f6180285-9f66-493b-b629-0892118c6d75', 'Database Software Engineer', 'https://imgur.com/g46tVVi.jpeg', 'admin', CURRENT_DATE, null, null),
    ('3c01ef99-9c73-4bac-a27f-24dc5089df16', 'Platform Engineer', 'https://imgur.com/VQAQnyc.jpeg', 'admin', CURRENT_DATE, null, null),
    ('23f8785e-efce-48fc-9dcd-0141ea777fed', 'Senior Machine Learning Engineer', 'https://imgur.com/R4gedIn.jpeg', 'admin', CURRENT_DATE, null, null),
    ('e513050a-a90a-4d3c-b820-113b9e098e52', 'Tech Lead', 'https://imgur.com/KIxjFAE.jpeg', 'admin', CURRENT_DATE, null, null);

INSERT INTO public."employee"
(id, dni, "name", surname, "role", telephone_number, username, employee_number, department_id, corporate_email, salary, hiring_date, status, job_title_id, created_by, created_at)
VALUES
('e5453fe0-b434-409d-ba9d-b96eb7f22a68', '12345678A', 'John', 'Doe', 'EMPLOYEE'::role_enum, 6653093811, 'jperez', 1, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'john.doe@company.com', 50000, '2023-06-01', 'ACTIVE'::status_enum, 'd8f90d3f-b6a9-4c45-9f4f-951f1d1b9571', 'admin', '2023-06-01 10:00:00');
