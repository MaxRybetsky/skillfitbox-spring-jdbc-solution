-- Insert 20 lockers with client_id set to null
INSERT INTO lockers (id, number, client_id, created_datetime, updated_datetime) VALUES
(GEN_RANDOM_UUID(), 1, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 2, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 3, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 4, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 5, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 6, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 7, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 8, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 9, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 10, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 11, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 12, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 13, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 14, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 15, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 16, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 17, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 18, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 19, NULL, NOW(), NOW()),
(GEN_RANDOM_UUID(), 20, NULL, NOW(), NOW());

-- Insert 5 additional services
INSERT INTO services (id, name, created_datetime, updated_datetime) VALUES
('SOLARIUM', 'Солярий', NOW(), NOW()),
('POOL', 'Бассейн', NOW(), NOW()),
('SAUNA', 'Сауна', NOW(), NOW()),
('CRYOSAUNA', 'Криосауна', NOW(), NOW()),
('CROSSFIT', 'Кроссфит', NOW(), NOW());
