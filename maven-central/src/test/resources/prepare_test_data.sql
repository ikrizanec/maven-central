TRUNCATE TABLE maven_central.library RESTART IDENTITY;

-- Library entities
INSERT INTO maven_central.library (library_groupId, library_artifactId, library_name, library_description)
     VALUES ('org.springframework', 'spring-core', 'Spring Core Framework', 'Spring Core Framework');
INSERT INTO maven_central.library (library_groupId, library_artifactId, library_name, library_description)
     VALUES ('org.springframework', 'spring-web', 'Spring Web Framework', 'Spring Web Framework');
INSERT INTO maven_central.library (library_groupId, library_artifactId, library_name, library_description)
     VALUES ('org.springframework', 'spring-test', 'Spring Web Framework', 'Spring Test Framework');

-- Version entities
INSERT INTO maven_central.version (version_semanticVersion, version_description, version_deprecated, library_id)
     VALUES ('5.10.11', 'Spring Core Framework 5.10.11', FALSE, '2024-01-01 00:00:00', 1);
INSERT INTO maven_central.version (version_semanticVersion, version_description, version_deprecated, library_id)
     VALUES ('6.3.12', 'Spring Core Framework', FALSE, '2024-01-01 00:00:00', 1);
INSERT INTO maven_central.version (version_semanticVersion, version_description, version_deprecated, library_id)
     VALUES ('4.12.13', 'Spring Web Framework', FALSE, '2024-01-01 00:00:00', 2);
INSERT INTO maven_central.version (version_semanticVersion, version_description, version_deprecated, library_id)
     VALUES ('5.10.11', 'Spring Web Framework', TRUE, '2024-01-01 00:00:00', 2);
INSERT INTO maven_central.version (version_semanticVersion, version_description, version_deprecated, library_id)
     VALUES ('5.10.11', 'Spring Test Framework', TRUE, '2024-01-01 00:00:00', 3);
