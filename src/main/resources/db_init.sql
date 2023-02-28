INSERT INTO roles(id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles(id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO currencies(name) VALUES ('RUB');
INSERT INTO currencies(name) VALUES ('BTC');
INSERT INTO currencies(name) VALUES ('TON');

INSERT INTO exchange_rates(name, rate) VALUES ('rubToBtc', 0.01);
INSERT INTO exchange_rates(name, rate) VALUES ('rubToTon', 0.1);
INSERT INTO exchange_rates(name, rate) VALUES ('btcToRub', 100);
INSERT INTO exchange_rates(name, rate) VALUES ('btcToTon', 10);
INSERT INTO exchange_rates(name, rate) VALUES ('tonToRub', 10);
INSERT INTO exchange_rates(name, rate) VALUES ('tonToBtc', 0.1);

INSERT INTO users(username, email, password, key) VALUES (
'admin', 'admin@mail.com', '$2a$12$o2DFb4ffTo2BQ8Nj.4g72uIxqxw9C7Lm75wasGddkqX.o4FZbTKFW', 'admin');

INSERT INTO user_roles(user_id, role_id) VALUES (1, 2);
