BEGIN;


CREATE SEQUENCE IF NOT EXISTS segment_id_seq START 1;

CREATE TABLE IF NOT EXISTS segments (
    segment_id INT NOT NULL DEFAULT NEXTVAL('segment_id_seq'),
    segment_name VARCHAR(50) UNIQUE NOT NULL,
    segment_description TEXT,
    PRIMARY KEY (segment_id)
);

CREATE INDEX IF NOT EXISTS segment_name_id on segments(segment_name);


CREATE SEQUENCE IF NOT EXISTS coverage_id_seq START 1;

CREATE TABLE IF NOT EXISTS coverage (
    coverage_id INT NOT NULL DEFAULT NEXTVAL('coverage_id_seq'),
    coverage_name VARCHAR(50) UNIQUE NOT NULL,
    coverage_description TEXT,
    PRIMARY KEY (coverage_id)
);

CREATE INDEX IF NOT EXISTS coverage_name_id on coverage(coverage_name);


CREATE TABLE IF NOT EXISTS segment_coverage(
    segment_id INT NOT NULL,
    coverage_id INT NOT NULL,
    PRIMARY KEY (segment_id, coverage_id)
);


CREATE SEQUENCE IF NOT EXISTS companies_id_seq START 1;

CREATE TABLE IF NOT EXISTS companies (
    company_id INT NOT NULL DEFAULT NEXTVAL('companies_id_seq'),
    company_name VARCHAR(50) UNIQUE NOT NULL,
    company_document VARCHAR(50) UNIQUE NOT NULL,
    segment_id INT NOT NULL,
    PRIMARY KEY (company_id),
    CONSTRAINT fk_segment FOREIGN KEY(segment_id) REFERENCES segments(segment_id)
);

CREATE INDEX IF NOT EXISTS company_name_id on companies(company_name);
CREATE INDEX IF NOT EXISTS company_document_id on companies(company_document);

COMMIT;
