CREATE SCHEMA IF NOT EXISTS `maven_central`;

IF NOT EXISTS (SELECT 1 FROM users WHERE api_key = 'la9psd71atbpgeg7fvvx') THEN
    INSERT INTO users (username, api_key) VALUES ('user1', 'la9psd71atbpgeg7fvvx');
END IF;

-- Korisnik 2
IF NOT EXISTS (SELECT 1 FROM users WHERE api_key = 'ox9w79g2jwctzww2hcyb') THEN
    INSERT INTO users (username, api_key) VALUES ('user2', 'ox9w79g2jwctzww2hcyb');
END IF;

-- Korisnik 3
IF NOT EXISTS (SELECT 1 FROM users WHERE apikey = 'othyqhps18srg7fdj0p9') THEN
    INSERT INTO users (username, api_key) VALUES ('user3', 'othyqhps18srg7fdj0p9');
END IF;