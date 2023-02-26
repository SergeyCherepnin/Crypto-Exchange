INSERT INTO roles(id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles(id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO currencies(name) VALUES ('RUB');
INSERT INTO currencies(name) VALUES ('BTC');
INSERT INTO currencies(name) VALUES ('TON');

INSERT INTO wallet_currencies(id, amount, currency_name, wallet_key) VALUES (1, 0.00, 'RUB', '7e7be355-3e41-417a-90b5-41399c369fcb');
INSERT INTO wallet_currencies(id, amount, currency_name, wallet_key) VALUES (2, 0.00, 'BTC', '7e7be355-3e41-417a-90b5-41399c369fcb');
INSERT INTO wallet_currencies(id, amount, currency_name, wallet_key) VALUES (3, 0.00, 'TON', '7e7be355-3e41-417a-90b5-41399c369fcb');

SELECT amount FROM wallet_currencies where wallet_key = '59f5d2b0-3a5d-48ce-a38c-e123d2d9355f';