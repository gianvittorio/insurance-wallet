BEGIN;

DELETE FROM companies;
DELETE FROM segment_coverage;
DELETE FROM coverage;
DELETE FROM segments;

INSERT INTO segments(segment_name, segment_description) VALUES('automotive', 'blah');

INSERT INTO coverage(coverage_name, coverage_description) VALUES('theft', 'blah');

INSERT INTO segment_coverage(segment_id, coverage_id) VALUES(CURRVAL('segment_id_seq'), CURRVAL('coverage_id_seq'));

INSERT INTO companies(company_name, company_document, segment_id) VALUES('X', '000.000.000-01', CURRVAL('segment_id_seq'));

COMMIT;