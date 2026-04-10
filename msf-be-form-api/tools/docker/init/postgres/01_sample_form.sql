CREATE SCHEMA IF NOT EXISTS smartform AUTHORIZATION temp;

SET search_path TO smartform;

CREATE TABLE IF NOT EXISTS _sample_form (
  form_id     VARCHAR(30) PRIMARY KEY,
  form_name   VARCHAR(100) NOT NULL,
  form_status VARCHAR(30)  NOT NULL
);

INSERT INTO _sample_form (form_id, form_name, form_status)
VALUES ('FORM1-001', 'Sample Form 1-1', 'RDY'),
       ('FORM1-002', 'Sample Form 1-2', 'IPR'),
       ('FORM1-003', 'Sample Form 1-3', 'CMP'),
       ('FORM1-004', 'Sample Form 1-4', 'RDY'),
       ('FORM1-005', 'Sample Form 1-5', 'IPR'),
       ('FORM1-006', 'Sample Form 1-6', 'CMP'),
       ('FORM1-007', 'Sample Form 1-7', 'RDY'),
       ('FORM1-008', 'Sample Form 1-8', 'IPR'),
       ('FORM1-009', 'Sample Form 1-9', 'CMP'),
       ('FORM1-010', 'Sample Form 1-10', 'RDY')
ON CONFLICT (form_id) DO UPDATE
SET form_name = EXCLUDED.form_name,
    form_status = EXCLUDED.form_status;
