-- noinspection SqlNoDataSourceInspectionForFile

TRUNCATE TABLE maven_central.library RESTART IDENTITY;

INSERT INTO maven_central.library (library_name, available) VALUES ('Franck', TRUE);
INSERT INTO maven_central.library (library_name, available) VALUES ('Cogito', TRUE);
INSERT INTO maven_central.library (library_name, available) VALUES ('Lavazza', FALSE);
