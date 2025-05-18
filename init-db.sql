
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

CREATE TYPE role_enum AS ENUM ('ROLE_COMPANY_MANAGEMENT', 'ROLE_DEPARTMENT_HEAD', 'ROLE_EMPLOYEE');

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
(id, dni, "name", surname, "role", telephone_number, username, password, employee_number, department_id, corporate_email, salary, hiring_date, status, job_title_id, created_by, created_at)
VALUES
('e5453fe0-b434-409d-ba9d-b96eb7f22a68', '12345678A', 'Jorge', 'Pérez', 'ROLE_COMPANY_MANAGEMENT'::role_enum, 6653093811, 'jperez', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe', 1, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'john.doe@antharos.com', 100000, '2025-01-01', 'ACTIVE'::status_enum, 'd8f90d3f-b6a9-4c45-9f4f-951f1d1b9571', 'admin', '2025-01-01 00:00:00'),
('a123b456-c789-012d-ef34-56789abcdef0', '87654321B', 'Lucía', 'González', 'ROLE_DEPARTMENT_HEAD'::role_enum, 6691122334, 'lgonzalez', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe', 2, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'lucia.gonzalez@antharos.com', 65000, '2025-01-01', 'ACTIVE'::status_enum, '23f8785e-efce-48fc-9dcd-0141ea777fed', 'jperez', '2025-02-15 09:30:00'),
('c789d012-e345-678f-9012-abcdef123456', '11223344C', 'Carlos', 'Ramírez', 'ROLE_EMPLOYEE'::role_enum, 6789012345, 'cramirez', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe', 3, 'db978bbe-a875-4368-a58a-3a864d3af8ec', 'carlos.ramirez@antharos.com', 40000, '2025-01-01', 'ACTIVE'::status_enum, 'e513050a-a90a-4d3c-b820-113b9e098e52', 'jperez', '2025-02-17 08:45:00'),
('c2998551-89b1-4021-b3a6-da5b317ebc4b', '75181629E', 'Pedro', 'Moreno', 'ROLE_DEPARTMENT_HEAD'::role_enum,6648994027, 'pmoreno4', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',4, 'db978bbe-a875-4368-a58a-3a864d3af8ec', 'pedro.moreno@antharos.com', 35000,'2025-01-01', 'ACTIVE'::status_enum, '098bd9b4-8b57-4a18-bbde-9e6d1d678cfa', 'jperez', '2025-01-01 00:00:00'),
('459765f5-1f16-439e-99db-58d20d93b31f', '59045826A', 'Laura', 'Ruiz', 'ROLE_EMPLOYEE'::role_enum,6624734666, 'lruiz5', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',5, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'laura.ruiz@antharos.com', 60000,'2025-01-01', 'ACTIVE'::status_enum, '098bd9b4-8b57-4a18-bbde-9e6d1d678cfa', 'jperez', '2025-01-01 00:00:00'),
('060a3c3a-d6d1-423f-9a67-014c6745c8cd', '71234135I', 'Ana', 'Muñoz', 'ROLE_EMPLOYEE'::role_enum,6620371452, 'amuñoz6', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',6, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'ana.muñoz@antharos.com', 60000,'2025-01-01', 'ACTIVE'::status_enum, 'bf3e2b6b-d2e9-409f-97b3-b36ec0b1a90e', 'jperez', '2025-01-01 00:00:00'),
('5f7cee2f-0e82-4c72-84f6-a66e9e4738e9', '53475484H', 'Ana', 'Álvarez', 'ROLE_EMPLOYEE'::role_enum,6682806224, 'aálvarez7', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',7, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'ana.álvarez@antharos.com', 60000,'2025-01-01', 'ACTIVE'::status_enum, '3c01ef99-9c73-4bac-a27f-24dc5089df16', 'jperez', '2025-01-01 00:00:00'),
('16332dbf-7b12-4dd8-aeb0-7cc3b87847cd', '80563048B', 'Diego', 'Ruiz', 'ROLE_EMPLOYEE'::role_enum,6635482330, 'druiz8', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',8, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'diego.ruiz@antharos.com', 60000,'2025-01-01', 'ACTIVE'::status_enum, '098bd9b4-8b57-4a18-bbde-9e6d1d678cfa', 'jperez', '2025-01-01 00:00:00'),
('ac51b013-34cd-443a-ac6e-e55fbe2bc82b', '17563605C', 'Sofía', 'Torres', 'ROLE_EMPLOYEE'::role_enum,6641247703, 'storres9', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',9, 'db978bbe-a875-4368-a58a-3a864d3af8ec', 'sofía.torres@antharos.com', 35000,'2025-02-01', 'ACTIVE'::status_enum, 'bf3e2b6b-d2e9-409f-97b3-b36ec0b1a90e', 'jperez', '2025-02-01 00:00:00'),
('0c28992c-654c-4232-a5b2-08be9fcece76', '46950938Z', 'Raúl', 'Díaz', 'ROLE_EMPLOYEE'::role_enum,6670363380, 'rdíaz10', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',10, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'raúl.díaz@antharos.com', 60000,'2025-02-01', 'ACTIVE'::status_enum, 'd8f90d3f-b6a9-4c45-9f4f-951f1d1b9571', 'jperez', '2025-02-01 00:00:00'),
('14461bbe-f05c-4de7-ac2a-f95e220a8aa6', '62946516J', 'Ana', 'Torres', 'ROLE_EMPLOYEE'::role_enum,6615636039, 'atorres11', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',11, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'ana.torres@antharos.com', 60000,'2025-02-01', 'ACTIVE'::status_enum, 'd8f90d3f-b6a9-4c45-9f4f-951f1d1b9571', 'jperez', '2025-02-01 00:00:00'),
('f663e95b-d6ff-4631-990e-284b30afecda', '17242355Z', 'Sofía', 'Torres', 'ROLE_DEPARTMENT_HEAD'::role_enum,6680105272, 'storres12', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',12, '2bc45806-1fc2-47f5-a39a-2d60f399f47a', 'sofía.torres@antharos.com', 50000,'2025-02-01', 'ACTIVE'::status_enum, 'e513050a-a90a-4d3c-b820-113b9e098e52', 'jperez', '2025-02-01 00:00:00'),
('8d4e74d5-f638-4561-ba68-fcc939d665d2', '60099233R', 'Raúl', 'Sánchez', 'ROLE_DEPARTMENT_HEAD'::role_enum,6644088976, 'rsánchez13', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',13, '3bcc77b4-13c3-4f73-9627-0c27ab3ffe8d', 'raúl.sánchez@antharos.com', 25000,'2025-02-01', 'ACTIVE'::status_enum, 'bf3e2b6b-d2e9-409f-97b3-b36ec0b1a90e', 'jperez', '2025-02-01 00:00:00'),
('61be8227-25ad-427a-a9cb-8fc3d5351e1e', '56062269Z', 'Raúl', 'Moreno', 'ROLE_EMPLOYEE'::role_enum,6646698367, 'rmoreno14', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',14, 'db978bbe-a875-4368-a58a-3a864d3af8ec', 'raúl.moreno@antharos.com', 30000,'2025-02-01', 'ACTIVE'::status_enum, '3c01ef99-9c73-4bac-a27f-24dc5089df16', 'jperez', '2025-02-01 00:00:00'),
('7da4ec55-6ee1-40ce-8815-36a52473e949', '15123780N', 'Diego', 'Moreno', 'ROLE_EMPLOYEE'::role_enum,6686666609, 'dmoreno15', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',15, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'diego.moreno@antharos.com', 60000,'2025-02-01', 'ACTIVE'::status_enum, '098bd9b4-8b57-4a18-bbde-9e6d1d678cfa', 'jperez', '2025-02-01 00:00:00'),
('8012bbe3-b7d0-4f62-aee0-00b7fcbbfdad', '84987421H', 'Marta', 'López', 'ROLE_EMPLOYEE'::role_enum,6651655822, 'mlópez16', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',16, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'marta.lópez@antharos.com', 60000,'2025-02-01', 'ACTIVE'::status_enum, 'dbf1a11c-f7f5-4b9f-98d4-e83c72ab1c57', 'jperez', '2025-02-01 00:00:00'),
('ffeec586-1137-44dd-bcd5-c53aec9841b8', '49859538Y', 'Marta', 'Moreno', 'ROLE_EMPLOYEE'::role_enum,6668459134, 'mmoreno17', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',17, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'marta.moreno@antharos.com', 60000,'2025-02-01', 'ACTIVE'::status_enum, 'bf3e2b6b-d2e9-409f-97b3-b36ec0b1a90e', 'jperez', '2025-02-01 00:00:00'),
('07d83a16-db40-4568-91ac-a46112f2e505', '94723744U', 'Laura', 'Torres', 'ROLE_EMPLOYEE'::role_enum,6684967777, 'ltorres18', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',18, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'laura.torres@antharos.com', 60000,'2025-02-01', 'ACTIVE'::status_enum, '098bd9b4-8b57-4a18-bbde-9e6d1d678cfa', 'jperez', '2025-02-01 00:00:00'),
('921a8396-e83e-4711-8173-606e4471b235', '11501131M', 'Raúl', 'Gómez', 'ROLE_EMPLOYEE'::role_enum,6673242953, 'rgómez19', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',19, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'raúl.gómez@antharos.com', 60000,'2025-03-01', 'ACTIVE'::status_enum, 'dbf1a11c-f7f5-4b9f-98d4-e83c72ab1c57', 'jperez', '2025-03-01 00:00:00'),
('5c5e80fc-f909-4b85-8302-43dd183e72fe', '90930901P', 'Pedro', 'López', 'ROLE_EMPLOYEE'::role_enum,6683148746, 'plópez20', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',20, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'pedro.lópez@antharos.com', 60000,'2025-03-01', 'ACTIVE'::status_enum, 'f6180285-9f66-493b-b629-0892118c6d75', 'jperez', '2025-03-01 00:00:00'),
('ff427458-93e3-444c-a0ff-e8234887e719', '22530914F', 'Pedro', 'Sánchez', 'ROLE_EMPLOYEE'::role_enum,6680377733, 'psánchez21', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',21, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'pedro.sánchez@antharos.com', 60000,'2025-03-01', 'ACTIVE'::status_enum, 'bf3e2b6b-d2e9-409f-97b3-b36ec0b1a90e', 'jperez', '2025-03-01 00:00:00'),
('63b4ce5b-6b19-4a45-9de5-93b8f3859979', '38349024Z', 'Raúl', 'Díaz', 'ROLE_EMPLOYEE'::role_enum,6672912392, 'rdíaz22', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',22, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'raúl.díaz@antharos.com', 60000,'2025-03-01', 'ACTIVE'::status_enum, 'e513050a-a90a-4d3c-b820-113b9e098e52', 'jperez', '2025-03-01 00:00:00'),
('824986da-c525-4cca-9911-e1a4d71bb528', '64882290H', 'Hugo', 'Sánchez', 'ROLE_EMPLOYEE'::role_enum,6654680271, 'hsánchez23', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',23, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'hugo.sánchez@antharos.com', 60000,'2025-03-01', 'ACTIVE'::status_enum, 'bf3e2b6b-d2e9-409f-97b3-b36ec0b1a90e', 'jperez', '2025-03-01 00:00:00'),
('c553598a-a86d-4130-a0cd-ac0071bbd88a', '71549026V', 'Isabel', 'Muñoz', 'ROLE_EMPLOYEE'::role_enum,6623920986, 'imuñoz24', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',24, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'isabel.muñoz@antharos.com', 60000,'2025-03-01', 'ACTIVE'::status_enum, 'c7924409-7eaf-4022-b0b3-9b8a78a0bb7d', 'jperez', '2025-03-01 00:00:00'),
('f0f32188-6d36-400c-a430-0bea94eee00d', '42342525C', 'Francisco', 'Torral', 'ROLE_EMPLOYEE'::role_enum,6692720986, 'ftorral', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',25, '3bcc77b4-13c3-4f73-9627-0c27ab3ffe8d', 'francisco.torral@antharos.com', 25000,'2025-03-01', 'ACTIVE'::status_enum, 'c7924409-7eaf-4022-b0b3-9b8a78a0bb7d', 'jperez', '2025-03-01 00:00:00'),
('43a29abe-81ea-4395-a8c7-011b9e420a51', '28142355Z', 'Sofía', 'Caldo', 'ROLE_EMPLOYEE'::role_enum,6680215272, 'scaldo', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',26, '2bc45806-1fc2-47f5-a39a-2d60f399f47a', 'sofía.caldo@antharos.com', 50000,'2025-05-11', 'ACTIVE'::status_enum, 'e513050a-a90a-4d3c-b820-113b9e098e52', 'jperez', '2025-02-01 00:00:00'),
('a8826eda-4e48-4710-a1d2-43dc3a178872', '92842355Z', 'Lucía', 'Malt', 'ROLE_EMPLOYEE'::role_enum,6680645272, 'lmalt', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',27, '2bc45806-1fc2-47f5-a39a-2d60f399f47a', 'lucia.malt@antharos.com', 50000,'2025-05-02', 'ACTIVE'::status_enum, 'e513050a-a90a-4d3c-b820-113b9e098e52', 'jperez', '2025-02-01 00:00:00'),
('47a0474b-5da8-439c-8011-df581848ce02', '71549026V', 'Ander', 'Loto', 'ROLE_EMPLOYEE'::role_enum,6623920986, 'aloto', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',28, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'ander.loto@antharos.com', 60000,'2025-05-01', 'ACTIVE'::status_enum, 'c7924409-7eaf-4022-b0b3-9b8a78a0bb7d', 'jperez', '2025-03-01 00:00:00'),
('fe12a72f-231b-4df2-ac52-ce95562d8a68', '32549026V', 'Felipe', 'Hermoso', 'ROLE_EMPLOYEE'::role_enum,6623920986, 'fhermoso', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',29, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'felipe.hermoso@antharos.com', 60000,'2025-05-01', 'ACTIVE'::status_enum, 'c7924409-7eaf-4022-b0b3-9b8a78a0bb7d', 'jperez', '2025-03-01 00:00:00'),
('9553eb6c-322f-44df-9a7c-6be77abb80fe', '21549026V', 'Juana', 'Polo', 'ROLE_EMPLOYEE'::role_enum,6623920986, 'jpolo', '$2a$10$dnmhMG1TNVXyslpuk5YlgeZxZxUCsyaYBZ0u5o1R44d0YFFJzogoe',30, 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f', 'juana.polo@antharos.com', 60000,'2025-05-01', 'ACTIVE'::status_enum, 'c7924409-7eaf-4022-b0b3-9b8a78a0bb7d', 'jperez', '2025-03-01 00:00:00');

UPDATE "department" SET "department_head_id" = 'a123b456-c789-012d-ef34-56789abcdef0' where id = 'a4a77bc5-e352-4ac0-8ec3-d3af8271f61f';
UPDATE "department" SET "department_head_id" = 'c2998551-89b1-4021-b3a6-da5b317ebc4b' where id = 'db978bbe-a875-4368-a58a-3a864d3af8ec';
UPDATE "department" SET "department_head_id" = 'f663e95b-d6ff-4631-990e-284b30afecda' where id = '2bc45806-1fc2-47f5-a39a-2d60f399f47a';
UPDATE "department" SET "department_head_id" = '8d4e74d5-f638-4561-ba68-fcc939d665d2' where id = '3bcc77b4-13c3-4f73-9627-0c27ab3ffe8d';

