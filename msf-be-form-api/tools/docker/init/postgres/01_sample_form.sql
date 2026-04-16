CREATE SCHEMA IF NOT EXISTS smartform AUTHORIZATION temp;

SET search_path TO smartform;

CREATE TABLE IF NOT EXISTS _sample_form (
  form_id     VARCHAR(30) PRIMARY KEY,
  form_name   VARCHAR(100) NOT NULL,
  form_status VARCHAR(30)  NOT NULL,
  cret_dt     TIMESTAMP,
  cret_id     VARCHAR(50),
  amd_dt      TIMESTAMP,
  amd_id      VARCHAR(50)
);

ALTER TABLE _sample_form ADD COLUMN IF NOT EXISTS cret_dt TIMESTAMP;
ALTER TABLE _sample_form ADD COLUMN IF NOT EXISTS cret_id VARCHAR(50);
ALTER TABLE _sample_form ADD COLUMN IF NOT EXISTS amd_dt TIMESTAMP;
ALTER TABLE _sample_form ADD COLUMN IF NOT EXISTS amd_id VARCHAR(50);

INSERT INTO _sample_form (form_id, form_name, form_status, cret_dt, cret_id, amd_dt, amd_id)
VALUES ('FORM1-001', 'Sample Form 1-1', 'RDY', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
       ('FORM1-002', 'Sample Form 1-2', 'IPR', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
       ('FORM1-003', 'Sample Form 1-3', 'CMP', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
       ('FORM1-004', 'Sample Form 1-4', 'RDY', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
       ('FORM1-005', 'Sample Form 1-5', 'IPR', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
       ('FORM1-006', 'Sample Form 1-6', 'CMP', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
       ('FORM1-007', 'Sample Form 1-7', 'RDY', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
       ('FORM1-008', 'Sample Form 1-8', 'IPR', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
       ('FORM1-009', 'Sample Form 1-9', 'CMP', NOW(), 'SYSTEM', NOW(), 'SYSTEM'),
       ('FORM1-010', 'Sample Form 1-10', 'RDY', NOW(), 'SYSTEM', NOW(), 'SYSTEM')
ON CONFLICT (form_id) DO UPDATE
SET form_name = EXCLUDED.form_name,
    form_status = EXCLUDED.form_status,
    cret_dt = EXCLUDED.cret_dt,
    cret_id = EXCLUDED.cret_id,
    amd_dt = EXCLUDED.amd_dt,
    amd_id = EXCLUDED.amd_id;

UPDATE _sample_form
SET cret_dt = COALESCE(cret_dt, NOW()),
    cret_id = COALESCE(cret_id, 'SYSTEM'),
    amd_dt = COALESCE(amd_dt, NOW()),
    amd_id = COALESCE(amd_id, 'SYSTEM')
WHERE cret_dt IS NULL
   OR cret_id IS NULL
   OR amd_dt IS NULL
   OR amd_id IS NULL;
