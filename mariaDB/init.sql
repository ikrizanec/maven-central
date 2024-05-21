CREATE SCHEMA IF NOT EXISTS `maven_central`;

IF NOT EXISTS (SELECT 1 FROM user WHERE user_apikey = 'App la9psd71atbpgeg7fvvx') THEN
    INSERT INTO user (user_username, user_apikey) VALUES ('user1', 'App la9psd71atbpgeg7fvvx');
END IF;

-- Korisnik 2
IF NOT EXISTS (SELECT 1 FROM user WHERE user_apikey = 'App ox9w79g2jwctzww2hcyb') THEN
    INSERT INTO user (user_username, user_apikey) VALUES ('user2', 'App ox9w79g2jwctzww2hcyb');
END IF;

-- Korisnik 3
IF NOT EXISTS (SELECT 1 FROM user WHERE user_apikey = 'App othyqhps18srg7fdj0p9') THEN
    INSERT INTO user (user_username, user_apikey) VALUES ('user3', 'App othyqhps18srg7fdj0p9');
END IF;