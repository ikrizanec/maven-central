-- Library entities
INSERT INTO maven_central.library (group_id, artifact_id, name, description)
     VALUES ('org.springframework', 'spring-core', 'Spring Core Framework', 'Spring Core Framework');
INSERT INTO maven_central.library (group_id, artifact_id, name, description)
     VALUES ('org.springframework', 'spring-web', 'Spring Web Framework', 'Spring Web Framework');
INSERT INTO maven_central.library (group_id, artifact_id, name, description)
     VALUES ('org.springframework', 'spring-test', 'Spring Test Framework', 'Spring Test Framework');

-- Version entities
INSERT INTO maven_central.version (semantic_version, description, deprecated, release_date, library_id)
     VALUES ('5.10.11', 'Spring Core Framework 5.10.11', FALSE, '2024-05-05 00:00:00', 1);
INSERT INTO maven_central.version (semantic_version, description, deprecated, release_date, library_id)
     VALUES ('6.3.12', 'Spring Core Framework', FALSE, '2024-05-05 00:00:00', 1);
INSERT INTO maven_central.version (semantic_version, description, deprecated, release_date, library_id)
     VALUES ('4.12.13', 'Spring Web Framework', FALSE, '2024-05-05 00:00:00', 2);
INSERT INTO maven_central.version (semantic_version, description, deprecated, release_date, library_id)
     VALUES ('5.10.11', 'Spring Web Framework', TRUE, '2024-05-05 00:00:00', 2);
INSERT INTO maven_central.version (semantic_version, description, deprecated, release_date, library_id)
     VALUES ('5.10.11', 'Spring Test Framework', TRUE, '2024-05-05 00:00:00', 3);

INSERT INTO maven_central.users (username, api_key)
     VALUES ('user1', 'la9psd71atbpgeg7fvvx');
INSERT INTO maven_central.users (username, api_key)
     VALUES ('user2', 'ox9w79g2jwctzww2hcyb');
INSERT INTO maven_central.users (username, api_key)
     VALUES ('user3', 'othyqhps18srg7fdj0p9');
